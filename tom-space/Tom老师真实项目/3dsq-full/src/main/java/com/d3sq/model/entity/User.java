package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 后台用户信息表
 *
 */
@Entity
@Table(name="t_user")
public class User implements Serializable {
	
	private Long id;				//自增ID
	private Long roleId; 			//角色ID
	private String loginName;		//用户账号
	private String loginPass;		//用户密码(MD5加密长32位)
	private String realName;		//真实姓名
	private Long createTime;		//创建时间
	private Integer sex = 1;		//性别（0：女；1：男；2：保密）
	private String province;		//所属省份
	private String city;			//所属城市
	private String area;			//所属区县
	private Long companyId;			//所属企业
	private String email;			//电子邮箱
	private Long loginCount = 0L;	//登录次数
	private String lastLoginIp;		//最后登录IP
	private Long lastLoginTime;		//最后登录时间
	private String lastLoginAddr;	//最后登录地址
	private String remark;			//备注
	private Integer state = 1;		//用户状态（0：不可用；1：可用；2：已禁用；-1：已删除）
	   
	   
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getLastLoginAddr() {
		return lastLoginAddr;
	}
	public void setLastLoginAddr(String lastLoginAddr) {
		this.lastLoginAddr = lastLoginAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	 
}
