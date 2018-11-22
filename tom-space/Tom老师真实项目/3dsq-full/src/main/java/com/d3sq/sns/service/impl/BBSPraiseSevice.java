package com.d3sq.sns.service.impl;

import javax.core.common.ResultMsg;
import javax.core.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.RequiredDao;
import com.d3sq.model.entity.BBSPraise;
import com.d3sq.model.entity.BBSReply;
import com.d3sq.model.entity.BBSTopic;
import com.d3sq.model.entity.Member;
import com.d3sq.sns.dao.BBSPraiseDao;
import com.d3sq.sns.dao.BBSReplyDao;
import com.d3sq.sns.dao.BBSTopicDao;
import com.d3sq.sns.service.IBBSPraiseSevice;

@Service("bbsPraiseSevice")
public class BBSPraiseSevice implements IBBSPraiseSevice {
	
	@Autowired
	private BBSPraiseDao bbsPraiseDao;
	@Autowired
	private BBSTopicDao bbsTopicDao;
	@Autowired
	private BBSReplyDao bbsReplyDao;
	@Autowired
	private RequiredDao requiredDao;

	@Override
	public ResultMsg<?> addPraise(String local, Long uid, String enc, Integer type, Long objId) {
		JSONObject error = new JSONObject();
		if(type != 1 && type != 2){
			error.put("type", "点赞类型错误！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数错误!",error);
		}
		if(null == uid || uid == 0){
			error.put("userId", "点赞类型错误！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		Member member = requiredDao.selectMemberById(uid);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在");
		}
		if(null == objId || objId == 0){
			error.put("objId", "点赞的对象标识不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(!MobileConstant.genEnc(uid.toString()+objId.toString()).equals(enc)){
			error.put("enc", "授权码错误！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		
		BBSPraise praise = null;
		BBSTopic topic = null;
		BBSReply reply = null;
		if(type == 1){
			topic = bbsTopicDao.selectTopicById(objId);
			if(null == topic) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在!");
			}
			praise = bbsPraiseDao.selectOnePraise(type, uid, objId, null);
		}else if(type == 2){
			reply = bbsReplyDao.selectReplyById(objId);
			if(null == reply) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复不存在!");
			}
			praise = bbsPraiseDao.selectOnePraise(type, uid, null, objId);
		}
		if(praise != null) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "点赞成功!");
		}
		praise = new BBSPraise();
		praise.setType(type);
		praise.setUid(uid);
		if(type == 1){
			praise.setTopicId(objId);
		}else if(type == 2){
			praise.setReplyId(objId);
		}
		praise.setCreateTime(System.currentTimeMillis());
		long id = 0;
		try {
			id = bbsPraiseDao.insert(praise);
		} catch (Exception e) {
		}
		if(id == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "点赞失败!");
		}
		String tag = uid+",";
		if(type == 1){
			//更新话题点赞用户
			String praiseUser = StringUtils.notNull(topic.getPraiseUser());
			praiseUser = praiseUser + tag;
			topic.setPraiseUser(praiseUser);
			topic.setPraiseCount(praiseUser.split(",").length);
			bbsTopicDao.update(topic);
		}else if(type == 2){
			//更新回复点赞用户
			String praiseUser = StringUtils.notNull(reply.getPraiseUser());
			praiseUser = praiseUser + tag;
			reply.setPraiseUser(praiseUser);
			reply.setPraiseCount(praiseUser.split(",").length);
			bbsReplyDao.update(reply);
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "点赞成功!");
	}

	@Override
	public ResultMsg<?> removePraise(String local, Long uid, String enc, Integer type, Long objId) {
		JSONObject error = new JSONObject();
		if(type != 1 && type != 2){
			error.put("type", "type参数错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数错误!",error);
		}
		if(null == uid || uid == 0){
			error.put("userId", "未登录");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录!",error);
		}
		Member member = requiredDao.selectMemberById(uid);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在");
		}
		if(null == objId || objId == 0){
			error.put("objId", "未知的对象");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数错误!",error);
		}
		if(!MobileConstant.genEnc(uid.toString()+objId.toString()).equals(enc)){
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		
		BBSPraise praise = null;
		BBSTopic topic = null;
		BBSReply reply = null;
		if(type == 1){
			topic = bbsTopicDao.selectTopicById(objId);
			if(null == topic) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在!");
			}
			praise = bbsPraiseDao.selectOnePraise(type, uid, objId, null);
		}else if(type == 2){
			reply = bbsReplyDao.selectReplyById(objId);
			if(null == reply) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复不存在!");
			}
			praise = bbsPraiseDao.selectOnePraise(type, uid, null, objId);
		}
		if(praise == null) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "取消点赞成功!");
		}
		int count = 0;
		try {
			count = bbsPraiseDao.delete(praise);
		} catch (Exception e) {
			
		}
		
		if(count == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "取消点赞失败!");
		}
		String tag = uid+",";
		if(type == 1){
			//更新话题点赞用户
			String praiseUser = StringUtils.notNull(topic.getPraiseUser());
			if(!StringUtils.isEmpty(praiseUser)) {
				praiseUser = praiseUser.replace(tag, "");
				topic.setPraiseUser(praiseUser);
				if(StringUtils.isEmpty(praiseUser)){
					topic.setPraiseCount(0);
				}else{
					topic.setPraiseCount(praiseUser.split(",").length);
				}
				
				bbsTopicDao.update(topic);
			}
			
		}else if(type == 2){
			//更新回复点赞用户
			String praiseUser = StringUtils.notNull(reply.getPraiseUser());
			if(!StringUtils.isEmpty(praiseUser)) {
				praiseUser = praiseUser.replace(tag, "");
				reply.setPraiseUser(praiseUser);
				if(StringUtils.isEmpty(praiseUser)){
					reply.setPraiseCount(0);
				}else{
					reply.setPraiseCount(praiseUser.split(",").length);
				}
				
				bbsReplyDao.update(reply);
			}
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "取消点赞成功!");
	}
	
}
