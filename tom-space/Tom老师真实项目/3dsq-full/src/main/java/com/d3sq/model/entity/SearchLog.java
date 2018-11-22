package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户搜索日志分析
 *
 */
@Entity
@Table(name="t_search_log")
public class SearchLog implements Serializable {
	
	private Long id;		//自增ID
	private String keyword;	//关键词
	private Float lon;		//经度
	private Float lat;		//纬度
	private String ip;		//IP
	private Long searchTime;//搜索时间
	private Long memberId;	//用户ID
	private Integer stype;	//搜索类型
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getSearchTime() {
		return searchTime;
	}
	public void setSearchTime(Long searchTime) {
		this.searchTime = searchTime;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getStype() {
		return stype;
	}
	public void setStype(Integer stype) {
		this.stype = stype;
	}
	
}
