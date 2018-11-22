package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 城市信息表
 *
 */
@Entity
@Table(name="t_city")
public class City implements Serializable {
	
	private Long id;			//自增ID
	private String name;		//省市区名称
	private Long parentId;		//上级ID
	private String xpath;		//xpath
	private String shortName;	//简称
	private Integer levelType;	//级别0:中国，1：省份，2：市，3：区、县
	private String cityCode;	//城市代码（电话区号）
	private String zipCode;		//邮政编码
	private Float lon;			//经度
	private Float lat;			//纬度
	private String pinyin;		//拼音
	private Integer state;	 	//状态，1：可用，0：禁用
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getLevelType() {
		return levelType;
	}
	public void setLevelType(Integer levelType) {
		this.levelType = levelType;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	
}
