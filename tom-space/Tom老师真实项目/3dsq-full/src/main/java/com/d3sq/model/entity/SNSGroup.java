package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 好友分组
 *
 */
@Entity
@Table(name="t_sns_group")
public class SNSGroup implements Serializable {
	
	private Long id;
	private Long memberId;
	private String name;
	private Integer gtype;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGtype() {
		return gtype;
	}
	public void setGtype(Integer gtype) {
		this.gtype = gtype;
	}
	
	
	
	
}
