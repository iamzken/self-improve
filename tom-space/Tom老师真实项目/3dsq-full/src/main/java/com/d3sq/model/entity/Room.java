package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 小区住房信息
 *
 */
@Entity
@Table(name="t_room")
public class Room implements Serializable{
	
	private Long id;			//自增ID
	private Long residentialId;	//小区ID
	private Long buildPath;		//楼栋ID
	private String buildName;	//楼栋号
	private String unitName;	//单元号
	private String floorName;	//楼层号
	private String name;		//房间名称
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getBuildPath() {
		return buildPath;
	}
	public void setBuildPath(Long buildPath) {
		this.buildPath = buildPath;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	
}
