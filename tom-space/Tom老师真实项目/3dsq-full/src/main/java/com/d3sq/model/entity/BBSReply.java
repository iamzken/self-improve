package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 话题回复
 *
 */
@Entity
@Table(name="t_bbs_reply")
public class BBSReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8454717413398364860L;
	
	private Long id;//回复主键标识
	private Long parentId;//父级回复id
	private Integer level;//回复的楼层
	private Long topicId;//话题id
	private Long topicCreatorId;//话题创建人id
	private Long creatorId;//回复创建者id
	
	private Long updateUid;//更新回复的用户Id
	private String content;//回复内容
	private String contentImgs;//回复图片
	private Long replyToUid;//被回复人的id
	private String xpath;
	private Long createTime;//回复的创建时间
	private Long updateTime;//回复的更新时间
	private Integer rootReplyCount;//针对一级回复的数量
	private String praiseUser;//点赞用户
	private Integer praiseCount;//点赞数量

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Long getTopicCreatorId() {
		return topicCreatorId;
	}
	public void setTopicCreatorId(Long topicCreatorId) {
		this.topicCreatorId = topicCreatorId;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentImgs() {
		return contentImgs;
	}
	public void setContentImgs(String contentImgs) {
		this.contentImgs = contentImgs;
	}
	public Long getReplyToUid() {
		return replyToUid;
	}
	public void setReplyToUid(Long replyToUid) {
		this.replyToUid = replyToUid;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public Long getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(Long updateUid) {
		this.updateUid = updateUid;
	}
	public Integer getRootReplyCount() {
		return rootReplyCount;
	}
	public void setRootReplyCount(Integer rootReplyCount) {
		this.rootReplyCount = rootReplyCount;
	}
	public String getPraiseUser() {
		return praiseUser;
	}
	public void setPraiseUser(String praiseUser) {
		this.praiseUser = praiseUser;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	
}
