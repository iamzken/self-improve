package com.d3sq.center.service.impl;

import java.util.List;

import javax.core.common.ResultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.center.service.ICommonService;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.CityDao;
import com.d3sq.model.entity.City;

@Service("commonService")
public class CommonService implements ICommonService {
	@Autowired private CityDao cityDao;

	@Override
	public ResultMsg<?> getCity(String local, Long parentId, String enc) {
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(parentId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		List<City> citys = cityDao.getByParentId(parentId);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",citys);
	}

}
