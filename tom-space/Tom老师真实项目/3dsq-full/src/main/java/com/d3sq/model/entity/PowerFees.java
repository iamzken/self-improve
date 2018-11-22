package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电费缴纳
 *
 */
@Entity
@Table(name="t_power_fees")
public class PowerFees implements Serializable{
	
	private Long id;			//自增ID
	private Long residentialId;	//小区ID
	private Long roomId;		//住户ID
	private String orderNum;	//订单编号
	private Float fees;			//费用
	private Float price;		//单价
	private Float account;		//计费电量
	private Float discount;		//折扣
	private Long startTime;		//计费开始时间
	private Long endTime;		//计费结束时间
	private Integer finishFlag;	//是否已完成
	private Long finishTime;	//完成时间
	private String tel;			//联系电话
	private String ownerName;	//业主姓名
	private Long createTime;	//创建时间
	private Integer state;		//状态码
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Float getFees() {
		return fees;
	}
	public void setFees(Float fees) {
		this.fees = fees;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getAccount() {
		return account;
	}
	public void setAccount(Float account) {
		this.account = account;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getFinishFlag() {
		return finishFlag;
	}
	public void setFinishFlag(Integer finishFlag) {
		this.finishFlag = finishFlag;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	
}
