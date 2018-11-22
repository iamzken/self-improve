package com.d3sq.center.vo;

public class PayOrderDetailVo {
	private Long orderId;//主订单id
	private Long productId;//商品id
	private String productName;//商品名称
	private Float price;//单价
	private int buyCount;//购买数量
	private Float payMent;//实际支付单价
	
	
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
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public Float getPayMent() {
		return payMent;
	}
	public void setPayMent(Float payMent) {
		this.payMent = payMent;
	}
	
	
}
