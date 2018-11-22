package com.d3sq.test.model.entity;

import java.io.Serializable;

public class Shop implements Serializable{

	private long id;
	
	private String name;
	
	private double lat;// 纬度
	
	private double lon;// 经度
	
//	private double[] location;// hashcode
	
	public Shop(Long id, String name, double lat, double lon) {
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
//		location = new double[]{lat,lon};
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public double[] getLocation() {
//		return location;
//	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

//	public void setLocation(double[] location) {
//		this.location = location;
//	}
	
}
