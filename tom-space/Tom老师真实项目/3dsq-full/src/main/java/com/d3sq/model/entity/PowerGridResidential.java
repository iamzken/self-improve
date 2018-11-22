package com.d3sq.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电网公司与小区关系表
 *
 */
@Entity
@Table(name="t_power_gird_residential")
public class PowerGridResidential {
	
	private Long id;			//自增ID
	private Long powerGridId;	//电网公司ID
	private Long residentialId;	//小区ID
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPowerGridId() {
		return powerGridId;
	}
	public void setPowerGridId(Long powerGridId) {
		this.powerGridId = powerGridId;
	}
	public Long getResidentialId() {
		return residentialId;
	}
	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}
	
}
