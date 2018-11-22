package com.d3sq.sns.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
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
import com.d3sq.model.entity.BBSReply;
import com.d3sq.model.entity.BBSTopic;
import com.d3sq.model.entity.Member;
import com.d3sq.sns.dao.BBSReplyDao;
import com.d3sq.sns.dao.BBSTopicDao;
import com.d3sq.sns.service.IBBSReplyService;
import com.d3sq.sns.utils.ImagesUtil;
import com.d3sq.sns.utils.MemberUtils;
import com.d3sq.sns.vo.BBSReplyVo;
import com.d3sq.sns.vo.ReplyTo;
import com.d3sq.sns.vo.RootReplyVo;

@Service("bbsReplyService")
public class BBSReplyService implements IBBSReplyService {
	
	private static Log log = LogFactory.getLog(BBSReplyService.class);
	
	@Autowired
	private BBSTopicDao bbsTopicDao;
	@Autowired
	private BBSReplyDao bbsReplyDao;
	@Autowired
	private RequiredDao requiredDao;
	//@Autowired
	//private SequenceDao sequenceDao;

	@Override
	public ResultMsg<?> addReply(String local, Long creatorId, Long topicId, Long replyId, String content,
			String contentImgs, String enc) {
		
		JSONObject error = new JSONObject();
		if(null == creatorId || creatorId == 0){
			error.put("userId", "未登录");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录!",error);
		}
		if(!MobileConstant.genEnc(creatorId.toString()).equals(enc)){
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		if(null == topicId || topicId == 0){
			error.put("topicId", "话题Id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(StringUtils.isEmpty(content) && StringUtils.isEmpty(contentImgs)){
			error.put("content", "回复内容不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		Member member = requiredDao.selectMemberById(creatorId);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在");
		}
		BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
		if(null == topic){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在！");
		}
		BBSReply reply = new BBSReply();
		reply.setContent(content);
		reply.setContentImgs(ImagesUtil.validImgArr(contentImgs));
		reply.setCreatorId(creatorId);
		reply.setTopicId(topicId);
		reply.setTopicCreatorId(topic.getCreatorId());
		long id = 0;
		try {
			id = bbsReplyDao.insert(reply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(id == 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复失败！");
		}
		reply.setId(id);
		long replyToUid = 0;//默认为回复话题
		long parentId = 0;
		int level = 1;
		String xpath = "/0/"+id+"/";
		BBSReply replyTo = null;
		boolean isReplyTopic = true;
		//回复的回复
		if(null != replyId && replyId != 0){
			replyTo = bbsReplyDao.selectReplyById(replyId);
		}
		if(null != replyTo){
			parentId = replyTo.getId();
			replyToUid = replyTo.getCreatorId();
			xpath = replyTo.getXpath()+id+"/";
			level = replyTo.getLevel() + 1;
			isReplyTopic = false;
		}
		long currTime = System.currentTimeMillis();
		reply.setReplyToUid(replyToUid);
		reply.setParentId(parentId);
		reply.setLevel(level);
		reply.setXpath(xpath);
		reply.setCreateTime(currTime);
		int count = 0;
		try {
			count = bbsReplyDao.update(reply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(count == 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复失败！");
		}
		if(isReplyTopic) {
			//更新话题最新回复,只记录一级回复
			topic.setLastReplyContent(content);
			topic.setLastReplyContentImgs(ImagesUtil.validImgArr(contentImgs));
			topic.setLastReplyName(MemberUtils.getName(member));
			topic.setLastReplyUid(creatorId);
			topic.setLastReplyTime(currTime);
			long replyCount = topic.getReplyCount() == null ? 0 : topic.getReplyCount();
			topic.setReplyCount(replyCount + 1);
			try {
				bbsTopicDao.update(topic);
			} catch (Exception e) {
				bbsReplyDao.delete(reply);
				log.error(e.getMessage());
			}
			
		}
		String rootXpath = reply.getXpath();
		if(!StringUtils.isEmpty(rootXpath)){
			rootXpath = rootXpath.substring(0, 6);
			BBSReply rootReply = bbsReplyDao.selectEqXpath(rootXpath);
			if(null != rootReply){
				Integer rootReplyCount = rootReply.getRootReplyCount() == null ? 0 : rootReply.getRootReplyCount();
				rootReply.setRootReplyCount(rootReplyCount + 1);
				bbsReplyDao.update(rootReply);
			}
		}
		
		RootReplyVo vo = RootReplyVo.getInstance(reply);
		vo.setPhoto(member.getPhoto());
		vo.setCreatorName(MemberUtils.getName(member));
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "回复成功啦~",JSONObject.toJSON(vo));
	}

	@Override
	public ResultMsg<?> modifyReply(String local, Long replyId, Long creatorId, String content, String contentImgs,
			String enc) {
		
		JSONObject error = new JSONObject();
		if(null == replyId || replyId == 0){
			error.put("replyId", "回复id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		if(null == creatorId || creatorId == 0){
			error.put("creatorId", "未登录!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录!",error);
		}
		if(!MobileConstant.genEnc(creatorId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		if(StringUtils.isEmpty(content) && StringUtils.isEmpty(contentImgs)){
			error.put("content", "回复内容不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		Member member = requiredDao.selectMemberById(creatorId);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在！");
		}
		BBSReply reply = bbsReplyDao.selectReplyById(replyId);
		if(null == reply) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复不存在！");
		}
		Long replyCreatorId = reply.getCreatorId();
		if(!creatorId.equals(replyCreatorId)) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "您没有权限修改此回复！");
		}
		reply.setContent(content);
		reply.setContentImgs(ImagesUtil.validImgArr(contentImgs));
		reply.setUpdateUid(creatorId);
		reply.setUpdateTime(System.currentTimeMillis());
		int count = 0;
		try {
			count = bbsReplyDao.update(reply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(count == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "修改回复失败！");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "修改回复成功啦~");
	}

	@Override
	public ResultMsg<?> removeReply(String local, Long replyId, Long creatorId, String enc) {
		
		JSONObject error = new JSONObject();
		if(null == replyId || replyId == 0){
			error.put("replyId", "回复的id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(null == creatorId || creatorId == 0){
			error.put("userId", "未登录!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "未登录!",error);
		}
		if(!MobileConstant.genEnc(creatorId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		Member member = requiredDao.selectMemberById(creatorId);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在！");
		}
		BBSReply reply = bbsReplyDao.selectReplyById(replyId);
		if(null == reply) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复不存在！");
		}
		Long replyCreatorId = reply.getCreatorId();
		if(!creatorId.equals(replyCreatorId)) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "您没有权限删除此回复！");
		}
		String xpath = reply.getXpath();
		long parentId = reply.getParentId();
		try {
			//删除回复
			int count = bbsReplyDao.delete(reply);
			if(count > 0) {
				//获取当前回复节点下的所有子节点
				List<BBSReply> subReplys = bbsReplyDao.selectReplyByXpath(xpath);
				if(null != subReplys && subReplys.size() > 0) {
					bbsReplyDao.deleteAll(subReplys); //删除子集回复
				}
			}
			if(parentId != 0){
				//更新一级回复数量
				if(!StringUtils.isEmpty(xpath)){
					xpath = xpath.substring(0, 6);
					BBSReply rootReply = bbsReplyDao.selectEqXpath(xpath);
					if(null != rootReply){
						Integer rootReplyCount = rootReply.getRootReplyCount();
						if(null != rootReplyCount && rootReplyCount > 0){
							rootReply.setRootReplyCount(rootReplyCount - 1);
							bbsReplyDao.update(rootReply);
						}
						
					}
				}
			}else{
				//更新话题回复数量
				Long topicId = reply.getTopicId();
				if(null != topicId){
					BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
					if(null != topic) {
						long replyCount = topic.getReplyCount() == null ? 0 : topic.getReplyCount();
						if(replyCount > 0){
							topic.setReplyCount(replyCount - 1);
							bbsTopicDao.update(topic);
						}
						
					}
					
				}
				
			}
		} catch (Exception e) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "删除回复失败！");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "删除回复成功啦~");
	}

	@Override
	public ResultMsg<?> getRootReplyListForPage(String local,Long uid, Long topicId, Long lastId, Integer replySize,
			Integer pageSize, Integer orderFlag,String enc) {
		
		JSONObject error = new JSONObject();
		if(null == topicId || topicId == 0){
			error.put("topicId", "话题Id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(!MobileConstant.genEnc(topicId.toString()).equals(enc)){
			error.put("enc", "授权码错误！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		BBSTopic topic = bbsTopicDao.selectTopicById(topicId);
		if(null == topic){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "话题不存在！");
		}
		//一级节点
		BBSReplyVo replyVo = new BBSReplyVo(); 
		//获取一级回复
		List<Map<String, Object>> rootReplyList = bbsReplyDao.selectReplyListForPage(topicId, null, 0l, null, orderFlag, 
				 lastId, pageSize);
		if(null == rootReplyList || rootReplyList.size() == 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复列表成功啦~",replyVo);
		}
		
		Member loginMember = null;
		if(null != uid){
			//获取当前登录用户
			loginMember = requiredDao.selectMemberById(uid);
		}
		
		List<String> xpathList = new ArrayList<String>();//一级回复的xpath
		List<Long> uids = new ArrayList<Long>();//回复人id
		
		Map<Long,Member> allMemberMap = new HashMap<Long,Member>();//所有回复人map
		List<RootReplyVo> result = new ArrayList<RootReplyVo>();
		Map<Long,String> xpathMap = new HashMap<Long,String>();
		try {
			for (Map<String, Object> map : rootReplyList) {
				BBSReply reply = JSON.parseObject(JSONObject.toJSONString(map), BBSReply.class);
				if(null == reply) continue;
				xpathList.add(reply.getXpath());
				xpathMap.put(reply.getId(), reply.getXpath());
				uids.add(reply.getCreatorId());
				uids.add(reply.getReplyToUid());
				RootReplyVo vo = RootReplyVo.getInstance(reply);
				if(null != loginMember){
					String praiseUser = reply.getPraiseUser();
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
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "获取回复列表失败~");
		}
		//获取子级回复
		List<Map<String, Object>> secondReplyList = bbsReplyDao.selectSecondReplyList(xpathList, replySize);
		if(null == secondReplyList || secondReplyList.size() == 0){
			if(uids.size() > 0){
				//获取一回复的用户
				List<Member> memberList = requiredDao.selectMemberByIds(uids);
				if(null != memberList && memberList.size() > 0){
					for (Member member : memberList) {
						allMemberMap.put(member.getId(), member);
					}
				}
			}
			
			for (RootReplyVo root : result) {
				Long creatorId = root.getCreatorId();
				Member member = null;
				if(null != creatorId && !allMemberMap.isEmpty()){
					member = allMemberMap.get(creatorId);
				}
				root.setPhoto(member.getPhoto());
				root.setCreatorName(MemberUtils.getName(member));
			}
			replyVo.setReply(result);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复列表成功啦~",JSONObject.toJSON(replyVo));
		}
		
		Map<Long,List<RootReplyVo>> secondReplyMap = new HashMap<Long,List<RootReplyVo>>();
		
		if(!xpathMap.isEmpty()){
			for (Long rootReplyId : xpathMap.keySet()) {
				String rootXpath = xpathMap.get(rootReplyId);
				if(StringUtils.isEmpty(rootXpath)) continue;
				for(Map<String, Object> replyMap : secondReplyList){
					BBSReply secondReply = JSON.parseObject(JSONObject.toJSONString(replyMap), BBSReply.class);
					if(null == secondReply) continue;
					//根据xpath分组
					String xpath = secondReply.getXpath();
					if(StringUtils.isEmpty(xpath)) continue;
					
					List<RootReplyVo> replyList = secondReplyMap.get(rootReplyId);
					if(null == replyList) {
						replyList = new ArrayList<RootReplyVo>();
					}
					if(xpath.startsWith(rootXpath)){
						RootReplyVo secondReplyVo = RootReplyVo.getInstance(secondReply);
						if(null != loginMember){
							String praiseUser = secondReply.getPraiseUser();
							if(!StringUtils.isEmpty(praiseUser)){
								//是否点赞
								praiseUser = praiseUser.replace(",", "|");
								if(uid.toString().matches(praiseUser)){
									secondReplyVo.setPraise(1);
								}
							}
						}
						replyList.add(secondReplyVo);
					}
					secondReplyMap.put(rootReplyId, replyList);
					
				}
			}
		}
		
		
		
		for (Long key : secondReplyMap.keySet()) {
			List<RootReplyVo> replays = secondReplyMap.get(key);
			if(null == replays || replays.size() == 0) continue;
			for (RootReplyVo secondReply : replays) {
				Long creatorId = secondReply.getCreatorId();
				if(null == creatorId) continue;
				uids.add(creatorId);
			}
			
		}
		
		if(uids.size() > 0){
			//获取回复的用户
			List<Member> memberList = requiredDao.selectMemberByIds(uids);
			if(null != memberList && memberList.size() > 0){
				for (Member member : memberList) {
					allMemberMap.put(member.getId(), member);
				}
			}
		}
		for (Long key : secondReplyMap.keySet()) {
			List<RootReplyVo> replays = secondReplyMap.get(key);
			if(null == replays || replays.size() == 0) continue;
			for (RootReplyVo secondReply : replays) {
				Long creatorId = secondReply.getCreatorId();
				if(null == creatorId) continue;
				Member srm = allMemberMap.get(creatorId);
				if(null == srm) continue;
				secondReply.setPhoto(srm.getPhoto());
				secondReply.setCreatorName(MemberUtils.getName(srm));
				Integer level = secondReply.getLevel() == null ? 0 : secondReply.getLevel();
				if(level > 2){
					Long replyToUid = secondReply.getReplyToUid();
					if(null != replyToUid){
						Member replyToMmebr = allMemberMap.get(replyToUid);
						if(null == replyToMmebr) continue;
						ReplyTo replyTo = new ReplyTo();
						replyTo.setReplyToUid(replyToMmebr.getId());
						replyTo.setReplyToName(MemberUtils.getName(replyToMmebr));
						secondReply.setReplyTo(replyTo);
					}
					
				}
			}
			
		}
		
		//组装回复数据
		for (RootReplyVo root: result) {
			Long id = root.getId();
			if(null == id) continue;
			Long creatorId = root.getCreatorId();
			if(null != creatorId) {
				Member srm = allMemberMap.get(creatorId);
				if(null == srm) continue;
				root.setPhoto(srm.getPhoto());
				root.setCreatorName(MemberUtils.getName(srm));
			}
			Integer residualReplyCount = 0;
			List<RootReplyVo> secondReply = secondReplyMap.get(id);
			if(null != secondReply && secondReply.size() > 0) {
				root.setSecondReply(secondReply);
				Integer rootReplyCount = root.getRootReplyCount();
				if(rootReplyCount > secondReply.size()){
					residualReplyCount = rootReplyCount - secondReply.size();
				}
			}
			root.setResidualReplyCount(residualReplyCount);
		}
		replyVo.setReply(result);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复列表成功啦~",JSONObject.toJSON(replyVo));
	}

	@Override
	public ResultMsg<?> getSecondReplyListForPage(String local,Long uid, Long rootReplyId, Long lastId, Integer pageSize,
			Integer orderFlag,String enc) {
		
		JSONObject error = new JSONObject();
		if(null == rootReplyId || rootReplyId == 0){
			error.put("replyId", "回复的id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整！",error);
		}
		if(!MobileConstant.genEnc(rootReplyId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误!",error);
		}
		BBSReply rootReply = bbsReplyDao.selectReplyById(rootReplyId);
		if(null == rootReply){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "回复不存在！");
		}
		String xpath = rootReply.getXpath();
		if(StringUtils.isEmpty(xpath)) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "xpath为空！");
		}
		
		RootReplyVo root = RootReplyVo.getInstance(rootReply);
		//一级节点
		List<RootReplyVo> replyList = new ArrayList<RootReplyVo>();
		Long rootReplyCreatorId = rootReply.getCreatorId();
		if(null != rootReplyCreatorId && rootReplyCreatorId != 0){
			Member rootReplyCreator = requiredDao.selectMemberById(rootReplyCreatorId);
			if(null != rootReplyCreator) {
				root.setPhoto(rootReplyCreator.getPhoto());
				root.setCreatorName(MemberUtils.getName(rootReplyCreator));
			}
		}
		
		//获取子集回复
		List<Map<String, Object>> secondReplyList = bbsReplyDao.selectReplyListForPage(null, xpath, null, null, orderFlag, lastId, pageSize);
		if(null == secondReplyList || secondReplyList.size() == 0){
			root.setSecondReply(replyList);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复成功啦~！",root);
		}
		List<RootReplyVo> result = new ArrayList<RootReplyVo>();
		List<Long> uids = new ArrayList<Long>();//回复人id
		try {
			for (Map<String, Object> map : secondReplyList) {
				BBSReply reply = JSON.parseObject(JSONObject.toJSONString(map), BBSReply.class);
				if(null == reply) continue;
				uids.add(reply.getCreatorId());
				Long replyToUid = reply.getReplyToUid();
				if(null != replyToUid && replyToUid != 0){
					uids.add(replyToUid);
				}
				RootReplyVo vo = RootReplyVo.getInstance(reply);
				result.add(vo);
			}
		} catch (Exception e) {
			root.setSecondReply(replyList);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复成功啦~！",root);
		}
		//回复用户Map
		Map<Long,Member> secondMemberMap = new HashMap<Long,Member>();
		if(uids.size() > 0){
			List<Member> members = requiredDao.selectMemberByIds(uids);
			if(null != members && members.size() > 0){
				for (Member member : members) {
					secondMemberMap.put(member.getId(), member);
				}
			}
		}
		//组装数据
		for (RootReplyVo secondReply : result) {
			Long crearotId = secondReply.getCreatorId();
			if(!secondMemberMap.isEmpty()){
				Member srm = secondMemberMap.get(crearotId);
				if(null != srm){
					secondReply.setPhoto(srm.getPhoto());
					secondReply.setCreatorName(MemberUtils.getName(srm));
				}
				Integer level = secondReply.getLevel();
				if(null == level || level == 0) continue;
				if(level > 2){
					Long replyToUid = secondReply.getReplyToUid();
					if(null == replyToUid) continue;
					Member replyToMmebr = secondMemberMap.get(replyToUid);
					if(null == replyToMmebr) continue;
					ReplyTo replyTo = new ReplyTo();
					replyTo.setReplyToUid(replyToMmebr.getId());
					replyTo.setReplyToName(MemberUtils.getName(replyToMmebr));
					secondReply.setReplyTo(replyTo);
				}
			}
			
		}
		root.setSecondReply(result);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取回复列表成功啦~",JSONObject.toJSON(root));
	}
	
	

}
