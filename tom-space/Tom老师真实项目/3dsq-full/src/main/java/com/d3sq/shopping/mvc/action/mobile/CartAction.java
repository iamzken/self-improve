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
import com.alibaba.fastjson.JSONObject;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.shopping.service.ICartService;

/**
 * 购物车(用户版)Controller
 *
 */
@Controller
@RequestMapping("/mobile/cart")
@Domain(value="mall",desc="购物车")
public class CartAction extends BaseAction{
	@Autowired
	private ICartService cartService;

	/**
	 * 获取购物车中的商品列表
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getList.json")
	public ModelAndView getList(@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = cartService.getList(local,userId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}


	/**
	 * 同步客户端的购物车
	 * @param shops
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/mergeCart.json")
	public ModelAndView mergeCart(
			@RequestParam(value="shops") String shops,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = cartService.mergeCart(local,userId,shops,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}


	/**
	 * 添加购物车
	 * @param cart
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addToCart.json")
	public ModelAndView addToCart(@RequestParam(value="productId") Long productId
			,@RequestParam(value="addCount") Integer addCount
			,@RequestParam(value="addPrice") Float addPrice
			,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = cartService.addCart(local,productId,addCount,addPrice,userId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 编辑商品选中状态
	 * @param type
	 * @param id
	 * @param check
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/modifyCheck.json")
	public ModelAndView modifyCheck(@RequestParam(value="type") Integer type
			,@RequestParam(value="id",required=false) Long id
			,@RequestParam(value="check") Integer check
			,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = cartService.modifyCheck(local,id,type,check,userId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}

	/**
	 * 删除购物车
	 * @param id
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping(value="/removeCart.json")
	public ModelAndView removeCart(@RequestParam(value="id",defaultValue="") Long prodcutId,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = cartService.removeCart(local,userId,prodcutId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}*/
}
