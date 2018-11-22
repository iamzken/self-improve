package com.d3sq.shopping.vo;

public class AddServiceVo {
	private String serviceName;    //服务名称
	private Long shopId;           //店铺id
	private Long kindId;           //商品分类id
	private String kindPath;       //商品分类path
	private String feeStand;       // 收费标准
	private String coverImg;       //封面图片
	private String photos;         //图文详情
	private String timesDual;      //服务时段
	private String content;	       //服务内容
	private String intro;	       //详情介绍
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
	public String getFeeStand() {
		return feeStand;
	}
	public void setFeeStand(String feeStand) {
		this.feeStand = feeStand;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
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
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}
