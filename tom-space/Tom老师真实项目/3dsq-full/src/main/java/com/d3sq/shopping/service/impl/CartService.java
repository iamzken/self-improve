package com.d3sq.shopping.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.model.entity.MallCart;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.Shop;
import com.d3sq.model.helper.ProductItem;
import com.d3sq.model.helper.ShopItem;
import com.d3sq.shopping.dao.CartDao;
import com.d3sq.shopping.dao.ProductDao;
import com.d3sq.shopping.dao.ShopDao;
import com.d3sq.shopping.service.ICartService;

@Service("cartService")
public class CartService implements ICartService {
	@Autowired private CartDao cartDao;
	@Autowired private ProductDao productDao;
	@Autowired private ShopDao shopDao;



	@Override
	public ResultMsg<?> getList(String local, Long userId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("userId", "未登录");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			MallCart cart = cartDao.selectByMemberId(userId);
			if(null == cart){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功");
			}
			List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);
			List<Long> productIds = new ArrayList<Long>();
			Map<Long,ShopItem> si = new HashMap<Long,ShopItem>();
			Map<Long,ProductItem> pi = new HashMap<Long,ProductItem>();
			for (ShopItem c : arr) {
				si.put(c.getShopId(), c);
				for (ProductItem p : c.getProducts()) {
					pi.put(p.getProductId(), p);
					productIds.add(p.getProductId());
				}
			}
			List<Product> products = productDao.selectByIds(productIds);
			for(Long shopId:si.keySet()){
				List<ProductItem> productItems = new ArrayList<ProductItem>();
				for(Product p:products){
					if(!p.getShopId().equals(shopId)){
						continue;
					}
					pi.get(p.getId()).setPrice(p.getPrice());
					BigDecimal price = new BigDecimal(p.getPrice());
					BigDecimal buyCount = new BigDecimal(pi.get(p.getId()).getBuyCount());
					pi.get(p.getId()).setTotalAmount(price.multiply(buyCount).floatValue());
					productItems.add(pi.get(p.getId()));
				}
				si.get(shopId).setProducts(productItems);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "获取成功",si.values());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}


	@Override
	public ResultMsg<?> addCart(String local,  Long productId,Integer addCount,Float addPrice,Long userId, String enc) {
		//验证参数
		if(null == userId || null == productId || null == addCount || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("userId", "未登录");
			}
			if(null == productId){
				error.put("id", "商品不能为空");
			}
			if(null == addCount || addCount.intValue() < 1){
				error.put("buyCount", "购买数量不能没有");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			Product product = productDao.getById(productId);
			if(null == product){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "该商品不存在或已下架");
			}
			Shop shop = shopDao.selectById(product.getShopId());

			MallCart cart = cartDao.selectByMemberId(userId);

			List<ShopItem> arr = new ArrayList<ShopItem>();
			if(cart != null){
				arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);
			}
			Map<Long,ProductItem> pi = new TreeMap<Long,ProductItem>();
			Map<Long,ShopItem> si = new TreeMap<Long,ShopItem>();
			for (ShopItem c : arr) {
				si.put(c.getShopId(), c);
				for (ProductItem p : c.getProducts()) {
					pi.put(p.getProductId(), p);
				}
			}

			if(!si.containsKey(product.getShopId())){
				ShopItem shopItem = new ShopItem();
				shopItem.setShopId(product.getShopId());
				shopItem.setShopName(shop.getName());
				si.put(product.getShopId(), shopItem);
			}

			if(!pi.containsKey(product.getId())){
				ProductItem p = new ProductItem();
				p.setProductId(productId);
				p.setProductName(product.getName());
				p.setCoverImg(product.getCoverImg());
				p.setBuyCount(addCount);
				p.setAddPrice(addPrice);
				//p.setPrice(product.getPrice());
				p.setCheck(SystemConstant.ENABLE);
				p.setCreateTime(System.currentTimeMillis());
				si.get(product.getShopId()).getProducts().add(p);
			}else{
				//如果当前商品购买数量为0时,则删除该商品
				if(pi.get(product.getId()).getBuyCount() + addCount == 0){
					si.get(product.getShopId()).getProducts().remove(pi.get(product.getId()));
					//如果当前店铺中商品为空,则删除该店铺
					if(null == si.get(product.getShopId()).getProducts() || si.get(product.getShopId()).getProducts().size() < 1){
						si.remove(product.getShopId());
					}
				}else{
					pi.get(product.getId()).setBuyCount(pi.get(product.getId()).getBuyCount() + addCount);
					pi.get(product.getId()).setAddPrice(addPrice);
				}
			}

			boolean result = false;
			if(null != cart){
				cart.setShops(JSONObject.toJSONString(si.values()));
				result = cartDao.update(cart) > 0;
			}else{
				cart = new MallCart();
				cart.setMemberId(userId);
				cart.setShops(JSONObject.toJSONString(si.values()));
				result = cartDao.insertAndReturnId(cart) > 0;
			}
			if(result){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "操作成功",si.values());
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}

	}


	@Override
	public ResultMsg<?> modifyCheck(String local, Long id, Integer type,
			Integer check, Long userId, String enc) {
		//验证参数
		if(null == type || null == userId  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("userId", "未登录");
			}
			if(null == type){
				error.put("type", "参数类型不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		if((1 == type.intValue() || 2 == type.intValue()) && null == id){
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",new JSONObject().put("id", "参数id不能为空"));
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			MallCart cart = cartDao.selectByMemberId(userId);
			List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);

			Map<Long,ShopItem> si = new TreeMap<Long,ShopItem>();
			for (ShopItem c : arr) {
				si.put(c.getShopId(), c);
			}

			//传的是商品id
			if(1 == type.intValue()){
				Product product = productDao.getById(id);
				for(int i=0;i<si.get(product.getShopId()).getProducts().size();i++){
					if(!id.equals(si.get(product.getShopId()).getProducts().get(i).getProductId())){
						continue;
					}
					si.get(product.getShopId()).getProducts().get(i).setCheck(check);
				}
			}

			//传的是店铺id
			if(2 == type.intValue()){
				for(int i=0;i<si.get(id).getProducts().size();i++){
					si.get(id).getProducts().get(i).setCheck(check);
				}
			}

			//全选
			if(3 == type.intValue()){
				for(Long shopId : si.keySet()){
					for(int j=0;j<si.get(shopId).getProducts().size();j++){
						si.get(shopId).getProducts().get(j).setCheck(check);
					}
				}
			}

			cart.setShops(JSONObject.toJSONString(si.values()));
			boolean result = cartDao.update(cart) > 0;
			if(result){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "操作成功",cart.getShops());
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}


	@Override
	public ResultMsg<?> mergeCart(String local, Long userId, String shops,
			String enc) {
		//验证参数
		if(null == shops || null == userId  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("userId", "未登录");
			}
			if(null == shops){
				error.put("type", "购物车信息不能空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		
		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			MallCart cart = cartDao.selectByMemberId(userId);
			boolean result = false;
			if(null == cart){
				cart = new MallCart();
				cart.setMemberId(userId);
				cart.setShops(shops);
				result = cartDao.insertAndReturnId(cart) > 0;
				if(result){
					return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "操作成功",cart.getShops());
				}else{
					return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "操作失败");
				}
			}
			//同步过来购物车json信息
			List<ShopItem> baseArr = JSONArray.parseArray(shops,ShopItem.class);
			Map<Long,ProductItem> basePi = new TreeMap<Long,ProductItem>();
			Map<Long,ShopItem> baseSi = new TreeMap<Long,ShopItem>();
			for (ShopItem c : baseArr) {
				baseSi.put(c.getShopId(), c);
				for (ProductItem p : c.getProducts()) {
					basePi.put(p.getProductId(), p);
				}
			}

			List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);

			Map<Long,ProductItem> pi = new TreeMap<Long,ProductItem>();
			Map<Long,ShopItem> si = new TreeMap<Long,ShopItem>();
			for (ShopItem c : arr) {
				si.put(c.getShopId(), c);
				for (ProductItem p : c.getProducts()) {
					pi.put(p.getProductId(), p);
				}
			}


			for (ShopItem s:baseArr) {
				//判断店铺是否存在,不存在则新增
				if(!si.containsKey(s.getShopId())){
					ShopItem shopItem = new ShopItem();
					shopItem.setShopId(s.getShopId());
					shopItem.setShopName(s.getShopName());
					si.put(s.getShopId(), shopItem);
				}
				for(ProductItem p:s.getProducts()){
					if(!pi.containsKey(p.getProductId())){
						ProductItem productItem = new ProductItem();
						DataUtils.copySimpleObject(p, productItem);
						si.get(s.getShopId()).getProducts().add(productItem);
					}else{
						si.get(s.getShopId()).getProducts().remove(p.getProductId());
						si.get(s.getShopId()).getProducts().add(p);
					}
				}
			}

			cart.setShops(JSONObject.toJSONString(si.values()));
			result = cartDao.update(cart) > 0;
			if(result){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "操作成功",cart.getShops());
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}




	/*	@Override
	public ResultMsg<?> removeCart(String local,Long userId, Long prodcutId, String enc) {
		//验证参数
		if(null == prodcutId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == prodcutId){
				error.put("id", "删除商品不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(prodcutId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			boolean result = true;// cartDao.deleteByMemberIdAndProductId(userId,prodcutId);
			if(result){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "删除成功");
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "删除失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}*/
}
