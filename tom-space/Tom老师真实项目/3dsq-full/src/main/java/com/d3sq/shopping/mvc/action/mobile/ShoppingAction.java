package com.d3sq.shopping.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
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
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.shopping.service.IShoppingService;
import com.d3sq.shopping.vo.MallOrderVo;


/**
 * 内容数据接口Controller
 *
 */
@Controller
@RequestMapping("/mobile/shopping")
@Domain(value="mall",desc="购物相关")
public class ShoppingAction extends BaseAction{
	@Autowired IShoppingService shoppingService;
	
	
	/**
	 * 去下单
	 * @param productId
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toAddOrder.json")
	public ModelAndView toAddOrder(@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = shoppingService.toAddOrder(local,userId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 确认下单(商品)
	 * @param mallOrderVo
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addOrderForProduct.json")
	public ModelAndView addOrderForProduct(@ModelAttribute MallOrderVo mallOrderVo,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		mallOrderVo.setOtype(FieldConstant.PAY_OTYPE_SHOPPING);
		ResultMsg<?> result = shoppingService.addOrder(local,domain,userId,mallOrderVo,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 确认下单(服务)
	 * @param mallOrderVo
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addOrderForService.json")
	public ModelAndView addOrderForService(@ModelAttribute MallOrderVo mallOrderVo,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		mallOrderVo.setOtype(FieldConstant.PAY_OTYPE_SERVICE);
		ResultMsg<?> result = shoppingService.addOrder(local,domain,userId,mallOrderVo,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	@Api(desc="取消订单",
			auth={ @Auth(checkEnc=true) },
			params={
					@Rule(name="orderId",desc="订单号"),
					@Rule(name="enc",desc="授权码")
	})
	@RequestMapping(value="/cancelOrder.json")
	public ModelAndView cancelOrder(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="orderId") String orderId,
			@RequestParam(value="enc") String enc) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = shoppingService.cancelOrder(local,userId,orderId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
}
