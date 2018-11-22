package com.d3sq.search.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Domain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.search.service.INearbyService;

/**
 * 附近搜索
 *
 */
@Controller
@RequestMapping("/mobile/nearby")
@Domain(value="s",desc="附近搜索")
public class NearbyAction extends BaseAction{
	
	@Autowired INearbyService nearbyService;
	
	/**
	 * 获取附近的商家
	 * @param request
	 * @param response
	 * @param location
	 * @param enc
	 * @return
	 */
	@RequestMapping(value="/getShop.json")
	public ModelAndView getShop(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(value="lon",defaultValue="") Double lon
			,@RequestParam(value="lat",defaultValue="") Double lat
			,@RequestParam(value="name",defaultValue="") String name
			,@RequestParam(value="lastCreateTime",defaultValue="") Long lastCreateTime
			,@RequestParam(value="pageSize",defaultValue="10") Integer pageSize
			,@RequestParam(value="enc",defaultValue="") String enc){
		String local = super.getLocal(request);
		ResultMsg<?> result = null;//nearbyService.getShop(local, lon,lat,name,lastCreateTime,pageSize,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 搜索
	 * @param lon
	 * @param lat
	 * @param name
	 * @param shopId
	 * @param lastCreateTime
	 * @param pageSize
	 * @param type
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/search.json")
	public ModelAndView getProduct(
			@RequestParam(value="lon") String lon
			,@RequestParam(value="lat") String lat
			,@RequestParam(value="wd",required=false) String name
			,@RequestParam(value="shopId",required=false) Long shopId
			,@RequestParam(value="lastId",required=false) Long lastCreateTime
			,@RequestParam(value="pageSize",defaultValue="10",required=false) Integer pageSize
			,@RequestParam(value="type",required=false) Integer type
			,@RequestParam(value="enc") String enc,
			HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = nearbyService.getProduct(local, domain, lon, lat,type, name, shopId, lastCreateTime, pageSize,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 获取附近的小区
	 * @param request
	 * @param response
	 * @param location
	 * @param enc
	 * @return
	 */
	@RequestMapping(value="/getResidential.json")
	public ModelAndView getResidential(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="lon") String lon,
			@RequestParam(value="lat") String lat,
			@RequestParam(value="wd",required=false) String wd,
			@RequestParam(value="lastId",required=false) Long lastCreateTime,
			@RequestParam(value="pageSize",defaultValue="10",required=false) Integer pageSize,
			@RequestParam(value="enc",defaultValue="") String enc){
		String local = super.getLocal(request);
		ResultMsg<?> result = nearbyService.getResidential(local, lon,lat,lastCreateTime, wd,pageSize, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 获取附近的人
	 * @param request
	 * @param response
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	@RequestMapping(value="/getMember.json")
	public ModelAndView getMember(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="location") String location,
			@RequestParam(value="wd",required=false) String wd,
			@RequestParam(value="lastId",required=false) String lastId,
			@RequestParam(value="pageSize",defaultValue="10",required=false) Integer pageSize,
			@RequestParam(value="enc",defaultValue="") String enc){
		String local = super.getLocal(request);
		ResultMsg<?> result = nearbyService.getMember(local, location,lastId,wd, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 获取周边城市
	 * @param request
	 * @param response
	 * @param location
	 * @param wd
	 * @param enc
	 * @return
	 */
	@RequestMapping(value="/getCity.json")
	public ModelAndView getCity(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="lon") String lon,
			@RequestParam(value="lat") String lat,
			@RequestParam(value="enc",defaultValue="") String enc){
		String local = super.getLocal(request);
		ResultMsg<?> result = nearbyService.getCity(local, lon, lat, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
}
