package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息表
 *
 */
@Entity
@Table(name="t_member")
public class Member implements Serializable{
	
	private Long id;			//自增ID
	private Integer mtype; 		//用户类型1：手机号，2:微信号，3：QQ号，4：新浪微博
	private String loginName;	//登录账号
	private String loginPass;	//登录密码（MD5二次加密，32位）
	private String realName;	//真实姓名
	private String nickName;	//昵称
	private String photo;		//头像
	private String tel;			//手机号
	private Integer sex;		//性别
	private Long store;			//用户积分
	private Integer level;		//用户等级
	private Long loginCount;	//登录次数
	private String lastLoginIp;	//最后登录IP
	private Long lastLoginTime;	//最后登录时间
	private String lastLoginLocation;//最后登录地址
	private Long fromSite;		//所属站点
	private Integer vipFlag;	//是否会员
	private String idCardNum;	//身份证号
	private String idCardImgs;	//身份证电子件
	private String licenseImgs;	//营业执照、组织机构代码、税务登记
	private String licenseNum;	//执照编码
	private Integer certFlag;	//证书类型(0:未提交，1：个人认证，2：企业认证）
	private Integer auditFlag;	//用户资料审核状态(0:未审核，1：审核通过，-1:审核未通过）
	private Long createTime;	//注册时间
	private Integer state;		//用户状态0：不可用，1：正常，2：已禁用，3：已删除
	private String remark;		//备注
	private String thirdInfo;	//第三方登录获取的用户信息
	private String modified;	//修改过的字段（JSON格式）
	private Long bindId;		//绑定账号
	private Integer shopCount;	//已开通店铺个数
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMtype() {
		return mtype;
	}
	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPass() {
		return loginPass;
	}
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getStore() {
		return store;
	}
	public void setStore(Long store) {
		this.store = store;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Long getFromSite() {
		return fromSite;
	}
	public void setFromSite(Long fromSite) {
		this.fromSite = fromSite;
	}
	public Integer getVipFlag() {
		return vipFlag;
	}
	public void setVipFlag(Integer vipFlag) {
		this.vipFlag = vipFlag;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getIdCardImgs() {
		return idCardImgs;
	}
	public void setIdCardImgs(String idCardImgs) {
		this.idCardImgs = idCardImgs;
	}
	public String getLicenseImgs() {
		return licenseImgs;
	}
	public void setLicenseImgs(String licenseImgs) {
		this.licenseImgs = licenseImgs;
	}
	public String getLicenseNum() {
		return licenseNum;
	}
	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getThirdInfo() {
		return thirdInfo;
	}
	public void setThirdInfo(String thirdInfo) {
		this.thirdInfo = thirdInfo;
	}
	public Long getBindId() {
		return bindId;
	}
	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLastLoginLocation() {
		return lastLoginLocation;
	}
	public void setLastLoginLocation(String lastLoginLocation) {
		this.lastLoginLocation = lastLoginLocation;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public Integer getShopCount() {
		return shopCount;
	}
	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}
	public Integer getCertFlag() {
		return certFlag;
	}
	public void setCertFlag(Integer certFlag) {
		this.certFlag = certFlag;
	}
	public Integer getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}
	
	
}
