package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VIP用户信息表
 *
 */
@Entity
@Table(name="t_vip")
public class Vip implements Serializable {

	private Long id;				//ID，自增
	private Long memberId;			//用户ID
	private Integer flag;			//会员类型
	private Integer level;			//会员等级
	private Integer store;			//会员等级
	private Long createTime;		//首次开通会员时间
	private Long expiredTime;		//会员到期时间
	private Long lastRenewTime;		//最后一次续费时间
	private Long lastRenewAmount;	//最后一次续费金额
	private Long lastOrderNum;		//最后一次续费订单编号
	
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
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Long expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Long getLastRenewTime() {
		return lastRenewTime;
	}
	public void setLastRenewTime(Long lastRenewTime) {
		this.lastRenewTime = lastRenewTime;
	}
	public Long getLastRenewAmount() {
		return lastRenewAmount;
	}
	public void setLastRenewAmount(Long lastRenewAmount) {
		this.lastRenewAmount = lastRenewAmount;
	}
	public Long getLastOrderNum() {
		return lastOrderNum;
	}
	public void setLastOrderNum(Long lastOrderNum) {
		this.lastOrderNum = lastOrderNum;
	}
	
}
