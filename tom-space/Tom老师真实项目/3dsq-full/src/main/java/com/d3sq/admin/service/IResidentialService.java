package com.d3sq.admin.service;

import javax.core.common.ResultMsg;

import com.d3sq.admin.vo.ResidentialVo;
import com.d3sq.model.entity.Residential;

public interface IResidentialService {

	public ResultMsg<?> add(String local,Long creator,ResidentialVo entity,String enc);
}
