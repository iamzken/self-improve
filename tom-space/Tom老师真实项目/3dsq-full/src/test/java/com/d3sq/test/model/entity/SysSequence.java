package com.d3sq.test.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统序列号实体对象
 */
@Entity
@Table(name = "t_sys_sequence")
public class SysSequence implements Serializable {
	
	private static final long serialVersionUID = -5268386010043733809L;
	
	private String businessCode;		//业务代码
	private Boolean hasPrefix;			//是否有前缀
	private String prefixValue;			//前缀
	private Boolean hasSplitFlag;		//是否分隔符
	private String splitFlagValue;		//分隔符
	private Long numLength;				//长度
	private Long currentNum;			//当前序号

	@Id
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public boolean isHasPrefix() {
		return hasPrefix;
	}

	public void setHasPrefix(boolean hasPrefix) {
		this.hasPrefix = hasPrefix;
	}

	public String getPrefixValue() {
		return prefixValue;
	}

	public void setPrefixValue(String prefixValue) {
		this.prefixValue = prefixValue;
	}

	public Boolean isHasSplitFlag() {
		return hasSplitFlag;
	}

	public void setHasSplitFlag(Boolean hasSplitFlag) {
		this.hasSplitFlag = hasSplitFlag;
	}

	public String getSplitFlagValue() {
		return splitFlagValue;
	}

	public void setSplitFlagValue(String splitFlagValue) {
		this.splitFlagValue = splitFlagValue;
	}

	public Long getNumLength() {
		return numLength;
	}

	public void setNumLength(Long numLength) {
		this.numLength = numLength;
	}

	public Long getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Long currentNum) {
		this.currentNum = currentNum;
	}

}
