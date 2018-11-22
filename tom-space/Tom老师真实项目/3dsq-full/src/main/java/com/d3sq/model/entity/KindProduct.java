package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品分类表
 *
 */
@Entity
@Table(name="t_kind_product")
public class KindProduct  implements Serializable{
	private Long id;		//自增ID
	private Long kindId;	//分类ID
	private Long productId;	//商品ID
	private String kindPath;	//分类路径
	private Integer customFlag;	//是否为自定义分类
	
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
