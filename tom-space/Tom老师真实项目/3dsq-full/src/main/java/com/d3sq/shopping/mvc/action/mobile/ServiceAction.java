package com.d3sq.shopping.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Domain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.shopping.service.IServiceService;
import com.d3sq.shopping.vo.AddServiceVo;

/**
 * 服务信息Controller
 *
 */
@Controller
@RequestMapping("/mobile/service")
@Domain(value="mall",desc="服务相关")
public class ServiceAction extends BaseAction{
	@Autowired
	private IServiceService serviceService;
	
	/**
	 * 创建服务
	 * @param serviceVo
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addService.json")
	public ModelAndView addService(@ModelAttribute AddServiceVo serviceVo,@RequestParam(value="enc") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		Long shopId = obj.getJSONObject("shop").getLong("id");
		serviceVo.setShopId(shopId);
		ResultMsg<?> result = serviceService.addService(local,userId,serviceVo,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	/**
	 * 获取服务详情
	 * @param id
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getServiceDetail.json")
	public ModelAndView getServiceDetail(@RequestParam(value="serviceId") Long serviceId,@RequestParam(value="enc") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		ResultMsg<?> result = serviceService.getService(local,serviceId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
}
