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
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.RequiredDao;
import com.d3sq.model.entity.BBSForum;
import com.d3sq.model.entity.BBSTopic;
import com.d3sq.model.entity.Member;
import com.d3sq.sns.dao.BBSForumDao;
import com.d3sq.sns.dao.BBSTopicDao;
import com.d3sq.sns.service.IBBSForumService;
import com.d3sq.sns.utils.MemberUtils;
import com.d3sq.sns.vo.BBSForumVo;
import com.d3sq.sns.vo.BBSTopicVo;

@Service("bbsForumService")
public class BBSForumService implements IBBSForumService {
	
	private static Log log = LogFactory.getLog(BBSForumService.class);
	
	@Autowired
	private BBSForumDao bbsForumDao;
	@Autowired
	private RequiredDao requiredDao;
	@Autowired
	private BBSTopicDao bbsTopicDao;

	@Override
	public ResultMsg<?> addForum(String local, Long creatorId, Long residentialId,Long parentId, String title, Integer typeFlag,
			Integer state, String enc) {
		
		JSONObject error = new JSONObject();
		if(null == creatorId || creatorId == 0){
			error.put("userId", "未登录");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(null == residentialId || residentialId == 0){
			error.put("residentialId", "社区id不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(!MobileConstant.genEnc(residentialId.toString()).equals(enc)){
			error.put("enc", "授权码错误！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		
		if(null == typeFlag || typeFlag == 0) {
			error.put("typeFlag", "板块类型不能为空！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		String alias = "";
		if(typeFlag == 1){
			alias = FieldConstant.BBSFORUM_ALIAS_RESIDENTIAL;
		}else if(typeFlag == 2){
			alias = FieldConstant.BBSFORUM_ALIAS_PROPERTY;
		}
		
		if(StringUtils.isEmpty(alias)){
			error.put("typeFlag", "未知的板块类型！");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数错误！",error);
		}
		Member member = requiredDao.selectMemberById(creatorId);
		if(null == member){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "用户不存在");
		}
		BBSForum parent = null;
		String xpath = "";
		
		if(null != parentId && parentId != 0){
			parent = bbsForumDao.selectForumById(parentId);
		}
		
		BBSForum forum = new BBSForum();
		forum.setTitle(title);
		forum.setResidentialId(residentialId);
		forum.setParentId(parentId);
		forum.setState(state);//默认为生效
		forum.setAlias(alias);
		long id = 0;
		try {
			id = bbsForumDao.insert(forum);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(id == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "添加板块失败！~");
		}
		xpath = "/0/"+id+"/";
		if(null != parent){
			xpath = parent.getXpath()+id+"/";
		}
		forum.setId(id);
		forum.setCreateTime(System.currentTimeMillis());
		forum.setXpath(xpath);
		int count = 0;
		try {
			count = bbsForumDao.update(forum);
		} catch (Exception e) {
			bbsForumDao.delete(forum);
			log.error(e.getMessage());
		}
		if(count == 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "添加板块失败！~");
		}
		BBSForumVo vo = BBSForumVo.getInstance(forum);
		vo.setPhoto(member.getPhoto());
		vo.setCreatorName(MemberUtils.getName(member));
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加板块成功啦~",JSONObject.toJSON(vo));
	}

	@Override
	public ResultMsg<?> getForum(String local, Long residentialId,Integer order,Long lastId,Integer topicSize,Integer pageSize,String enc) {
		
		JSONObject error = new JSONObject();
		if(null == residentialId || residentialId == 0){
			error.put("residentialId", "社区id不能为空!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整!",error);
		}
		if(!MobileConstant.genEnc(residentialId.toString()).equals(enc)){
			error.put("enc", "授权码错误!");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		List<Map<String, Object>> forumList = bbsForumDao.selectFornumList(residentialId, null,order,lastId,pageSize);
		List<BBSForum> list = new ArrayList<BBSForum>();
		if(null == forumList || forumList.size() == 0){
			for(int i = 0;i< 2;i++){
				//生成社区板块和物业板块
				BBSForum forum = new BBSForum();
				forum.setResidentialId(residentialId);
				forum.setParentId(0l);
				forum.setState(SystemConstant.ENABLE);
				forum.setTitle(i == 0 ? "小区":"物业");
				forum.setAlias(i == 0 ? FieldConstant.BBSFORUM_ALIAS_RESIDENTIAL:FieldConstant.BBSFORUM_ALIAS_PROPERTY);
				long id = 0;
				try {
					id = bbsForumDao.insert(forum);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				if(id == 0) {
					continue;
				}
				forum.setId(id);
				forum.setCreateTime(System.currentTimeMillis());
				forum.setXpath("/0/"+id+"/");
				try {
					bbsForumDao.update(forum);
				} catch (Exception e) {
					bbsForumDao.delete(forum);
					continue;
				}
				list.add(forum);
			}
			
		}else{
			for (Map<String, Object> map : forumList) {
				 BBSForum forum = JSON.parseObject(JSONObject.toJSONString(map), BBSForum.class);
				 if(null == forum) continue;
				 list.add(forum);
			}
		}
		
		if(list.size() == 0) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_FAILED, "获取板块失败！~");
		}
		List<Long> forumIds = new ArrayList<Long>();
		List<BBSForumVo> resultList = new ArrayList<BBSForumVo>();
		for (BBSForum forum : list) {
			BBSForumVo vo = BBSForumVo.getInstance(forum);
			resultList.add(vo);
			forumIds.add(vo.getId());
			
		}
		//获取指定数量的话题
		List<Map<String, Object>> topics = bbsTopicDao.selectToipicByForumIds(forumIds, topicSize);
		List<BBSTopicVo> forumTopics = new ArrayList<BBSTopicVo>();
		if(null != topics && topics.size() > 0) {
			List<Long> uids = new ArrayList<Long>();
			List<BBSTopic> topicList = new ArrayList<BBSTopic>();
			for (Map<String, Object> map : topics) {
				BBSTopic topic = JSON.parseObject(JSONObject.toJSONString(map), BBSTopic.class);
				if(null == topic) continue;
				uids.add(topic.getCreatorId());
				topicList.add(topic);
			}
			Map<Long,Member> memberMap = new HashMap<Long,Member>();
			if(uids.size() > 0){
				//获取回复的用户
				List<Member> memberList = requiredDao.selectMemberByIds(uids);
				if(null != memberList && memberList.size() > 0){
					for (Member member : memberList) {
						memberMap.put(member.getId(), member);
					}
				}
			}
			if(topicList.size() > 0) {
				for (BBSTopic topic : topicList) {
					Member member = null;
					Long creatorId = topic.getCreatorId();
					if(null == creatorId) continue;
					if(!memberMap.isEmpty()) {
						member = memberMap.get(creatorId);
					}
					BBSTopicVo vo = BBSTopicVo.getInstance(topic, member);
					if(null == vo) continue;
					forumTopics.add(vo);
				}
			}
		}
		Map<Long,List<BBSTopicVo>> resultTopicMap = new HashMap<Long,List<BBSTopicVo>>();
		if(forumTopics.size() > 0){
			for (BBSTopicVo bbsTopicVo : forumTopics) {
				Long forumId =  bbsTopicVo.getForumId();
				if(null == forumId)continue;
				List<BBSTopicVo> topicList = resultTopicMap.get(forumId);
				if(null == topicList){
					topicList = new ArrayList<BBSTopicVo>();
				}
				topicList.add(bbsTopicVo);
				resultTopicMap.put(forumId, topicList);
			}
		}
		
		for (BBSForumVo vo : resultList) {
			if(!resultTopicMap.isEmpty()){
				List<BBSTopicVo> topic = resultTopicMap.get(vo.getId());
				if(null == topic) {
					topic = new ArrayList<BBSTopicVo>();
				}
				vo.setTopic(topic);
			}
			
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取板块成功啦~",JSONObject.toJSON(resultList));
	}

}
