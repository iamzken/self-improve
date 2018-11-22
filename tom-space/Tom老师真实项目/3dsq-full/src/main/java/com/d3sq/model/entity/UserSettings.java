package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 用户个性化设置
 *
 */
@Entity
@Table(name="t_user_settings")
public class UserSettings implements Serializable{

	private Long id;		//自增主键
	private	Long memberId;	//用户ID
	private String settings;//用户配置（JSON对象）
	
	
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
	public String getSettings() {
		return settings;
	}
	public void setSettings(String settings) {
		this.settings = settings;
	}
	
}
