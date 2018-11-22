package com.d3sq.admin.service.impl;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;
import javax.core.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d3sq.admin.dao.CityDao;
import com.d3sq.admin.dao.ResidentialDao;
import com.d3sq.admin.service.IResidentialService;
import com.d3sq.admin.vo.ResidentialVo;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.City;
import com.d3sq.model.entity.Residential;
import com.d3sq.search.plugin.ResidentialIndexPlugin;


@Service("aResidentialService")
public class ResidentialService implements IResidentialService{

	@Autowired private ResidentialDao aResidentialDao;
	@Autowired private CityDao aCityDao;
	@Autowired private ResidentialIndexPlugin residentialPlugin;

	@Override
	public ResultMsg<?> add(String local,Long creator,ResidentialVo param,String enc) {
		
		Residential entity = new Residential();
		
		if(StringUtils.isEmpty(param.getShortName()) || 
			StringUtils.isEmpty(param.getCityNamePath()) || 
			StringUtils.isEmpty(param.getAddress()) || 
			null == param.getLat() ||
			null == param.getLon()){
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整");
		}
		
		if(StringUtils.isEmpty(param.getName())){
			param.setName(param.getShortName());
		}
		
		DataUtils.copySimpleObject(param, entity);
		
		if(!StringUtils.isEmpty(entity.getCityNamePath())){
			String[] citys = entity.getCityNamePath().split("/");
			if(!"中国".equals(citys[0])){
				entity.setCityNamePath("/中国/" + entity.getCityNamePath());
			}
			if(!entity.getCityNamePath().endsWith("/")){
				entity.setCityNamePath(entity.getCityNamePath() + "/");
			}
			if(!entity.getCityNamePath().startsWith("/")){
				entity.setCityNamePath("/" + entity.getCityNamePath());
			}
			
			City city = aCityDao.selectOne(citys[citys.length - 1]);
			if(city != null){
				entity.setCityPath(city.getXpath());
			}
		}
		
		Residential r = aResidentialDao.selectByNameForCity(entity.getCityPath(), entity.getShortName());
		if(null != r){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "已存在相同名字的小区");
		}
		
		entity.setLat(Float.valueOf(param.getLat()));
		entity.setLon(Float.valueOf(param.getLon()));
		entity.setPinyin(PinyinUtil.converterToSpell(entity.getName()).split(",")[0]);
		entity.setState(SystemConstant.ENABLE);
		entity.setCreateTime(System.currentTimeMillis());
		entity.setCreatorId(creator);
		
		Long id = aResidentialDao.insertAndReturnId(entity);
		entity.setId(id);
		if(id > 0){
			entity.setId(id);
			QueueItem item = new QueueItem(residentialPlugin,IndexConstant.RESIDENTIAL_SEARCH_INDEX, QueueTarget.OPT_ADD, id, entity);
			residentialPlugin.push(item);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "创建成功",entity);
		}else{
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "创建失败");
		}
	}

	
	
}
