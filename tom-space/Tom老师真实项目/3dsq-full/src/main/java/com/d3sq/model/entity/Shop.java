package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 店铺信息表
 *
 */
@Entity
@Table(name="t_shop")
public class Shop implements Serializable {

	private Long id;				//自增主键
	private String name;			//商家（店铺名称）
	private Integer certFlag;		//认证类型(0:未认证，1：个人认证，2：企业认证)
	private String pinyin;			//中文拼音
	private String cityPath;		//所在城市
	private Float lon;				//经度
	private Float lat;				//纬度
	private String coverImg;		//封面图片
	private Float startHours;		//营业开始时间
	private Float endHours;			//营业结束时间
	private String residentialName; //所在小区名称
	private String intro;			//店铺简介
	private String concacts;		//联系人姓名
	private String tel;				//联系电话
	private Long creatorId;			//创建人
	private Long createTime;		//创建时间
	private Integer sysStore;		//系统评分
	private Integer customStore;	//用户评分
	private Integer authFlag;		//是否官方认证
	private Integer policeCertFlag;	//是否公安机关认证
	private Long policeCertId;		//公安机关认证资料信息ID
	private Float serviceRange;		//服务范围（单位：米）
	private	String address;			//详细地址
	private Integer state;			//状态
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResidentialName() {
		return residentialName;
	}
	public void setResidentialName(String residentialName) {
		this.residentialName = residentialName;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
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
	public Integer getSysStore() {
		return sysStore;
	}
	public void setSysStore(Integer sysStore) {
		this.sysStore = sysStore;
	}
	public Integer getCustomStore() {
		return customStore;
	}
	public void setCustomStore(Integer customStore) {
		this.customStore = customStore;
	}
	public Integer getAuthFlag() {
		return authFlag;
	}
	public void setAuthFlag(Integer authFlag) {
		this.authFlag = authFlag;
	}
	public Integer getPoliceCertFlag() {
		return policeCertFlag;
	}
	public void setPoliceCertFlag(Integer policeCertFlag) {
		this.policeCertFlag = policeCertFlag;
	}
	public Long getPoliceCertId() {
		return policeCertId;
	}
	public void setPoliceCertId(Long policeCertId) {
		this.policeCertId = policeCertId;
	}
	public Float getServiceRange() {
		return serviceRange;
	}
	public void setServiceRange(Float serviceRange) {
		this.serviceRange = serviceRange;
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
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public Float getStartHours() {
		return startHours;
	}
	public void setStartHours(Float startHours) {
		this.startHours = startHours;
	}
	public Float getEndHours() {
		return endHours;
	}
	public void setEndHours(Float endHours) {
		this.endHours = endHours;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getConcacts() {
		return concacts;
	}
	public void setConcacts(String concacts) {
		this.concacts = concacts;
	}
	public Integer getCertFlag() {
		return certFlag;
	}
	public void setCertFlag(Integer certFlag) {
		this.certFlag = certFlag;
	}

}
