package com.d3sq.model.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息
 *
 */
public class ProductItem{
	private Long productId; 	//商品ID
	private String productName; //商品名称
	private String coverImg;//封面图片
	private String unitName;//计量单位
	private Integer stock;  //库存
	private Integer sold;   //已售
	private Float addPrice;	//单价
	private Float price;	//真实价格
	private Float discount;		//折扣率
	private Float disPrice;		//折后价格
	private Integer buyCount;	//购买数量
	private Float totalAmount;	//总金额
	private Integer check;		//是否选中
	//优惠券列表
	private List<Long> coupons = new ArrayList<Long>();
	private Float disStore;	//积分抵扣
	private Long createTime;	//创建时间
	private Integer state;		//状态码
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
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getSold() {
		return sold;
	}
	public void setSold(Integer sold) {
		this.sold = sold;
	}
	public Float getAddPrice() {
		return addPrice;
	}
	public void setAddPrice(Float addPrice) {
		this.addPrice = addPrice;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Float getDisPrice() {
		return disPrice;
	}
	public void setDisPrice(Float disPrice) {
		this.disPrice = disPrice;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<Long> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Long> coupons) {
		this.coupons = coupons;
	}
	public Float getDisStore() {
		return disStore;
	}
	public void setDisStore(Float disStore) {
		this.disStore = disStore;
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
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
}
