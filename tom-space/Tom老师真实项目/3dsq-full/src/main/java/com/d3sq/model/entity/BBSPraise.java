package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 点赞
 *
 */
@Entity
@Table(name="t_bbs_praise")
public class BBSPraise implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4082035593903690973L;
	
	private Long id;
	private Long uid;
	private Long topicId;
	private Long replyId;
	private Integer type;//1:话题点赞,2:回复点赞
	private Long createTime;//点赞时间
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

}
