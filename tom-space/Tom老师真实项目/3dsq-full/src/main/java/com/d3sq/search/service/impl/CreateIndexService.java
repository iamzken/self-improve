package com.d3sq.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.core.dao.CityDao;
import com.d3sq.model.entity.City;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.Residential;
import com.d3sq.model.entity.Shop;
import com.d3sq.model.helper.RuleItem;
import com.d3sq.search.dao.ProductDao;
import com.d3sq.search.dao.ResidentialDao;
import com.d3sq.search.dao.ServiceDao;
import com.d3sq.search.dao.ShopDao;
import com.d3sq.search.service.ICreateIndexService;
import com.d3sq.search.utils.ES;
import com.d3sq.search.utils.ESTools;
import com.d3sq.shopping.vo.ProductVo;
import com.d3sq.shopping.vo.ServiceVo;

@Service("createIndexService")
public class CreateIndexService implements ICreateIndexService {
	//@Autowired IProductService productService; 
	//@Autowired IShoppingService shopService;
	@Autowired ShopDao sShopdao;
	@Autowired ProductDao sProductDao;
	@Autowired ServiceDao sServiceDao;
	@Autowired ResidentialDao sResidentialDao;
	@Autowired CityDao cityDao;


	/**
	 * 创建首页搜索
	 */
	@Override
	public ResultMsg<Object> createHomeIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("productId", "long");
		mappingMap.put("productName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("ptype", "Integer");
		mappingMap.put("stock", "Integer");
		mappingMap.put("unitName", "string");
		mappingMap.put("creatTime", "long");
		mappingMap.put("price", "float");
		mappingMap.put("shopId", "long");
		mappingMap.put("shopName", "string");
		mappingMap.put("catelogIds", "string");
		mappingMap.put("coverImg", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.HOME_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.HOME_SEARCH_INDEX, IndexConstant.HOME_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.HOME_SEARCH_INDEX, IndexConstant.HOME_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Client client = ESTools.getClient();
		//商品
		List<Product> products = sProductDao.selectAll();
		List<Shop> shopList = sShopdao.selectAll();
		List<ProductVo> productVos = new ArrayList<ProductVo>();
		for(Product product : products){
			ProductVo productVo = new ProductVo();
			DataUtils.copySimpleObject(product, productVo);
			productVo.setPtype(FieldConstant.PRODUCT_PTYPE_COMMOD);

			for(Shop shop : shopList){
				if(product.getShopId().equals(shop.getId())){
					//productVo.setCatelogIds(shop.getCatelogIds());
					productVo.setLat(shop.getLat());
					productVo.setLon(shop.getLon());
					productVo.setShopName(shop.getName());
					break;
				}
			}
			productVos.add(productVo);
		}
		
		//服务
		List<com.d3sq.model.entity.Service> services = sServiceDao.selectAll();
		for(com.d3sq.model.entity.Service service : services){
			ProductVo productVo = new ProductVo();
			productVo.setPtype(FieldConstant.PRODUCT_PTYPE_SERVICE);
			productVo.setId(service.getId());
			productVo.setName(service.getName());
			productVo.setCoverImg(service.getCoverImg());
			productVo.setShopId(service.getShopId());
			productVo.setPinyin(service.getPinyin());
			productVo.setCreateTime(service.getCreateTime());
			for(Shop shop : shopList){
				if(service.getShopId().equals(shop.getId())){
					//productVo.setCatelogIds(shop.getCatelogIds());
					productVo.setLat(shop.getLat());
					productVo.setLon(shop.getLon());
					productVo.setShopName(shop.getName());
					break;
				}
			}
			productVos.add(productVo);
		}

		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(ProductVo vo:productVos){
			String jsonData = ES.obj2JsonProductData(vo);
			IndexRequest request = client.prepareIndex(IndexConstant.HOME_SEARCH_INDEX, IndexConstant.HOME_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}

	/**
	 * 商品搜索
	 */
	@Override
	public ResultMsg<Object> createCommodityIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("productId", "long");
		mappingMap.put("productName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("ptype", "Integer");
		mappingMap.put("stock", "Integer");
		mappingMap.put("unitName", "string");
		mappingMap.put("creatTime", "long");
		mappingMap.put("price", "float");
		mappingMap.put("shopId", "long");
		mappingMap.put("shopName", "string");
		mappingMap.put("catelogIds", "string");
		mappingMap.put("coverImg", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.COMMODITY_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//往索引添加数据
		List<Product> products = sProductDao.selectAll();
		if(products.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建0条记录索引");
		}

		List<Shop> shopList = sShopdao.selectAll();

		List<ProductVo> productVos = new ArrayList<ProductVo>();

		for(Product product : products){
			ProductVo productVo = new ProductVo();
			DataUtils.copySimpleObject(product, productVo);

			for(Shop shop : shopList){
				if(product.getShopId().equals(shop.getId())){
					//productVo.setCatelogIds(shop.getCatelogIds());
					productVo.setLat(shop.getLat());
					productVo.setLon(shop.getLon());
					productVo.setShopName(shop.getName());
					break;
				}
			}
			productVos.add(productVo);
		}
		Client client = ESTools.getClient();
		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(ProductVo vo:productVos){
			String jsonData = ES.obj2JsonProductData(vo);
			IndexRequest request = client.prepareIndex(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}

	/**
	 * 服务搜索
	 */
	@Override
	public ResultMsg<Object> createServiceIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("serviceId", "long");
		mappingMap.put("serviceName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("price", "string");
		mappingMap.put("stock", "Integer");
		mappingMap.put("creatTime", "long");
		mappingMap.put("shopId", "long");
		mappingMap.put("shopName", "string");
		mappingMap.put("coverImg", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.SERVICE_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.SERVICE_SEARCH_INDEX, IndexConstant.SERVICE_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.SERVICE_SEARCH_INDEX, IndexConstant.SERVICE_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//往索引添加数据
		List<com.d3sq.model.entity.Service> services = sServiceDao.selectAll();
		if(services.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建0条记录索引");
		}

		List<Shop> shopList = sShopdao.selectAll();

		List<ServiceVo> serviceVos = new ArrayList<ServiceVo>();

		for(com.d3sq.model.entity.Service service : services){
			ServiceVo serviceVo = new ServiceVo();
			DataUtils.copySimpleObject(service, serviceVo);
			String price = this.getPrice(service);
			serviceVo.setPrice(price);
			for(Shop shop : shopList){
				if(service.getShopId().equals(shop.getId())){
					//productVo.setCatelogIds(shop.getCatelogIds());
					serviceVo.setLat(shop.getLat());
					serviceVo.setLon(shop.getLon());
					serviceVo.setShopName(shop.getName());
					break;
				}
			}
			serviceVos.add(serviceVo);
		}

		Client client = ESTools.getClient();

		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(ServiceVo vo:serviceVos){
			String jsonData = ES.obj2JsonServiceData(vo);
			IndexRequest request = client.prepareIndex(IndexConstant.SERVICE_SEARCH_INDEX, IndexConstant.SERVICE_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}
	
	/**
	 * 获取服务收费区间
	 * @param feeStand
	 * @return
	 */
	private String getPrice(com.d3sq.model.entity.Service service) {
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

	/**
	 * 店铺搜索
	 */
	@Override
	public ResultMsg<Object> createShopIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("shopId", "long");
		mappingMap.put("shopName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("creatTime", "long");
		mappingMap.put("coverImg", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.SHOP_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.SHOP_SEARCH_INDEX, IndexConstant.SHOP_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.SHOP_SEARCH_INDEX, IndexConstant.SHOP_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Shop> shops = sShopdao.selectAll();
		if(shops.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建0条记录索引");
		}
		Client client = ESTools.getClient();
		//往索引添加数据
		//List<ProductVo> productVos = productService.getProductList(FieldConstant.PRODUCT_PTYPE_SERVICE);

		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(Shop shop:shops){
			String jsonData = ES.obj2JsonShopData(shop);
			IndexRequest request = client.prepareIndex(IndexConstant.SHOP_SEARCH_INDEX, IndexConstant.SHOP_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		//System.out.println(JSONObject.toJSONString(bulkResponse));
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}



	/**
	 * 小区搜索
	 */
	@Override
	public ResultMsg<Object> createResidentialIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("id", "long");
		mappingMap.put("cityName", "string");
		mappingMap.put("shortName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("creatTime", "long");
		mappingMap.put("cityPath", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.RESIDENTIAL_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.RESIDENTIAL_SEARCH_INDEX, IndexConstant.RESIDENTIAL_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.RESIDENTIAL_SEARCH_INDEX, IndexConstant.RESIDENTIAL_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Residential> residentials = sResidentialDao.selectAll();
		if(residentials.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建0条记录索引");
		}
		Client client = ESTools.getClient();
		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(Residential residential:residentials){
			String jsonData = ES.obj2JsonResidentialData(residential);
			IndexRequest request = client.prepareIndex(IndexConstant.RESIDENTIAL_SEARCH_INDEX, IndexConstant.RESIDENTIAL_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		//System.out.println(JSONObject.toJSONString(bulkResponse));
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}
	
	
	/**
	 * 城市搜索
	 */
	@Override
	public ResultMsg<Object> createCityIndex() {
		final Map<String,String> mappingMap = new HashMap<String, String>();
		mappingMap.put("shortName", "string");
		mappingMap.put("pinyin", "string");
		mappingMap.put("xpath", "string");
		mappingMap.put("location", "geo_point");

		try {
			//判断索引是否存在
			if(ESTools.isExistsType(IndexConstant.CITY_SEARCH_INDEX)){
				//删除索引并重建
				ES.deleteIndex(IndexConstant.CITY_SEARCH_INDEX, IndexConstant.CITY_SEARCH_INDEX_TYPE,mappingMap);
			}else{
				//创建索引
				ES.createIndex(IndexConstant.CITY_SEARCH_INDEX, IndexConstant.CITY_SEARCH_INDEX_TYPE,mappingMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<City> citys = cityDao.selectByLevelType(FieldConstant.CITY_LEVELTYPE_CITY);
		if(citys.isEmpty()){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建0条记录索引");
		}
		Client client = ESTools.getClient();
		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for(City city:citys){
			String jsonData = ES.obj2JsonCityData(city);
			IndexRequest request = client.prepareIndex(IndexConstant.CITY_SEARCH_INDEX, IndexConstant.CITY_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR,"索引创建失败");
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS,"索引创建成功","成功创建"+bulkRequest.numberOfActions()+"条记录索引");
	}
}
