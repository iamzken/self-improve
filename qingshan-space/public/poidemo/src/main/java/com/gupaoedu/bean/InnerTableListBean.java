package com.gupaoedu.bean;

/**
 * 咕泡学院，只为更好的你
 */
public class InnerTableListBean implements java.io.Serializable {

	private static final long serialVersionUID = 8251719130581747353L;

	private String sn; // 序号
	private String storeRegCode; // 门店注册号
	private String storeName; // 门店名称
	private String storeAddr; // 门店地址
	private String primUserPhone; // 负责人联系电话

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStoreRegCode() {
		return storeRegCode;
	}

	public void setStoreRegCode(String storeRegCode) {
		this.storeRegCode = storeRegCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public String getPrimUserPhone() {
		return primUserPhone;
	}

	public void setPrimUserPhone(String primUserPhone) {
		this.primUserPhone = primUserPhone;
	}

}
