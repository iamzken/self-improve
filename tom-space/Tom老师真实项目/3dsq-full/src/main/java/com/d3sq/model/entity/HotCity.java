package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * 热门城市
 *
 */
public class HotCity implements Serializable {
	
	
	private Long id;			//自增ID
	private Long cityId;		//城市ID
	private String cityPath;	//城市路径
	private Integer hotStore;	//热度评分
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	public Integer getHotStore() {
		return hotStore;
	}
	public void setHotStore(Integer hotStore) {
		this.hotStore = hotStore;
	}
	
}
