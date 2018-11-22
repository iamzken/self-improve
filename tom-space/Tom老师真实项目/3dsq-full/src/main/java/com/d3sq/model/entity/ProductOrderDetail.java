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
@Table(name="t_product_order_detail")
public class ProductOrderDetail implements Serializable {
	
	private Long id;//主键id
	private Long orderId;//主订单编号
	private Long productId;//商品id
	private String productName;//商品名称
	private Float price;//单价
	private Integer buyCount;//购买数量
	private Float payMent;//实际支付单价
	//private Long couponId;		//优惠劵
	//private Float disStore;		//积分抵扣
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public Float getPayMent() {
		return payMent;
	}
	public void setPayMent(Float payMent) {
		this.payMent = payMent;
	}
	/*public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Float getDisStore() {
		return disStore;
	}
	public void setDisStore(Float disStore) {
		this.disStore = disStore;
	}*/
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
