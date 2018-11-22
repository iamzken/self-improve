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
import com.d3sq.shopping.service.IProductService;
import com.d3sq.shopping.vo.AddProductVo;

/**
 * 商品信息Controller
 *
 */
@Controller
@RequestMapping("/mobile/product")
@Domain(value="mall",desc="商品相关")
public class ProductAction extends BaseAction{
	@Autowired
	private IProductService productService;
	
	/**
	 * 添加商品
	 * @param name
	 * @param shopId
	 * @param intro
	 * @param coverImg
	 * @param price
	 * @param sale
	 * @param stock
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addProduct.json")
	public ModelAndView addProduct(@ModelAttribute AddProductVo productVo,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		Long shopId = obj.getJSONObject("shop").getLong("id");
		productVo.setShopId(shopId);
		ResultMsg<?> result = productService.addProduct(local,userId,productVo,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	/**
	 * 分页查询商品信息根据分类
	 * @param catalogueId
	 * @param lastProductId
	 * @param pageSize
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getProductByCatalogueId.json")
	public ModelAndView getProductByCatalogueId(
			@RequestParam(value="lon",defaultValue="") Double lon,
			@RequestParam(value="lat",defaultValue="") Double lat,
			@RequestParam(value="catalogueId",defaultValue="") String catalogueId,
			@RequestParam(value="lastProductId",defaultValue="") Long lastProductId,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = productService.getProductByCatalogueId(local,domain,lon,lat,catalogueId,lastProductId,pageSize,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	

	/**
	 * 获取商品详情
	 * @param productId
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getProductDetail.json")
	public ModelAndView getProduct(@RequestParam(value="productId",defaultValue="") Long productId,@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		String domain = getDomain(request);
		ResultMsg<?> result = productService.getProduct(local,domain,productId,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	
	/**
	 * 修改商品
	 * @param name
	 * @param shopId
	 * @param intro
	 * @param coverImg
	 * @param price
	 * @param sale
	 * @param stock
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateProduct.json")
	public ModelAndView updateProduct(@RequestParam(value="id",defaultValue="") Long id,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="shopId",defaultValue="") Long shopId,
			@RequestParam(value="intro",defaultValue="") String intro,
			@RequestParam(value="coverImg",defaultValue="") String coverImg,
			@RequestParam(value="price",defaultValue="") Float price,
			@RequestParam(value="sale",defaultValue="") Float sale,
			@RequestParam(value="stock",defaultValue="") int stock,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		ResultMsg<?> result = productService.updateProduct(local,id,name,shopId,intro,coverImg,price,sale,stock,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
	
	
	/**
	 * 删除商品
	 * @param id
	 * @param enc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteProduct.json")
	public ModelAndView deleteProduct(@RequestParam(value="id",defaultValue="") Long id,
			@RequestParam(value="enc",defaultValue="") String enc,
			HttpServletRequest request,HttpServletResponse response) {
		String local = super.getLocal(request);
		ResultMsg<?> result = productService.deleteProduct(local,id,enc);
		return super.callBackForJsonp(request, response,
				JSON.toJSONString(result));
	}
}
