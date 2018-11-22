package com.d3sq.sns.service;

import javax.core.common.ResultMsg;

public interface IBBSPraiseSevice {
	
	/**
	 * 点赞
	 * @param local
	 * @param uid
	 * @param enc
	 * @param topicId
	 * @param replyId
	 * @return
	 */
	ResultMsg<?> addPraise(String local,Long uid,String enc,Integer type,Long objId);
	/**
	 * 取消点赞
	 * @param local
	 * @param uid
	 * @param enc
	 * @param type
	 * @param objId
	 * @return
	 */
	ResultMsg<?> removePraise(String local,Long uid,String enc,Integer type,Long objId);

}
