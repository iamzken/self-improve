package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 好友关系表
 *
 */

@Entity
@Table(name="t_sns_friend")
public class SNSFriend implements Serializable {

	private Long id;		//自增ID
	private Long memberId;	//用户ID
	private Long groupId;	//分组ID
	private Long friendId;	//好友ID
	private Integer blackFlag;//是否拉黑
	private String alias;	//别名（好友备注名称）
	private Long createTime;//添加好友日期
	private Integer state;	//状态（1：有效，0：无效）
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getFriendId() {
		return friendId;
	}
	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
	public Integer getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(Integer blackFlag) {
		this.blackFlag = blackFlag;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
