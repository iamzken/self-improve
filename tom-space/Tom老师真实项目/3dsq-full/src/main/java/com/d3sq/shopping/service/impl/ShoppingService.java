package com.d3sq.shopping.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.service.ILangService;
import com.d3sq.model.entity.MallCart;
import com.d3sq.model.entity.Order;
import com.d3sq.model.entity.ProductOrderDetail;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.ServiceOrderDetail;
import com.d3sq.model.entity.Shop;
import com.d3sq.model.helper.ProductItem;
import com.d3sq.model.helper.RuleItem;
import com.d3sq.model.helper.ShopItem;
import com.d3sq.model.helper.TimeDualItem;
import com.d3sq.search.dao.ServiceDao;
import com.d3sq.shopping.dao.CartDao;
import com.d3sq.shopping.dao.OrderDao;
import com.d3sq.shopping.dao.ProductOrderDetailDao;
import com.d3sq.shopping.dao.ProductDao;
import com.d3sq.shopping.dao.ServiceOrderDetailDao;
import com.d3sq.shopping.dao.ShopDao;
import com.d3sq.shopping.service.IShoppingService;
import com.d3sq.shopping.vo.MallOrderVo;

@Service("shoppingService")
public class ShoppingService implements IShoppingService {
	@Autowired ProductDao productDao;
	@Autowired ServiceDao serviceDao;
	@Autowired OrderDao mallOrderDao;
	@Autowired ProductOrderDetailDao productOrderDetailDao;
	@Autowired ServiceOrderDetailDao serviceOrderDetailDao;
	@Autowired ShopDao shopDao;
	@Autowired CartDao cartDao;
	@Autowired ILangService langService;

	@Override
	public ResultMsg<?> addOrder(String local, String domain,
			Long userId,MallOrderVo mallOrderVo, String enc) {
		//验证参数
		if(null == userId 
				|| StringUtils.isEmpty(mallOrderVo.getCustomName()) 
				|| StringUtils.isEmpty(mallOrderVo.getCustomTel()) 
				|| StringUtils.isEmpty(mallOrderVo.getAddrDetail())
				|| StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId ){
				error.put("userId", "登录已失效");
			}
			if(StringUtils.isEmpty(mallOrderVo.getCustomName())){
				error.put("customName", "联系人必填");
			}
			if(StringUtils.isEmpty(mallOrderVo.getCustomTel())){
				error.put("customName", "联系方式必填");
			}
			if(StringUtils.isEmpty(mallOrderVo.getAddrDetail())){
				error.put("customName", "收货地址必填");
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

			/**
			 * 购买商品订单
			 */
			if(FieldConstant.PAY_OTYPE_SHOPPING.equals(mallOrderVo.getOtype())){
				return this.addOrderForProduct(userId,mallOrderVo);
			}

			/**
			 * 预约服务订单
			 */
			if(FieldConstant.PAY_OTYPE_SERVICE.equals(mallOrderVo.getOtype())){
				return this.addOrderForService(userId,mallOrderVo);
			}

			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "暂时不支持的订单");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

	/**
	 * 预约服务订单
	 * @param userId
	 * @param mallOrderVo
	 * @return
	 */
	private ResultMsg<?> addOrderForService(Long userId, MallOrderVo mallOrderVo) {
		//查找服务信息
		com.d3sq.model.entity.Service service = serviceDao.selectById(mallOrderVo.getServiceId());
		if(null == service){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "服务不存在或已下架");
		}
		//查找服务所属店铺信息
		Shop shop = shopDao.selectById(service.getShopId());
		//获取所选服务价格规格
		List<RuleItem> ruleItemArr = JSONArray.parseArray(service.getFeeStand(),RuleItem.class);
		RuleItem ruleItem = ruleItemArr.get(mallOrderVo.getFeeStandIndex());
		ruleItemArr.clear();
		ruleItemArr.add(ruleItem);
		//获取所选服务服务时段
		List<TimeDualItem> timeDualItemArr = JSONArray.parseArray(service.getTimesDual(),TimeDualItem.class);
		TimeDualItem timeDualItem = timeDualItemArr.get(mallOrderVo.getTimesDualIndex());
		timeDualItemArr.clear();
		timeDualItemArr.add(timeDualItem);


		//订单号
		String parentNum = langService.getOrderNum(true, mallOrderVo.getOtype());
		Order order = new Order();
		DataUtils.copySimpleObject(mallOrderVo, order);
		order.setShopId(shop.getId());
		order.setSellerName(shop.getConcacts());
		order.setSellerTel(shop.getTel());
		order.setOrderAmount(ruleItem.getPrice());
		//父订单号默认-1
		order.setParentNum(parentNum);
		//订单号
		order.setNum(langService.getOrderNum(false, mallOrderVo.getOtype()));
		//付款人
		order.setFromMemberId(userId);
		//下单时间
		order.setCreateTime(System.currentTimeMillis());
		//订单进度
		order.setProcess(FieldConstant.PAY_PROCESS_UNPAY);
		//订单状态
		order.setState(SystemConstant.ENABLE);
		Long id = mallOrderDao.insertAndReturnId(order);
		if(id <= 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "预约失败");
		}

		//订单详情入库
		ServiceOrderDetail orderDetail = new ServiceOrderDetail();
		orderDetail.setOrderId(id);
		orderDetail.setServiceId(mallOrderVo.getServiceId());
		orderDetail.setPrice(ruleItem.getPrice());
		orderDetail.setServiceName(service.getName());
		orderDetail.setContent(service.getContent());
		orderDetail.setFeeStand(JSONObject.toJSONString(ruleItemArr));
		orderDetail.setTimesDual(JSONObject.toJSONString(timeDualItemArr));
		Long detailId = serviceOrderDetailDao.insertAndReturnId(orderDetail);
		if(detailId > 0){
			JSONObject obj = new JSONObject();
			obj.put("orderId", parentNum);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "预约成功",obj);
		}
		//删除订单
		mallOrderDao.delete(id);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "预约失败");
	}


	/**
	 * 购买商品订单
	 * @param userId
	 * @param mallOrderVo
	 * @return
	 */
	private ResultMsg<?> addOrderForProduct(Long userId,MallOrderVo mallOrderVo) {
		//1.先获取购物车
		MallCart cart = cartDao.selectByMemberId(userId);
		if(null == cart){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "没查询到购物车信息");
		}

		//2.获取购物车选中的商品信息
		Map<Long, List<ProductItem>> checkProductInfo = this.getCheckProductInfo(cart);
		if(null == checkProductInfo || checkProductInfo.values().size() < 1){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "购物车没有选中购买的商品");
		}

		//3.根据店铺计算购买商品的总价
		Map<Long,Float> priceMap = this.calcCheckProductInfo(checkProductInfo);

		//4.获取店铺信息
		Map<Long,Shop> shopMap = this.getShopInfo(checkProductInfo);

		Long currTime =  System.currentTimeMillis();
		//父订单号
		String parentNum = langService.getOrderNum(true, mallOrderVo.getOtype());
		//5.订单入库
		for(Long shopId:checkProductInfo.keySet()){
			Order order = new Order();
			DataUtils.copySimpleObject(mallOrderVo, order);
			order.setShopId(shopId);
			order.setSellerName(shopMap.get(shopId).getConcacts());
			order.setSellerTel(shopMap.get(shopId).getTel());
			order.setProductCount(checkProductInfo.get(shopId).size());
			order.setOrderAmount(priceMap.get(shopId));
			//订单号
			order.setNum(langService.getOrderNum(false, mallOrderVo.getOtype()));
			//主订单号
			order.setParentNum(parentNum);
			//付款人
			order.setFromMemberId(userId);
			//下单时间
			order.setCreateTime(currTime);
			//订单进度
			order.setProcess(FieldConstant.PAY_PROCESS_UNPAY);
			//订单状态
			order.setState(SystemConstant.ENABLE);
			Long id = mallOrderDao.insertAndReturnId(order);
			if(id <= 0){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "购买失败");
			}

			//订单详细信息入库
			List<ProductOrderDetail> orderDetails = new ArrayList<ProductOrderDetail>();
			List<ProductItem> productItems = checkProductInfo.get(shopId);
			for(ProductItem productItem:productItems){
				ProductOrderDetail orderDetail = new ProductOrderDetail();
				orderDetail.setBuyCount(productItem.getBuyCount());
				orderDetail.setProductId(productItem.getProductId());
				orderDetail.setProductName(productItem.getProductName());
				orderDetail.setPrice(productItem.getPrice());
				orderDetail.setOrderId(id);
				orderDetails.add(orderDetail);
			}
			productOrderDetailDao.saveAll(orderDetails);
		}

		//6.清理购物车已下单的商品
		Map<Long,ShopItem> shopItemMap = this.clearCart(cart,checkProductInfo);
		String shops = JSONObject.toJSONString(shopItemMap.values());
		cart.setShops(shops);
		cartDao.update(cart);

		//7.判断商品价格是否有变动
		Map<Long,JSONArray> floatMap = this.compareProductPrice(checkProductInfo);
		if(null != floatMap && floatMap.values().size() > 0){
			return new ResultMsg<Object>(SystemConstant.RESULT_RESULT_ORDER_ERROR, "下单商品有差价",floatMap);
		}
		JSONObject obj = new JSONObject();
		obj.put("orderId", parentNum);
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "购买成功",obj);
	}

	/**
	 * 清理已下单的购物车商品
	 * @param cart
	 * @param checkProductInfo
	 * @return
	 */
	private Map<Long,ShopItem> clearCart(MallCart cart,
			Map<Long, List<ProductItem>> checkProductInfo) {
		Map<Long,ShopItem> data = new HashMap<Long, ShopItem>();

		Map<Long,ProductItem> productItemMap = new HashMap<Long, ProductItem>();
		for(List<ProductItem> arr : checkProductInfo.values()){
			for(ProductItem o : arr){
				productItemMap.put(o.getProductId(),o);
			}
		}
		List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);
		for(ShopItem shopItem:arr){
			List<ProductItem> productItems = new ArrayList<ProductItem>();
			for(ProductItem productItem:shopItem.getProducts()){
				if(productItemMap.containsKey(productItem.getProductId())){
					continue;
				}
				productItems.add(productItem);
			}
			if(null != productItems && productItems.size() > 0){
				shopItem.setProducts(productItems);
				data.put(shopItem.getShopId(), shopItem);
			}
		}
		return data;
	}

	/**
	 * 比较商品价格是否有浮动
	 * @param checkProductInfo
	 * @return
	 */
	private Map<Long, JSONArray> compareProductPrice(
			Map<Long, List<ProductItem>> checkProductInfo) {
		Map<Long,JSONArray> data = new HashMap<Long, JSONArray>();
		//获取商品最新的价格
		List<Long> productIds = new ArrayList<Long>();
		for(List<ProductItem> arr : checkProductInfo.values()){
			for(ProductItem o : arr){
				productIds.add(o.getProductId());
			}
		}

		List<Product> products = productDao.selectByIds(productIds);
		Map<Long,Product> productMap = new HashMap<Long, Product>();
		for(Product p:products){
			productMap.put(p.getId(), p);
		}

		for(Long shopId:checkProductInfo.keySet()){
			List<ProductItem> productItems = checkProductInfo.get(shopId);
			JSONArray array = new JSONArray();
			for(ProductItem productItem:productItems){
				float orderPrice = productItem.getPrice();
				float newPrice = productMap.get(productItem.getProductId()).getPrice();
				if(newPrice - orderPrice != 0){
					JSONObject obj = new JSONObject();
					obj.put("orderPrice", orderPrice);
					obj.put("newPrice", newPrice);
					obj.put("diffPrice", newPrice-orderPrice);
					array.add(obj);
				}
			}
			if(null != array && array.size() > 0){
				data.put(shopId, array);
			}
		}
		return data;
	}

	/**
	 * 获取店铺信息
	 * @param checkProductInfo
	 * @return
	 */
	private Map<Long, Shop> getShopInfo(
			Map<Long, List<ProductItem>> checkProductInfo) {
		Map<Long,Shop> data = new HashMap<Long, Shop>();
		List<Long> shopIds = new ArrayList<Long>();
		for(Long shopId:checkProductInfo.keySet()){
			shopIds.add(shopId);
		}

		List<Shop> shops = shopDao.selectByIds(shopIds);
		for(Shop s:shops){
			data.put(s.getId(), s);
		}
		return data;
	}

	/**
	 * 计算购买商品的总价
	 * @param values
	 * @return
	 */
	private Map<Long,Float> calcCheckProductInfo(Map<Long, List<ProductItem>> checkProductInfo) {
		Map<Long,Float> data = new HashMap<Long, Float>();
		//获取商品最新的价格
		List<Long> productIds = new ArrayList<Long>();
		for(List<ProductItem> arr : checkProductInfo.values()){
			for(ProductItem o : arr){
				productIds.add(o.getProductId());
			}
		}

		List<Product> products = productDao.selectByIds(productIds);
		Map<Long,Product> productMap = new HashMap<Long, Product>();
		for(Product p:products){
			productMap.put(p.getId(), p);
		}

		for(Long shopId:checkProductInfo.keySet()){
			List<ProductItem> productItems = checkProductInfo.get(shopId);
			int index=0;
			float total = 0;
			for(ProductItem productItem:productItems){
				if(!productMap.containsKey(productItem.getProductId())){
					continue;
				}
				float price = productMap.get(productItem.getProductId()).getPrice();
				checkProductInfo.get(shopId).get(index).setPrice(price);
				total += price;
			}
			data.put(shopId, total);
		}
		return data;
	}

	/**
	 * 获取购物车选中的商品信息
	 * @param cart
	 * @return
	 */
	private Map<Long, List<ProductItem>> getCheckProductInfo(MallCart cart) {
		Map<Long, List<ProductItem>> data = new HashMap<Long, List<ProductItem>>();
		List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);
		//获取购物车选中信息
		for(ShopItem shopItem:arr){
			List<ProductItem> productItems = new ArrayList<ProductItem>();
			for(ProductItem productItem:shopItem.getProducts()){
				if(null == productItem.getCheck() || 1 != productItem.getCheck().intValue()){
					continue;
				}
				productItems.add(productItem);
			}
			if(null != productItems && productItems.size() > 0){
				data.put(shopItem.getShopId(), productItems);
			}
		}
		return data;
	}

	@Override
	public ResultMsg<?> toAddOrder(String local, Long userId, String enc) {
		//验证参数
		if(null == userId  || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("userId", "登录已失效");
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
			//获取购物车信息
			MallCart cart = cartDao.selectByMemberId(userId);
			if(null == cart || StringUtils.isEmpty(cart.getShops())){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "未查询到购物车信息");
			}

			Map<String,Object> data = new HashMap<String,Object>();

			List<ShopItem> arr = JSONArray.parseArray(cart.getShops(),ShopItem.class);

			//积分列表
			Map<String,Object> integral = new HashMap<String,Object>();
			integral.put("totalIntegral", 0);
			integral.put("existIntegral", 0);

			//优惠券列表
			List<Object> coupons = new ArrayList<Object>();
			//商品列表
			List<ProductItem> productItems = new ArrayList<ProductItem>();

			data.put("products", productItems);
			data.put("integral", integral);
			data.put("coupons", coupons);

			List<Long> productIds = new ArrayList<Long>();
			//获取购物车选中信息
			for(ShopItem shopItem:arr){
				for(ProductItem productItem:shopItem.getProducts()){
					if(null == productItem.getCheck() || 1 != productItem.getCheck().intValue()){
						continue;
					}
					productIds.add(productItem.getProductId());
					productItems.add(productItem);
				}
			}
			//查询商品列表
			List<Product> products = productDao.selectByIds(productIds);
			//修改购物车商品价格
			for(int i=0;i<productItems.size();i++){
				for(Product product:products){
					if(!productItems.get(i).getProductId().equals(product.getId())){
						continue;
					}
					productItems.get(i).setPrice(product.getPrice());
					//计算总价
					BigDecimal price = new BigDecimal(product.getPrice());
					BigDecimal buyCount = new BigDecimal(productItems.get(i).getBuyCount());
					productItems.get(i).setTotalAmount(price.multiply(buyCount).floatValue());
				}
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "操作成功",data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}


	@Override
	public List<Shop> getShopList() {
		return shopDao.selectAll();
	}

	@Override
	public ResultMsg<?> cancelOrder(String local, Long userId, String orderId, String enc) {
		//验证参数
		if(null == userId || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == userId){
				error.put("loginName", "未登录");
			}
			if(StringUtils.isEmpty(orderId)){
				error.put("orderId", "订单号必填");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+orderId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			//获取订单信息
			List<Order> orders = null;
			if(langService.isParentOrderNum(orderId)){
				orders = mallOrderDao.selectByParentNum(orderId);
			}else{
				orders = mallOrderDao.selectByOrderNum(orderId);
			}
			if(orders.isEmpty()){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "未找到对应订单");
			}

			//判断订单是否可以取消
			for(Order order : orders){
				if(!(order.getProcess().intValue() < FieldConstant.PAY_PROCESS_FINISH)){
					return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "不允许取消的订单");
				}
			}

			int result = 0 ;
			for(Order order : orders){
				order.setProcess(FieldConstant.PAY_PROCESS_CANCEL);
				result = mallOrderDao.update(order);
			}
			if(result > 0){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "取消成功");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "取消失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "系统出现异常,请稍后再试");
		}
	}


}
