package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 店铺分类
 *
 */
@Entity
@Table(name="t_kind_shop")
public class KindShop  implements Serializable{
	private Long id;		//自增ID
	private Long shopId;	//店铺ID
	private Long kindId;	//分类ID
	private String kindPath;//分类xpath
	private Integer customFlag;//是否为自定义分类
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
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
	public Integer getCustomFlag() {
		return customFlag;
	}
	public void setCustomFlag(Integer customFlag) {
		this.customFlag = customFlag;
	}
	
}
