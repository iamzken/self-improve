package com.d3sq.sns.service;

import javax.core.common.ResultMsg;

public interface IBBSForumService {
	
	/**
	 * 增加板块
	 * @param local
	 * @param creatorId
	 * @param residentialId 社区id
	 * @param title 标题
	 * @param typeFlag 板块类型 1：小区公共板块，2：物业公共板块
	 * @param state 
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addForum(String local,Long creatorId,Long residentialId,Long parentId,String title,Integer typeFlag,Integer state,String enc);
	/**
	 * 获取板块，如果当前社区下没有板块默认初始话小区公共板块和物业公共板块
	 * @param local
	 * @param residentialId
	 * @return
	 */
	ResultMsg<?> getForum(String local,Long residentialId,Integer order,Long lastId,Integer topicSize,Integer pageSize,String enc);

}
