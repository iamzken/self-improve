package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 站点信息表
 *
 */
@Entity
@Table(name="t_site")
public class Site implements Serializable {
	
   private Long id;				//自增ID
   private Long parentId;		//父级ID
   private String xpath;		//xPath路径
   private String name;			//功能名称
   private String sortName;		//站点简称
   private String logo;			//logo
   private String minLogo;		//小logo
   private String favIcon;		//标题栏logo
   private String title;		//标题栏文字
   private String intro;		//站点简介
   private String domain;		//站点域名
   private String homePage;		//主页地址
   private Integer orderNum;	//排序
   private String config;		//特殊配置
   private Long createTime;		//创建时间
   private Long creatorId;		//创建人
   private Integer state; 		//状态
	   
	
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
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getMinLogo() {
		return minLogo;
	}
	public void setMinLogo(String minLogo) {
		this.minLogo = minLogo;
	}
	public String getFavIcon() {
		return favIcon;
	}
	public void setFavIcon(String favIcon) {
		this.favIcon = favIcon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
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
