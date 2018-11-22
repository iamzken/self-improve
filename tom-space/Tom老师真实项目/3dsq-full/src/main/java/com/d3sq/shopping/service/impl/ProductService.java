package com.d3sq.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;
import javax.core.common.utils.DataUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.common.utils.SystemUtil;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.KindProduct;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.Shop;
import com.d3sq.search.plugin.ProductIndexPlugin;
import com.d3sq.shopping.dao.KindProductDao;
import com.d3sq.shopping.dao.ProductDao;
import com.d3sq.shopping.dao.ShopDao;
import com.d3sq.shopping.service.IProductService;
import com.d3sq.shopping.vo.AddProductVo;
import com.d3sq.shopping.vo.ProductVo;

@Service("productService")
public class ProductService implements IProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private KindProductDao kindProductDao;
	@Autowired 
	private ProductIndexPlugin productIndexPlugin;

	@Override
	public ResultMsg<?> addProduct(String local,Long userId, AddProductVo productVo, String enc) {

		String productName = productVo.getProductName();
		Long shopId = productVo.getShopId();
		Float price = productVo.getPrice();
		Long kindId = productVo.getKindId();
		//验证参数
		if(StringUtils.isEmpty(productName) || StringUtils.isEmpty(productVo.getCoverImg()) || null == kindId || null == shopId ||  null == price   || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(productName)){
				error.put("productName", "商品名称未填写");
			}
			if(null == kindId){
				error.put("kindId", "未选择分类");
			}
			if(null == shopId){
				error.put("shopId", "未选择店铺");
			}
			if(null == price){
				error.put("price", "商品价格未填写");
			}
			if(StringUtils.isEmpty(productVo.getCoverImg())){
				error.put("coverImg", "封面图片必传");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//验证封面图片格式
//		if(!SystemUtil.validImgByJson(productVo.getCoverImg())){
//			JSONObject error = new JSONObject();
//			error.put("coverImg", "封面图片格式不正确");
//			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
//		}

		if(!StringUtils.isEmpty(productVo.getPhotos())){
			boolean [] valids = SystemUtil.validImgArrByJson(productVo.getPhotos());
			int i = 1;
			for(boolean valid : valids){
				i++;
				if(valid){
					continue;
				}
				JSONObject error = new JSONObject();
				error.put("photos", "第"+i+"张图文介绍图片格式不正确");
				return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
			}
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(userId+""+shopId+kindId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		try {
			//转义图片url斜杠
			String photos =  productVo.getPhotos().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\\\\/", "/");
//			productVo.setPhotos(photos);
			Product product = new Product();
			DataUtils.copySimpleObject(productVo, product);
			product.setPhotos(photos);
			product.setName(productName);
			product.setCreateTime(System.currentTimeMillis());
			product.setState(SystemConstant.ENABLE);
			product.setPinyin(PinyinUtil.converterToSpell(productName).split(",")[0]);
			Long id = productDao.insert(product);
			if(id > 0){
				//入库商品分类
				KindProduct kindProduct = new KindProduct();
				kindProduct.setKindId(productVo.getKindId());
				kindProduct.setKindPath(productVo.getKindPath());
				kindProduct.setProductId(id);
				kindProductDao.insertAndReturnId(kindProduct);

				product.setId(id);
				//把商品添加到索引
				ProductVo vo = new ProductVo();
				DataUtils.copySimpleObject(product, vo);
				//查找对应店铺信息
				Shop shop = shopDao.selectById(vo.getShopId());
				vo.setLat(shop.getLat());
				vo.setLon(shop.getLon());
				vo.setShopName(shop.getName());
				QueueItem item = new QueueItem(productIndexPlugin,IndexConstant.COMMODITY_SEARCH_INDEX, QueueTarget.OPT_ADD, id, JSONObject.toJSON(vo));
				productIndexPlugin.push(item);
				
				JSONObject r = JSON.parseObject(JSON.toJSONString(product));
				r.put("photos", JSON.parseArray(product.getPhotos()));
				
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加成功",r);
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加出现异常");
		}
	}

	@Override
	public ResultMsg<?> updateProduct(String local,Long id, String name, Long shopId,
			String intro, String coverImg, Float price, Float sale,
			int stock, String enc) {
		//验证参数
		if(null == id || StringUtils.isEmpty(name) || null == shopId ||  null == price   || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == id){
				error.put("id", "商品id为空");
			}
			if(StringUtils.isEmpty(name)){
				error.put("name", "商品名称未填写");
			}
			if(null == shopId){
				error.put("shopId", "未选择店铺");
			}
			if(null == price){
				error.put("price", "商品价格未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(id+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			Product prodcut = productDao.getById(id);
			prodcut.setShopId(shopId);
			prodcut.setName(name);
			prodcut.setIntro(intro);
			prodcut.setPrice(price);
			prodcut.setSale(sale);
			prodcut.setCoverImg(coverImg);
			prodcut.setStock(stock);
			int result = productDao.update(prodcut);
			if(result > 0){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "修改成功",prodcut);
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "修改失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "修改出现异常");
		}
	}

	@Override
	public ResultMsg<?> deleteProduct(String local, Long id, String enc) {
		//验证参数
		if(null == id || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == id){
				error.put("id", "商品id为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(id+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			boolean result = productDao.delete(id);
			if(result){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "删除成功");
			}else{
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "删除失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "删除出现异常");
		}
	}

	@Override
	public ResultMsg<?> getProduct(String local, String domain, Long productId,
			String enc) {
		if(null == productId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == productId){
				error.put("productId", "商品id为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(productId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		Product product = productDao.getById(productId);
		if(null == product){
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "商品未找到");
		}
		ProductVo vo = new ProductVo();
		DataUtils.copySimpleObject(product, vo);
		Shop shop = shopDao.selectById(product.getShopId());
		vo.setShopName(shop.getName());
		vo.setLat(shop.getLat());
		vo.setLon(shop.getLon());
		
		
		JSONObject r = JSON.parseObject(JSON.toJSONString(vo));
		r.put("photos", JSON.parseArray(vo.getPhotos()));
		
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",r);
	}

	@Override
	public ResultMsg<?> getProductList(String local, String domain, String enc) {
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}

		List<ProductVo> list = new ArrayList<ProductVo>();

		List<Product> productList = productDao.selectAll();

		List<Shop> shopList = shopDao.selectAll();


		for(Product product : productList){
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
			list.add(productVo);
		}
		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",list);
	}


	@Override
	public List<ProductVo> getProductList() {
		List<ProductVo> list = new ArrayList<ProductVo>();

		List<Product> productList = productDao.selectAll();

		List<Shop> shopList = shopDao.selectAll();


		for(Product product : productList){
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
			list.add(productVo);
		}
		return list;
	}

	@Override
	public ResultMsg<?> getProductByCatalogueId(String local, String domain,Double lon,Double lat,
			String catalogueId, Long lastProductId, Integer pageSize, String enc) {
		if(null == lon || null == lat || null == catalogueId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lon || null == lat){
				error.put("dlon", "定位失败");
			}
			if(null == catalogueId){
				error.put("catalogueId", "分类id为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(catalogueId+""+lastProductId).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			List<Map<String,Object>> products = productDao.selectByCatalogueIdForPage(catalogueId,lastProductId,pageSize);

			List<JSONObject> dataList = new ArrayList<JSONObject>();

			for(Map<String,Object> product:products){
				JSONObject data = new JSONObject();
				data.put("id", product.get("id"));
				data.put("name", product.get("name"));
				data.put("price", product.get("price"));
				//计算距离
				double distance = this.getDistance(lon, lat, DataUtils.getDouble(product.get("longitude")), DataUtils.getDouble(product.get("latitude")));
				data.put("distance", distance);
				dataList.add(data);
			}

			//按距离排序
			Collections.sort(dataList, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject a, JSONObject b) {
					double aDis = a.getDouble("distance");
					double bDis = b.getDouble("distance");
					if(aDis > bDis){
						return 1;
					}else if(aDis < bDis){
						return -1;
					}else{
						return 0;
					}
				}

			});

			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "查询异常");
		}
	}


	private  double EARTH_RADIUS = 6378.137; 



	private  double rad(double d) { 

		return d * Math.PI / 180.0; 

	} 

	/** 
	 * 通过经纬度获取距离(单位：米) 
	 * @param lat1 
	 * @param lng1 
	 * @param lat2 
	 * @param lng2 
	 * @return 
	 */  
	public  double getDistance(double lat1, double lng1, double lat2, 

			double lng2) { 

		double radLat1 = rad(lat1); 

		double radLat2 = rad(lat2); 

		double a = radLat1 - radLat2; 

		double b = rad(lng1) - rad(lng2); 

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) 

				+ Math.cos(radLat1) * Math.cos(radLat2) 

				* Math.pow(Math.sin(b / 2), 2))); 

		s = s * EARTH_RADIUS; 

		s = Math.round(s * 10000) / 10000; 

		return s; 

	}

	@Override
	public List<ProductVo> getProductList(Integer ptype) {
		List<ProductVo> list = new ArrayList<ProductVo>();

		List<Product> productList = productDao.selectByPtype(ptype);

		List<Shop> shopList = shopDao.selectAll();


		for(Product product : productList){
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
			list.add(productVo);
		}
		return list;
	} 
}
