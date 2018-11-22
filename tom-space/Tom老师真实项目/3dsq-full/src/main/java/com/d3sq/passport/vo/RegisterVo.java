package com.d3sq.passport.vo;

import com.d3sq.model.entity.Member;

public class RegisterVo extends Member{

	@Override
	public String getIdCardNum() {
		return null;
	}

	@Override
	public void setIdCardNum(String idCardNum) {
		super.setIdCardNum(null);
	}

	
	
	
	
}
