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
import com.d3sq.shopping.service.IShopService;
import com.d3sq.shopping.vo.AddShopVo;

/**
 * 店铺信息Controller
 *
 */
@Controller
@RequestMapping("/mobile/shop")
@Domain(value="mall",desc="店铺相关")
public class ShopAction extends BaseAction{
	@Autowired
	private IShopService shopService;
	
	/**
	 * 添加店铺
	 * @param shopVo
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addShop.json")
	public ModelAndView addShop(@ModelAttribute AddShopVo shopVo,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		shopVo.setCreatorId(userId);
		ResultMsg<?> result = shopService.addShop(local,domain,shopVo,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
}
