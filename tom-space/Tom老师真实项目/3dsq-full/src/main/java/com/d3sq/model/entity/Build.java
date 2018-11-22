package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 小区楼栋、单元、楼层信息登记表
 *
 */
@Entity
@Table(name="t_build")
public class Build implements Serializable{
	
	private Long id;			//自增ID
	private Long residentialId;	//小区ID
	private String xpath;		//xpath
	private Long parentId;		//父级ID
	private String name;		//名称
	private String floorCount;	//楼层数量
	private String levelType;	//层级信息
	private Integer state;		//状态码
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFloorCount() {
		return floorCount;
	}
	public void setFloorCount(String floorCount) {
		this.floorCount = floorCount;
	}
	
}
