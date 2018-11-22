package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 团购订单
 *
 */
@Entity
@Table(name="t_tuan_order")
public class TuanOrder implements Serializable{
	
	private Long id;	//自增ID

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
