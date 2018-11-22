package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物业保修订单
 *
 */
@Entity
@Table(name="t_repa_order")
public class RepaOrder implements Serializable{
	
	private Long id;			//自增ID
	private String num;			//订单号
	private Long residentialId;	//小区ID
	private Long roomId;		//房间ID
	private Long propertyId;	//物业公司ID
	private String roomName;	//房间名称
	private String ownerName;	//联系人
	private String tel;			//联系手机
	private String content;		//保修内容
	private Long appointTime;	//预约上门时间
	private Integer nowFlag;	//是否立即上门
	private Long createTime;	//下单时间
	private Integer state;		//状态码
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(Long appointTime) {
		this.appointTime = appointTime;
	}
	public Integer getNowFlag() {
		return nowFlag;
	}
	public void setNowFlag(Integer nowFlag) {
		this.nowFlag = nowFlag;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
}
