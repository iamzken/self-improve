package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退货订单
 *
 */
@Entity
@Table(name="t_order_return")
public class OrderReturn implements Serializable {
	
	private Long id;		//自增ID
	private Long orderId;	//订单ID
	private Long productId;	//商品ID
	private Long shopId;	//店铺ID
	private String orderNum;//订单号
	private Long memberId;	//用户ID
	private Long delivTime;	//配送时间
	private String reason;	//退货原因
	private String contacts;//退货联系人姓名
	private String telPhone;//退货联系人手机
	private Float returnAmount;//退款金额
	private Long createTime;//创建时间
	private Integer finishFlag;//是否已完成
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getDelivTime() {
		return delivTime;
	}
	public void setDelivTime(Long delivTime) {
		this.delivTime = delivTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public Float getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Float returnAmount) {
		this.returnAmount = returnAmount;
	}
	public Integer getFinishFlag() {
		return finishFlag;
	}
	public void setFinishFlag(Integer finishFlag) {
		this.finishFlag = finishFlag;
	}
	
	
	
}
