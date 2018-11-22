package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 小区业主信息
 *
 */
@Entity
@Table(name="t_owner")
public class Owner implements Serializable{
	
	private Long id;			//自增ID
	private Long roomId;		//房间号
	private String name;		//业主姓名
	private String propertyNum;	//物业服务卡号
	private String idCardImg;	//身份证电子版
	private String idCardNum;	//身份证号码
	private String tel;			//手机号
	private Integer sex;		//性别
	private Integer holderFlag;	//是否为户主
	private Long createTime;	//录入时间
	private Long buyTime;		//购买时间
	private Long handoverTime;	//交房时间
	private Long checkinTime;	//入住时间
	private Integer state;		//状态码
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPropertyNum() {
		return propertyNum;
	}
	public void setPropertyNum(String propertyNum) {
		this.propertyNum = propertyNum;
	}
	public String getIdCardImg() {
		return idCardImg;
	}
	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getHolderFlag() {
		return holderFlag;
	}
	public void setHolderFlag(Integer holderFlag) {
		this.holderFlag = holderFlag;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Long buyTime) {
		this.buyTime = buyTime;
	}
	public Long getHandoverTime() {
		return handoverTime;
	}
	public void setHandoverTime(Long handoverTime) {
		this.handoverTime = handoverTime;
	}
	public Long getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Long checkinTime) {
		this.checkinTime = checkinTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
