package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 快递公司目录(包含服务网点)
 *
 */
@Entity
@Table(name="t_express")
public class Express implements Serializable{
	
	private Long id;			//自增ID
	private Long parentId;		//上级ID
	private String xpath;		//xpath
	private String cityPath;	//所在城市
	private String num;			//全国快递公司统一编码
	private String name;		//快递公司名称
	private String shortName;	//快递公司简称
	private String pinyin;		//拼音
	private String homePage;	//官方网址
	private String queryAPI;	//快递单号查询接口
	private String address;		//地址
	private String tel;			//联系电话
	private String contacts;	//负责人
	private Float lon;			//经度
	private Float lat;			//纬度
	private Long creatorId;		//创建人
	private Long createTime;	//创建时间
	private Integer hotStore;	//公司热度
	private Integer state;		//状态码
	
	
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
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	
	public String getQueryAPI() {
		return queryAPI;
	}
	public void setQueryAPI(String queryAPI) {
		this.queryAPI = queryAPI;
	}
	public Integer getHotStore() {
		return hotStore;
	}
	public void setHotStore(Integer hotStore) {
		this.hotStore = hotStore;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
