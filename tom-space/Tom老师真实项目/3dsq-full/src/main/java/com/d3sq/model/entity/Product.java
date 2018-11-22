package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 商品信息表
 *
 */
@Entity
@Table(name="t_product")
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;    	//主键id
	private Long shopId;	//店铺ID
	private Integer ptype;	//商品类型
	private String name;	//商品名称
	private String pinyin;	//中文拼音
	private String intro;	//商品介绍
	private String coverImg;//封面图片
	private Float price; 	//商品价格
	private String unitName;//计量单位
	private Float sale;		//折后价格
	private Integer stock;  //库存
	private Integer sold;   //已售
	private Long createTime;//添加时间
	private Long proDate;	//生产日期
	private Long keepDate;	//保质期
	private String photos;	//商品照片（JSON 数组，详见ImageItem中定义）
	private Integer able; 	//是否有效（1：表示已删或下架，0：表示正常）
	private String brand;	//品牌
	private String barCode;	//商品条形码
	private Integer state;	//状态码
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getSale() {
		return sale;
	}
	public void setSale(Float sale) {
		this.sale = sale;
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getAble() {
		return able;
	}
	public void setAble(Integer able) {
		this.able = able;
	}
	public Long getProDate() {
		return proDate;
	}
	public void setProDate(Long proDate) {
		this.proDate = proDate;
	}
	public Long getKeepDate() {
		return keepDate;
	}
	public void setKeepDate(Long keepDate) {
		this.keepDate = keepDate;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
}
