package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 服务与商品关系表
 *
 */
@Entity
@Table(name="t_kind_service")
public class KindService implements Serializable{

	private Long id;		//自增ID
	private Long kindId;	//分类ID
	private String kindPath;	//分类路径
	private Long serviceId;	//服务ID
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
	public String getKindPath() {
		return kindPath;
	}
	public void setKindPath(String kindPath) {
		this.kindPath = kindPath;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getCustomFlag() {
		return customFlag;
	}
	public void setCustomFlag(Integer customFlag) {
		this.customFlag = customFlag;
	}
	
}
