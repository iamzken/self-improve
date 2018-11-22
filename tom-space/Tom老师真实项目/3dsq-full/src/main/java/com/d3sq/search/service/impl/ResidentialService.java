package com.d3sq.search.service.impl;

import java.util.List;

import javax.core.common.ResultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.model.entity.City;
import com.d3sq.model.entity.Residential;
import com.d3sq.search.dao.CityDao;
import com.d3sq.search.dao.ResidentialDao;
import com.d3sq.search.service.IResidentialService;


@Service("sResidentialService")
public class ResidentialService implements IResidentialService{

	@Autowired private ResidentialDao sResidentialDao;
	@Autowired private CityDao sCityDao;
	
	public ResultMsg<?> getById(Long id){
		try{
			Residential r = sResidentialDao.selectById(id);
			
			String cityAddr = "/";
			if(r != null){
				List<City> citys = sCityDao.selectByPath(r.getCityPath());
				for (int i = 0; i < citys.size(); i ++) {
					cityAddr += citys.get(i).getName();
					cityAddr += "/";
				}
			}
			JSONObject result = JSON.parseObject(JSON.toJSONString(r));
			result.put("cityNamePath", cityAddr);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"",result);
		}catch(Exception e){
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"获取失败");
		}
	}
	
}
