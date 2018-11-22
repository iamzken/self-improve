package com.d3sq.sns.service;

import javax.core.common.ResultMsg;

public interface IBBSReplyService {
	
	/**
	 * 发表回复
	 * @param local
	 * @param creatorId 回复创建者
	 * @param topicId 话题id
	 * @param replyId 回复某个回复的id，如果 为 0 默认回复话题
	 * @param content 回复的内容
	 * @param contentImgs 回复内容中的图片地址
	 * @param enc 
	 * @return
	 */
	ResultMsg<?> addReply(String local,Long creatorId,Long topicId,
			Long replyId,String content,String contentImgs,String enc);
	
	/**
	 * 修改回复
	 * @param local
	 * @param replyId
	 * @param createId
	 * @param content
	 * @param contentImgs
	 * @param enc
	 * @return
	 */
	ResultMsg<?> modifyReply(String local,Long replyId,Long creatorId,String content,String contentImgs,String enc);
	
	/**
	 * 删除回复
	 * @param local
	 * @param replyId
	 * @param creatorId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> removeReply(String local,Long replyId,Long creatorId,String enc);
	/**
	 * 获取根回复列表
	 * @param local
	 * @param topicId 话题id
	 * @param lastId  每页最后一条回复的id
	 * @param replySize 二级回复的条数
	 * @param pageSize 每次获取的条数
	 * @param orderFlag 排序标记
	 * @return
	 */
	ResultMsg<?> getRootReplyListForPage(String local,Long uid,Long topicId,Long lastId,Integer replySize,Integer pageSize,Integer orderFlag,String enc);
	/**
	 * 获取指定根级回复下的子集回复列表
	 * @param local
	 * @param rootReplyId
	 * @param lastId
	 * @param pageSize
	 * @param orderFlag
	 * @return
	 */
	ResultMsg<?> getSecondReplyListForPage(String local,Long uid,Long rootReplyId,Long lastId,Integer pageSize,Integer orderFlag,String enc);
	

}
