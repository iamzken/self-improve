package com.d3sq.shopping.mvc.action.mobile;

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
import com.d3sq.shopping.service.IKindService;

/**
 * 分类Controller
 *
 */
@Controller
@RequestMapping("/mobile/kind")
@Domain(value="mall",desc="分类查询")
public class KindAction extends BaseAction{
	@Autowired
	private IKindService kindService;
	
	/**
	 * 获取商品分类信息
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getForProduct.json")
	public ModelAndView getForProduct(@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = kindService.getForProduct(local,domain,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 获取服务分类信息
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getForService.json")
	public ModelAndView getForService(@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = kindService.getForService(local,domain,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 获取店铺分类信息
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getForShop.json")
	public ModelAndView getKindForShop(@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = kindService.getForShop(local,domain,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	/**
	 * 根据父类id获取子分类
	 * @param parentId
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getByParentId.json")
	public ModelAndView getByParentId(@RequestParam(value="parentId",defaultValue="") Long parentId,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = kindService.getByParentId(local,domain,parentId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
}
