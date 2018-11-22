package com.d3sq.sns.service;

import javax.core.common.ResultMsg;

public interface IBBSTopicService {
	
	/**
	 * 发表话题
	 * @param creatorId 创建人id
	 * @param author 创建人
	 * @param residentialId 社区id
	 * @param title 标题
	 * @param content 话题内容
	 * @param contentImgs 话题内容中的图片地址
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addTopic(String local,Long creatorId,Long residentialId,
			Long forumId,String forumPath,String title,String content,String contentImgs,String enc);
	
	/**
	 * 删除话题
	 * @param bbsTopicId 话题id
	 * @param creatorId 话题创建者id
	 * @param enc
	 * @return
	 */
	ResultMsg<?> removeTopic(String local,Long topicId,Long creatorId,String enc);
	/**
	 * 获取话题详情
	 * @param local
	 * @param topicId
	 * @return
	 */
	ResultMsg<?> getTopic(String local,Long uid,Long topicId,String enc);
	/**
	 * 获取话题列表
	 * @param residentialId 社区ID
	 * @param sw 检索参数,检索范围(话题标题、话题内容、话题创建人名称)
	 * @param lastBBSTopicId
	 * @param pageSize
	 * @return
	 */
	ResultMsg<?> getTopicList(String local,Long uid,Long residentialId,Long forumId,Integer top,String sw,Long lastTopicId,Integer pageSize,Integer order,String enc);
	
	/**
	 * 修改话题
	 * @param creatorId 话题创建者id
	 * @param title 话题标题
	 * @param content 话题内容
	 * @param contentImgs 话题内容中的图片地址
	 * @param enc
	 * @return
	 */
	ResultMsg<?> modifyTopic(String local,Long creatorId,Long forumId,String forumPath,Long topicId,String title,String content,String contentImgs,String enc);

}
