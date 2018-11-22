package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公安机关备案信息
 *
 */
@Entity
@Table(name="t_police_cert")
public class PoliceCert implements Serializable  {
	
	
	private Long id;		//自增ID
	private String title;	//备案名称
	private String certNum;	//备案号
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	
	
}
