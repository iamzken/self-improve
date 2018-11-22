package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 搜索关键词条目表
 *
 */
@Entity
@Table(name="t_keyword")
public class Keyword implements Serializable {

	private Long id;					//自增ID
	private String keyword;				//搜索关键词
	private Integer hotStore;			//词条热度评分
	private String lastSearchLocation;	//最后一次搜索的位置
	private String lastSearchIp;		//最后一次搜索的IP
	private Long lastSearchTime;		//最后一次搜索的时间
	private Long createTime;			//词条创建时间
	private Integer state;				//词条状态
	
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
	public Integer getHotStore() {
		return hotStore;
	}
	public void setHotStore(Integer hotStore) {
		this.hotStore = hotStore;
	}
	public String getLastSearchLocation() {
		return lastSearchLocation;
	}
	public void setLastSearchLocation(String lastSearchLocation) {
		this.lastSearchLocation = lastSearchLocation;
	}
	public String getLastSearchIp() {
		return lastSearchIp;
	}
	public void setLastSearchIp(String lastSearchIp) {
		this.lastSearchIp = lastSearchIp;
	}
	public Long getLastSearchTime() {
		return lastSearchTime;
	}
	public void setLastSearchTime(Long lastSearchTime) {
		this.lastSearchTime = lastSearchTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
