package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 居民小区
 * @author tanyongde
 *
 */
@Entity
@Table(name="t_residential")
public class Residential implements Serializable{
	
	private Long id;			//自增ID
	private String name;		//小区名称
	private String shortName;	//小区简称
	private String cityPath;	//所在城市（城市代码组成的xpath）
	private String cityNamePath;//所在城市的简称
	private Float lat;			//纬度
	private Float lon;			//经度
	private String pinyin;		//小区名称中文拼音
	private String address;		//小区详细地址
	private Long createTime;	//录入时间
	private Long creatorId;		//录入人
	private Integer state;		//状态
	
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public String getCityNamePath() {
		return cityNamePath;
	}
	public void setCityNamePath(String cityNamePath) {
		this.cityNamePath = cityNamePath;
	}
	
}
