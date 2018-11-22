package com.d3sq.sns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DateUtils;
import javax.core.common.utils.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.RequiredDao;
import com.d3sq.model.entity.BBSForum;
import com.d3sq.model.entity.BBSTopic;
import com.d3sq.model.entity.Member;
import com.d3sq.sns.constants.SysConstant;
import com.d3sq.sns.dao.BBSForumDao;
import com.d3sq.sns.dao.BBSReplyDao;
import com.d3sq.sns.dao.BBSTopicDao;
import com.d3sq.sns.service.IBBSTopicService;
import com.d3sq.sns.utils.ImagesUtil;
import com.d3sq.sns.utils.MemberUtils;
import com.d3sq.sns.vo.BBSTopicVo;

@Service("bbsTopicService")
public class BBSTopicService implements IBBSTopicService {
	
	private static Log log = LogFactory.getLog(BBSTopicService.class);
	
	@Autowired
	private BBSTopicDao bbsTopicDao;
	@Autowired
	private RequiredDao requiredDao;
	@Autowired
	private BBSForumDao bbsForumDao;
	@Autowired
	private BBSReplyDao bbsReplyDao;

	@Override
	public ResultMsg<?> addTopic(String local,Long creatorId,Long residentialId,Long forumId,String forumPath, String title, String content,
			String contentImgs, String enc) {
		
		JSONObject error = new JSONObject();
		if(null == creatorId || creatorId == 0){
			error.put("userId", "未登录!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录!",error);
		}
		if(null == residentialId || residentialId == 0) {
			error.put("residentialId", "社区id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(null == forumId || forumId == 0){
			error.put("forumId", "板块id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(!MobileConstant.genEnc(residentialId.toString()+creatorId.toString()+forumId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		if((StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) && StringUtils.isEmpty(contentImgs)) {
			error.put("content", "内容不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		BBSForum forum = bbsForumDao.selectForumById(forumId);
		if(null == forum){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "板块不存在！");
		}
		Member member = requiredDao.selectMemberById(creatorId);
		if(null == member) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在！");
		}
		BBSTopic topic = new BBSTopic();
		if(!StringUtils.isEmpty(title)) {
			int len = title.length();
			if(len > 30) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "标题长度不能大于30.");
			}
		}
		if(!StringUtils.isEmpty(forumPath)) {
			forumPath = StringUtils.decode(forumPath, "utf-8");
		}
		long currTime = System.currentTimeMillis();
		topic.setTitle(title);
		topic.setContent(content);
		topic.setContentImgs(ImagesUtil.validImgArr(contentImgs));
		topic.setResidentialId(residentialId);
		topic.setCreatorId(creatorId);
		topic.setCreateTime(currTime);
		topic.setUpdateTime(currTime);
		topic.setLastReplyTime(currTime);
		topic.setForumId(forumId);
		topic.setForumPath(forumPath);
		Long id = null;
		try {
			id = bbsTopicDao.insert(topic);
			topic.setId(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(id == null) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "发表话题失败");
		}
		BBSTopicVo vo = BBSTopicVo.getInstance(topic,member);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "发表话题成功啦~",JSONObject.toJSON(vo));
	}

	@Override
	public ResultMsg<?> removeTopic(String local,Long topicId, Long creatorId, String enc) {
		
		
		JSONObject error = new JSONObject();
		if(null == creatorId || creatorId == 0) {
			error.put("userId", "未登录！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录",error);
		}
		if(null == topicId || topicId == 0) {
			error.put("topicId", "话题id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(!MobileConstant.genEnc(topicId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		//检查用户
		Member member = requiredDao.selectMemberById(creatorId);
		if(member == null) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "删除话题失败！");
		}
		
		BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
		if(null == topic) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "删除话题成功啦~");
		}
		Long _creatorId = topic.getCreatorId();
		if(!creatorId.equals(_creatorId)) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "没有权限删除!");
		}
		int count = 0;
		try {
			count = bbsTopicDao.delete(topic);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(count == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "删除话题失败！");
		}
		//删除话题下所有回复
		bbsReplyDao.deleteByTopicId(topicId);
		
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "删除话题成功啦~");
	}

	@Override
	public ResultMsg<?> getTopic(String local,Long uid, Long topicId,String enc) {
		JSONObject error = new JSONObject();
		if(null == topicId || topicId == 0) {
			error.put("topicId", "话题id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(!MobileConstant.genEnc(topicId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
		if(null == topic) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在！");
		}
		Long creatorId = topic.getCreatorId();
		Member member = null;
		if(null != creatorId){
			member = requiredDao.selectMemberById(creatorId);
		}
		long readPersonCount = topic.getReadPersonCount();
		topic.setReadPersonCount(readPersonCount + 1);//增加阅读数
		bbsTopicDao.update(topic);
		BBSTopicVo vo = BBSTopicVo.getInstance(topic, member);
		if(null != uid){
			//获取当前登录用户
			Member loginMember = requiredDao.selectMemberById(uid);
			if(null != loginMember){
				String praiseUser = topic.getPraiseUser();
				if(!StringUtils.isEmpty(praiseUser)){
					//是否点赞
					praiseUser = praiseUser.replace(",", "|");
					if(uid.toString().matches(praiseUser)){
						vo.setPraise(1);
					}
				}
			}
		}
		Long updateUid = topic.getUpdateUid();
		if(null != updateUid) {
			Member updateMember = requiredDao.selectMemberById(updateUid);
			if(null != updateMember){
				Long updateTime = topic.getUpdateTime();
				String textTime = DateUtils.distance(new Date(updateTime), "yyyy-MM-dd HH:mm");
				String updateText = "此帖"+textTime+"被"+MemberUtils.getName(updateMember)+"进行过编辑";
				vo.setUpdateText(updateText);
			}
			
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取话题成功啦~",vo);
	}
	@Override
	public ResultMsg<?> getTopicList(String local,Long uid,Long residentialId,Long forumId,Integer top, String sw, Long lastTopicId, Integer pageSize,Integer order,String enc) {
		
		JSONObject error = new JSONObject();
		if(null == residentialId || residentialId == 0){
			error.put("residentialId", "社区Id不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(null == forumId || forumId == 0){
			error.put("forumId", "板块Id不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(!MobileConstant.genEnc(residentialId.toString()+forumId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		if(!StringUtils.isEmpty(sw)) {
			sw = StringUtils.decode(sw, SysConstant.UTF_8);
		}
		if(null == order) order = 0;
		//获取话题
		List<Map<String, Object>> topicList = bbsTopicDao.selectTopicListForPage(null, residentialId,forumId, top, sw, lastTopicId, order, pageSize);
		if(null == topicList || topicList.size() == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取话题列表成功啦~"); 
		}
		Member loginMember = null;
		if(null != uid){
			//获取当前登录用户
			loginMember = requiredDao.selectMemberById(uid);
		}
		List<BBSTopicVo> result = new ArrayList<BBSTopicVo>();
		try {
			//用户map
			Map<Long,Member> memberMap = new HashMap<Long,Member>();
			List<Long> ids = new ArrayList<Long>();
			for (Map<String, Object> map : topicList) {
				BBSTopic topic = JSON.parseObject(JSONObject.toJSONString(map), BBSTopic.class);
				if(null == topic) continue;
				Long creatorId = topic.getCreatorId();
				if(null != creatorId) {
					ids.add(creatorId);
				}
			}
			if(ids.size() > 0){
				List<Member> members = requiredDao.selectMemberByIds(ids);
				if(null != members && members.size() > 0) {
					for (Member member : members) {
						memberMap.put(member.getId(), member);
					}
				}
			}
			for (Map<String, Object> map : topicList) {
				BBSTopic topic = JSON.parseObject(JSONObject.toJSONString(map), BBSTopic.class);
				if(null == topic) continue;
				Member member = null;
				Long creatorId = topic.getCreatorId();
				if(null == creatorId) continue;
				if(!memberMap.isEmpty()) {
					member = memberMap.get(creatorId);
				}
				BBSTopicVo vo = BBSTopicVo.getInstance(topic, member);
				if(null == vo) continue;
				if(null != loginMember){
					String praiseUser = topic.getPraiseUser();
					if(!StringUtils.isEmpty(praiseUser)){
						//是否点赞
						praiseUser = praiseUser.replace(",", "|");
						if(uid.toString().matches(praiseUser)){
							vo.setPraise(1);
						}
					}
				}
				result.add(vo);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "获取话题列表出现异常！");
		}
		
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取话题列表成功啦~",JSONObject.toJSON(result));
	}

	@Override
	public ResultMsg<?> modifyTopic(String local,Long creatorId,Long forumId,String forumPath,Long topicId, String title, String content, String contentImgs, String enc) {
		JSONObject error = new JSONObject();
		if(!MobileConstant.genEnc("").equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		if(null == creatorId || creatorId == 0) {
			error.put("userId", "未登录!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录",error);
		}
		if(null == forumId || forumId == 0){
			error.put("forumId", "板块id不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if((StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) && StringUtils.isEmpty(contentImgs)) {
			error.put("content", "内容不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		BBSForum forum = bbsForumDao.selectForumById(forumId);
		if(null == forum){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "板块不存在！");
		}
		//检查用户
		Member member = requiredDao.selectMemberById(creatorId);
		if(member == null) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "修改话题失败,用户不存在！");
		}
		BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
		if(null == topic) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在");
		}
		Long _creatorId = topic.getCreatorId();
		if(!creatorId.equals(_creatorId)) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "没有权限修改话题!");
		}
		if(!StringUtils.isEmpty(title)) {
			int len = title.length();
			if(len > 30) {
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "标题长度不能大于30.");
			}
		}
		if(!StringUtils.isEmpty(forumPath)) {
			forumPath = StringUtils.decode(forumPath, "utf-8");
		}
		topic.setForumId(forumId);
		topic.setForumPath(forumPath);
		topic.setTitle(StringUtils.notNull(title, ""));
		topic.setContent(StringUtils.notNull(content, ""));
		topic.setContentImgs(ImagesUtil.validImgArr(contentImgs));
		topic.setUpdateTime(System.currentTimeMillis());
		topic.setUpdateUid(creatorId);
		int count = 0;
		try {
			count = bbsTopicDao.update(topic);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(count == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "修改话题失败！");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "修改话题成功啦~");
	}
	
}
