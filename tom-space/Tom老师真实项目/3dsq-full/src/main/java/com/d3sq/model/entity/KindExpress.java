package com.d3sq.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 快递公司分类
 *
 */
@Entity
@Table(name="t_kind_express")
public class KindExpress {
	private Long id;		//自增ID
	private Long kindId;	//分类ID
	private Long expressId;	//快递公司ID
	private Long kindPath;	//分类路径
	private Integer customFlag;//是否为自定义分类
	
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getKindId() {
		return kindId;
	}
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}
	public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	public Long getKindPath() {
		return kindPath;
	}
	public void setKindPath(Long kindPath) {
		this.kindPath = kindPath;
	}
	public Integer getCustomFlag() {
		return customFlag;
	}
	public void setCustomFlag(Integer customFlag) {
		this.customFlag = customFlag;
	}
	
}
