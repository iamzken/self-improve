package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 快递订单
 *
 */
@Entity
@Table(name="t_ex_order")
public class ExOrder implements Serializable {
	
	private Long id;			//自增ID
	private String fromAddrId;	//寄件地址ID
	private String toAddrId;	//收件地址ID
	private String sender;		//寄件人
	private String receiver;	//收件人
	private String fromAddr;	//寄件人地址
	private String toAddr;		//收件人地址
	private String fromZip;		//寄件人邮编
	private String toZip;		//收件人邮编
	private String senderTel;	//寄件人电话
	private String receiverTel;	//收件人电话
	private Long expressId;		//快递公司ID
	private String expressName;	//快递公司名称
	private String expressNum;	//快递单号
	private Long itemKindId;	//物品分类ID
	private String itemName;	//物品名称
	private Float itemWeight;	//物品重量
	private Float itemLong;		//物品长
	private Float itemWide;		//物品宽
	private Float itemHigh;		//物品高
	private Integer feesFlag;	//计费方式（1：按重量计费，2：按体积计费）
	private Integer itemCount;	//件内数量
	private Integer finish;		//是否已经完成（1:已完成，0：未完成)
	private Integer postState;	//订单状态（1：取件中，2：已打包，3：运送中，4：到达目的地，5：派件中，6：已完成）
	private Integer accessFlag;	//寄件方式（1：上门取件，2：自助送件）
	private Integer payRecvFlag;//是否到付
	private Integer tomorrowFlag;//是否次日达
	private Long creatorId;		//下单人
	private Long createTime;	//下单时间
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFromAddrId() {
		return fromAddrId;
	}
	public void setFromAddrId(String fromAddrId) {
		this.fromAddrId = fromAddrId;
	}
	public String getToAddrId() {
		return toAddrId;
	}
	public void setToAddrId(String toAddrId) {
		this.toAddrId = toAddrId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getFromAddr() {
		return fromAddr;
	}
	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}
	public String getToAddr() {
		return toAddr;
	}
	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}
	public String getFromZip() {
		return fromZip;
	}
	public void setFromZip(String fromZip) {
		this.fromZip = fromZip;
	}
	public String getToZip() {
		return toZip;
	}
	public void setToZip(String toZip) {
		this.toZip = toZip;
	}
	public String getSenderTel() {
		return senderTel;
	}
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNum() {
		return expressNum;
	}
	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Float getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(Float itemWeight) {
		this.itemWeight = itemWeight;
	}
	public Float getItemLong() {
		return itemLong;
	}
	public void setItemLong(Float itemLong) {
		this.itemLong = itemLong;
	}
	public Float getItemWide() {
		return itemWide;
	}
	public void setItemWide(Float itemWide) {
		this.itemWide = itemWide;
	}
	public Float getItemHigh() {
		return itemHigh;
	}
	public void setItemHigh(Float itemHigh) {
		this.itemHigh = itemHigh;
	}
	public Integer getFeesFlag() {
		return feesFlag;
	}
	public void setFeesFlag(Integer feesFlag) {
		this.feesFlag = feesFlag;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	
	public Integer getTomorrowFlag() {
		return tomorrowFlag;
	}
	public void setTomorrowFlag(Integer tomorrowFlag) {
		this.tomorrowFlag = tomorrowFlag;
	}
	public Long getItemKindId() {
		return itemKindId;
	}
	public void setItemKindId(Long itemKindId) {
		this.itemKindId = itemKindId;
	}
	public Integer getPostState() {
		return postState;
	}
	public void setPostState(Integer postState) {
		this.postState = postState;
	}
	public Integer getAccessFlag() {
		return accessFlag;
	}
	public void setAccessFlag(Integer accessFlag) {
		this.accessFlag = accessFlag;
	}
	public Integer getPayRecvFlag() {
		return payRecvFlag;
	}
	public void setPayRecvFlag(Integer payRecvFlag) {
		this.payRecvFlag = payRecvFlag;
	}
	
}
