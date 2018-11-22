package com.d3sq.model.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺信息
 *
 */
public class ShopItem{
	private Long shopId;		//店铺ID
	private String shopName;    //店铺名称
	private Integer check;		//是否选中
	//商品列表
	private List<ProductItem> products = new ArrayList<ProductItem>();
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public List<ProductItem> getProducts() {
		return products;
	}
	public void setProducts(List<ProductItem> products) {
		this.products = products;
	}
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
