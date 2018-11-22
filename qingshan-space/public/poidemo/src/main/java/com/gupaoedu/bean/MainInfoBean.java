package com.gupaoedu.bean;

import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 */
public class MainInfoBean implements Serializable {

	private static final long serialVersionUID = -3643440443822221727L;

	private String retailName; // 商户名称
	private String retailAddr; // 商户具体地址
	private String legalName; // 法人代表姓名
	private String retailRegcode; // 商户注册号
	private String primUserPhone; // 主要联系人电话
	private String primUserEmail; // 主要联系人邮箱

	public String getRetailName() {
		return retailName;
	}

	public void setRetailName(String retailName) {
		this.retailName = retailName;
	}

	public String getRetailAddr() {
		return retailAddr;
	}

	public void setRetailAddr(String retailAddr) {
		this.retailAddr = retailAddr;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getRetailRegcode() {
		return retailRegcode;
	}

	public void setRetailRegcode(String retailRegcode) {
		this.retailRegcode = retailRegcode;
	}

	public String getPrimUserPhone() {
		return primUserPhone;
	}

	public void setPrimUserPhone(String primUserPhone) {
		this.primUserPhone = primUserPhone;
	}

	public String getPrimUserEmail() {
		return primUserEmail;
	}

	public void setPrimUserEmail(String primUserEmail) {
		this.primUserEmail = primUserEmail;
	}

}
