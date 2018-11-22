package com.d3sq.admin.vo;

public class ResidentialVo {
	
	private String name;		//小区名称
	private String shortName;	//小区简称
	private String cityNamePath;//所在城市的简称
	private String lat;			//纬度
	private String lon;			//经度
	private String address;		//小区详细地址
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
	public String getCityNamePath() {
		return cityNamePath;
	}
	public void setCityNamePath(String cityNamePath) {
		this.cityNamePath = cityNamePath;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
