package com.d3sq.shopping.service.impl;

import java.util.List;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.common.utils.SystemUtil;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Service;
import com.d3sq.model.entity.Shop;
import com.d3sq.model.helper.RuleItem;
import com.d3sq.search.plugin.ServiceIndexPlugin;
import com.d3sq.shopping.dao.KindServiceDao;
import com.d3sq.shopping.dao.ServiceDao;
import com.d3sq.shopping.dao.ShopDao;
import com.d3sq.shopping.service.IServiceService;
import com.d3sq.shopping.vo.AddServiceVo;
import com.d3sq.shopping.vo.ServiceVo;

@org.springframework.stereotype.Service("serviceService")
public class ServiceService implements IServiceService {
	@Autowired ServiceDao serviceDao;
	@Autowired ShopDao shopDao;
	@Autowired KindServiceDao kindServiceDao;
	@Autowired ServiceIndexPlugin serviceIndexPlugin;


	@Override
	public ResultMsg<?> addService(String local, Long userId, AddServiceVo serviceVo, String enc) {
		String serviceName = serviceVo.getServiceName();
		Long shopId = serviceVo.getShopId();
		Long kindId = serviceVo.getKindId();
		//验证参数
		if(StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(serviceVo.getCoverImg()) || null == kindId || null == shopId    || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(serviceName)){
				error.put("productName", "服务名称未填写");
			}
			if(null == kindId){
				error.put("kindId", "未选择分类");
			}
			if(null == shopId){
				error.put("shopId", "未选择店铺");
			}
			if(StringUtils.isEmpty(serviceVo.getCoverImg())){
				error.put("coverImg", "封面图片必传");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		if(!StringUtils.isEmpty(serviceVo.getPhotos())){
			boolean [] valids = SystemUtil.validImgArrByJson(serviceVo.getPhotos());
			JSONObject error = new JSONObject();
			if(null == valids){
				return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "图片数据格式有误");
			}
			int i = 1;
			for(boolean valid : valids){
				i++;
				if(valid){
					continue;
				}

				error.put("photos", "第"+i+"张图文介绍图片格式不正确");
				return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
			}
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+""+shopId+kindId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			//转义图片url斜杠
			String photos =  serviceVo.getPhotos().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
			Service service = new Service();
			DataUtils.copySimpleObject(serviceVo, service);
			if(!StringUtils.isEmpty(serviceVo.getFeeStand())){
				String feeStand = serviceVo.getFeeStand().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
				service.setFeeStand(feeStand);
			}
			if(!StringUtils.isEmpty(serviceVo.getTimesDual())){
				String timeDual = serviceVo.getTimesDual().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
				service.setTimesDual(timeDual);
			}
			if(!StringUtils.isEmpty(serviceVo.getIntro())){
				String intro = serviceVo.getIntro().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
				service.setIntro(intro);
			}
			service.setPhotos(photos);
			service.setName(serviceName);
			service.setCreateTime(System.currentTimeMillis());
			service.setCreatorId(userId);
			service.setState(SystemConstant.ENABLE);
			service.setPinyin(PinyinUtil.converterToSpell(serviceName).split(",")[0]);
			Long id = serviceDao.insertAndReturnId(service);
			if(id > 0){
				//入库服务分类
				com.d3sq.model.entity.KindService kindService = new com.d3sq.model.entity.KindService();
				kindService.setKindId(serviceVo.getKindId());
				kindService.setKindPath(serviceVo.getKindPath());
				kindService.setServiceId(id);
				kindServiceDao.insertAndReturnId(kindService);

				service.setId(id);
				//把服务添加到索引
				ServiceVo vo = new ServiceVo();
				DataUtils.copySimpleObject(service, vo);
				//查找对应店铺信息
				Shop shop = shopDao.selectById(vo.getShopId());
				vo.setLat(shop.getLat());
				vo.setLon(shop.getLon());
				vo.setShopName(shop.getName());
				QueueItem item = new QueueItem(serviceIndexPlugin,IndexConstant.SERVICE_SEARCH_INDEX, QueueTarget.OPT_ADD, id, JSONObject.toJSON(vo));
				serviceIndexPlugin.push(item);

				JSONObject r = JSON.parseObject(JSON.toJSONString(service));
				r.put("photos", JSON.parseArray(service.getPhotos()));
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加成功",r);
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加出现异常");
		}
	}


	@Override
	public ResultMsg<?> getService(String local, Long serviceId, String enc) {
		//验证参数
		if(null == serviceId  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == serviceId){
				error.put("serviceId", "服务id必传");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		//检查授权码是否正确
		if(!MobileConstant.genEnc(serviceId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			Service service = serviceDao.selectById(serviceId);
			if(null == service){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "服务不存在或已下架");
			}
			
			ServiceVo vo = new ServiceVo();
			DataUtils.copySimpleObject(service, vo);
			//查找对应店铺信息
			Shop shop = shopDao.selectById(vo.getShopId());
			vo.setLat(shop.getLat());
			vo.setLon(shop.getLon());
			vo.setShopName(shop.getName());
			//计算价格区间
			String price = this.getPrice(service);
			vo.setPrice(price);

			JSONObject r = JSON.parseObject(JSON.toJSONString(vo));
			r.put("photos", JSON.parseArray(vo.getPhotos()));
			r.put("intro", JSON.parseArray(vo.getIntro()));
			r.put("feeStand", JSON.parseArray(vo.getFeeStand()));
			r.put("timesDual", JSON.parseArray(vo.getTimesDual()));
			r.put("promise", JSON.parseArray(vo.getPromise()));
			r.put("notice", JSON.parseArray(vo.getNotice()));
			r.put("program", JSON.parseArray(vo.getProgram()));


			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功", r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

	/**
	 * 获取收费区间
	 * @param feeStand
	 * @return
	 */
	private String getPrice(Service service) {
		List<RuleItem> arr = JSONArray.parseArray(service.getFeeStand(),RuleItem.class);
		if(arr.isEmpty()){
			return null;
		}
		//查询最大值
		float max = 0;
		//最小值
		float min = 0;

		if(1 == arr.size()){
			min = arr.get(0).getPrice().floatValue();
		}else{
			int i = 0;
			for(RuleItem ruleItem:arr){
				if(i == 0){
					max = ruleItem.getPrice().floatValue();
					min = ruleItem.getPrice().floatValue();
				}
				if(ruleItem.getPrice().floatValue() > max){
					max = ruleItem.getPrice().floatValue();
				}

				if(ruleItem.getPrice().floatValue() < max){
					max = ruleItem.getPrice().floatValue();
				}
				i++;
			}
		}

		if(min > 0 && max > 0){
			return min+"~"+max;
		}else if(min > 0){
			return min+"";
		}else if(max > 0){
			return max+"";
		}
		return null;
	}
}
