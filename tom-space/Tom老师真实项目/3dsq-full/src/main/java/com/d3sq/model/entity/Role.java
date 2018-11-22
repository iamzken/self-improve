package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色表
 *
 */
@Entity
@Table(name="t_role")
public class Role implements Serializable {

	private Long id;			//自增ID
	private String name;		//角色名称
	private String discription;	//描述信息
	private Integer rootFlag;	//是否超级管理
	private String options;		//权限信息,JSON格式
	private Integer fromSite;	//所属站点
	private String platform;	//所属平台,用于区分是前端用户角色，还是后台用户角色
	private Integer state;		//状态0：无效，1：正常
	private Long creatorId;		//创建人
	private Long createTime;	//创建时间
	
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
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public Integer getRootFlag() {
		return rootFlag;
	}
	public void setRootFlag(Integer rootFlag) {
		this.rootFlag = rootFlag;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public Integer getFromSite() {
		return fromSite;
	}
	public void setFromSite(Integer fromSite) {
		this.fromSite = fromSite;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
