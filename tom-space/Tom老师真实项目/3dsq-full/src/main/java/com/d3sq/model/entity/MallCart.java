package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 购物车信息表
 *
 */
@Entity
@Table(name="t_mall_cart")
public class MallCart  implements Serializable{

	private Long id;			//自增ID
	private Long memberId;		//用户ID
	private String shops;		//购物车信息（JSON数组，参考CartItem定义）

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

	public String getShops() {
		return shops;
	}

	public void setShops(String shops) {
		this.shops = shops;
	}

}
