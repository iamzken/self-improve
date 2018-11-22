package com.d3sq.search.service.impl;

import java.util.ArrayList;

import javax.core.common.ResultMsg;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.center.vo.ESVo;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.search.service.INearbyService;
import com.d3sq.search.utils.ES;

/**
 * 附近搜索服务
 *
 */
@Service
public class NearbyService implements INearbyService {

	/**
	 * 附近店铺
	 *//*
	@Override
	public ResultMsg<?> getShop(String local,Double lon,Double lat,Integer type,String name,Long lastCreateTime,int pageSize,String enc) {
		//验证参数
		if(null == lon || null == lat || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lon){
				error.put("lon", "经度值不能为空");
			}
			if(null == lat){
				error.put("lat", "纬度值不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(lon+""+lat).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			ESVo vo = ES.getNearbyShop(IndexConstant.SHOP_SEARCH_INDEX, IndexConstant.SHOP_SEARCH_INDEX_TYPE, lat, lon,name,lastCreateTime,pageSize);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}
	  */
	/**
	 * 附近商品和服务（在线寄快递、交物业费）
	 */
	@Override
	public ResultMsg<?> getProduct(String local, String domain, String lonstr,
			String latstr,Integer type, String name,Long shopId,Long lastCreateTime,Integer pageSize, String enc) {
		//验证参数
		if(null == lonstr || null == latstr || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lonstr || null == latstr){
				error.put("lon", "定位失败");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(lonstr+""+latstr).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Double lon = Double.valueOf(lonstr);
		Double lat = Double.valueOf(latstr);

		try {
			ESVo vo = null;
			//搜索商品
			if(type == FieldConstant.SEARCH_TYPE_COMMOD){
				vo = ES.getNearbyProduct(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE, lat, lon, name, lastCreateTime, pageSize);
			}
			//搜索服务
			else if(type == FieldConstant.SEARCH_TYPE_SERVICE){
				vo = ES.getNearbyService(IndexConstant.SERVICE_SEARCH_INDEX, IndexConstant.SERVICE_SEARCH_INDEX_TYPE, lat, lon, name, lastCreateTime, pageSize);
			}
			//搜索店铺
			else if(type == FieldConstant.SEARCH_TYPE_SHOP){
				vo = ES.getNearbyShop(IndexConstant.SHOP_SEARCH_INDEX, IndexConstant.SHOP_SEARCH_INDEX_TYPE, lat, lon,name,lastCreateTime,pageSize);
			}
			//搜索小区
			/*else if(type == FieldConstant.SEARCH_TYPE_RESIDENTIAL){
				vo = ES.getNearbyResidential(IndexConstant.RESIDENTIAL_SEARCH_INDEX, IndexConstant.RESIDENTIAL_SEARCH_INDEX_TYPE, lat, lon,name,shopId,lastCreateTime,pageSize);
			}*/
			//首页搜索
			else{
				vo = ES.getNearbyIndex(IndexConstant.HOME_SEARCH_INDEX, IndexConstant.HOME_SEARCH_INDEX_TYPE, lat, lon,name,shopId,lastCreateTime,pageSize);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",vo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

	/**
	 * 附近小区
	 */
	@Override
	public ResultMsg<?> getResidential(String local, String lonstr,String latstr,Long lastCreateTime,String wd, Integer pageSize,String enc) {
		//验证参数
		if(null == lonstr || null == latstr || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lonstr || null == latstr){
				error.put("lon", "定位失败");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(lonstr+""+latstr).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Double lon = Double.valueOf(lonstr);
		Double lat = Double.valueOf(latstr);
		try {
			ESVo vo = ES.getNearbyResidential(IndexConstant.RESIDENTIAL_SEARCH_INDEX, IndexConstant.RESIDENTIAL_SEARCH_INDEX_TYPE, lat, lon,wd,lastCreateTime,pageSize);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",vo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

	/**
	 * 附近的人
	 */
	@Override
	public ResultMsg<?> getMember(String local, String location,String lastId, String wd,String enc) {
		return null;
	}

	/**
	 * 周边城市
	 */
	@Override
	public ResultMsg<?> getCity(String local, String lonstr,String latstr,String enc) {
		//验证参数
		if(null == lonstr || null == latstr || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lonstr || null == latstr){
				error.put("lon", "定位失败");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(lonstr+""+latstr).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Double lon = Double.valueOf(lonstr);
		Double lat = Double.valueOf(latstr);
		try {
			ESVo vo = ES.getNearbyCity(IndexConstant.CITY_SEARCH_INDEX, IndexConstant.CITY_SEARCH_INDEX_TYPE, lat, lon);
			JSONObject r = JSONObject.parseObject(JSON.toJSONString(vo));
			r.put("hot", new ArrayList<Object>());
			r.put("list", vo.getData());
			r.remove("data");
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",r);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

}
