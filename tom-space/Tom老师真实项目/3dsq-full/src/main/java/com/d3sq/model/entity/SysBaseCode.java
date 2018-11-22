package com.d3sq.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统基础代码表
 * 
 */
@Entity
@Table(name = "t_sys_base_code")
public class SysBaseCode implements Serializable {

	private String codeNum; 	// 自定义唯一编码
	private String parentCode;	// 上级代码
	private String codeName; 	// 代码名称
	private String codeType; 	// 代码类型
	private String i18nKey;		// 国际化配置key
	private String remark;		// 备注说明
	
	@Id
	public String getCodeNum() {
		return codeNum;
	}
	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getI18nKey() {
		return i18nKey;
	}
	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}
