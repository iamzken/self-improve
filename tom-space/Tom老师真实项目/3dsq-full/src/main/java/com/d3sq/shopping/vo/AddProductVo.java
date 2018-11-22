package com.d3sq.shopping.vo;

public class AddProductVo {
	private String productName;    //商品名
	private Long kindId;           //商品分类id
	private String kindPath;       //商品分类path
	private String brand;          //品牌
	private String unitName;       //规格
	private Float price;           //单价
	private String intro;          //介绍
	private String coverImg;       //封面图片
	private Integer stock;         //库存
	private String photos;         //图文详情
	private Long shopId;           //店铺id

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getKindId() {
		return kindId;
	}

	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	public String getKindPath() {
		return kindPath;
	}

	public void setKindPath(String kindPath) {
		this.kindPath = kindPath;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	
}
