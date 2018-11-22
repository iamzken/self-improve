package com.gupaoedu.vip.mongo.explorer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "t_member")
public class Member implements Serializable{

	@Id
	private long id;
	private String loginName;
	private String passWord;
	private String nickName;
	private long createTime;
	private int status;
	private String lashLoginIp;
	private long lastLoginTime;

	public Member(){}

	public Member(long id,String loginName, String passWord, String nickName) {
		this.id = id;
		this.loginName = loginName;
		this.passWord = passWord;
		this.nickName = nickName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLashLoginIp() {
		return lashLoginIp;
	}

	public void setLashLoginIp(String lashLoginIp) {
		this.lashLoginIp = lashLoginIp;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}

