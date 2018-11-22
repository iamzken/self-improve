package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 功能菜单表
 *
 */
@Entity
@Table(name="t_tree_menu")
public class TreeMenu implements Serializable {

   private Long id;				//自增主键
   private String parentId;		//父级ID
   private Long xpath;			//xpath
   private String name;			//菜单名称
   private String orderNum;		//排序
   private Long url;			//功能地址
   private Long icon;			//功能图标
   private String attributes;	//自定义属性
   private String actions;		//权限代码
   private String platform;		//所属平台
   private Long createTime;		//创建日期
   private Long creatorId;		//创建人
   private Integer state;		//状态
	   
	   
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Long getXpath() {
		return xpath;
	}
	public void setXpath(Long xpath) {
		this.xpath = xpath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Long getUrl() {
		return url;
	}
	public void setUrl(Long url) {
		this.url = url;
	}
	public Long getIcon() {
		return icon;
	}
	public void setIcon(Long icon) {
		this.icon = icon;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getActions() {
		return actions;
	}
	public void setActions(String actions) {
		this.actions = actions;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
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
	   
	   
	
}
