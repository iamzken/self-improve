package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户家庭信息表
 *
 */
@Entity
@Table(name="t_home")
public class Home implements Serializable  {
	
	
	private Long id;				//自增ID
	private Long memberId;			//用户名
	private String cityPath;		//所在城市
	private String roadName;		//主干道路名称
	private String residentinalName;//小区名称
	private String bulidNum;		//楼栋号
	private String unitNum;			//单元号	
	private String roomNum;			//房间号
	private String fullAddr;		//全地址
	private Float lon;				//经度
	private Float lat;				//纬度
	private Integer useNow;			//是否为现在的家
	private Long createTime;		//创建时间
	private Integer state;			//状态
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCityPath() {
		return cityPath;
	}
	public void setCityPath(String cityPath) {
		this.cityPath = cityPath;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getResidentinalName() {
		return residentinalName;
	}
	public void setResidentinalName(String residentinalName) {
		this.residentinalName = residentinalName;
	}
	public String getBulidNum() {
		return bulidNum;
	}
	public void setBulidNum(String bulidNum) {
		this.bulidNum = bulidNum;
	}
	public String getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getFullAddr() {
		return fullAddr;
	}
	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}
	
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Integer getUseNow() {
		return useNow;
	}
	public void setUseNow(Integer useNow) {
		this.useNow = useNow;
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
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	
}
