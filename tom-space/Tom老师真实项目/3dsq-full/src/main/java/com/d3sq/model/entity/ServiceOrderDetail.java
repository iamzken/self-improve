package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单详细表
 *
 */
@Entity
@Table(name="t_service_order_detail")
public class ServiceOrderDetail implements Serializable {
	
	private Long id;		//主键id
	private Long orderId;	//主订单编号
	private Long serviceId;	//服务ID
	private String serviceName;//服务名称
	private Float price;	//订单价格
	private String feeStand;	//服务内容(JSON数组，参考RuleItem)
	private String timesDual;//服务时段(JSON数组,参考TimeDualItem)
	private String content; // 服务介绍
	private Float payMent;	//实际支付单价
	//private Long couponId;		//优惠劵
	//private Float disStore;		//积分抵扣
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Float getPayMent() {
		return payMent;
	}
	public void setPayMent(Float payMent) {
		this.payMent = payMent;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getFeeStand() {
		return feeStand;
	}
	public void setFeeStand(String feeStand) {
		this.feeStand = feeStand;
	}
	public String getTimesDual() {
		return timesDual;
	}
	public void setTimesDual(String timesDual) {
		this.timesDual = timesDual;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
