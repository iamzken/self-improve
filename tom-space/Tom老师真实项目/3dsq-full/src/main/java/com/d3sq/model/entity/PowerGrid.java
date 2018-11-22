package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 国家电网公司名录
 *
 */
@Entity
@Table(name="t_power_grid")
public class PowerGrid implements Serializable{
	
	private Long id;		//自增ID
	private Long parentId;	//父级ID
	private String xpath;	//xpath
	private String cityPath;//所在城市
	private String num;		//统一编号
	private String name;	//电网公司名称
	private String shortName;//电网公司简称
	private Float lat;		//纬度
	private Float lon;		//经度
	private String address;	//地址
	private Integer state;	//状态码
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	
}
