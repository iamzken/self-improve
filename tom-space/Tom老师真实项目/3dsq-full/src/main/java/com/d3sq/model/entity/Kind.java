package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 条目分类表
 *
 */
@Entity
@Table(name="t_kind")
public class Kind implements Serializable {

   private Long id;				//自增ID
   private Long parentId;		//父级ID
   private String xpath;		//xpath
   private String name;			//分类名称
   public Integer orderNum;		//排序
   private String icon;			//功能图标
   private String attributes; 	//自定义属性
   private String platform;		//所属平台
   private Long fromSite;		//所属站点
   private String alias;		//分类别名（例如店铺分类，商品分类，见FieldConstants中定义）
   private Long createTime;		//创建时间
   private Long creatorId; 		//创建人
   private Integer state;		//状态
	   
	   
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public Long getFromSite() {
		return fromSite;
	}
	public void setFromSite(Long fromSite) {
		this.fromSite = fromSite;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	   
}
