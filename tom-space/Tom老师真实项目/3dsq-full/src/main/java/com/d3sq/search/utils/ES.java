package com.d3sq.search.utils;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceRangeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.StringUtils;

import com.d3sq.center.vo.ESVo;
import com.d3sq.model.entity.City;
import com.d3sq.model.entity.Residential;
import com.d3sq.model.entity.Shop;
import com.d3sq.shopping.vo.ProductVo;
import com.d3sq.shopping.vo.ServiceVo;


public class ES {

	private static Logger LOG = Logger.getLogger(ES.class);

	/**
	 * 获取附近的商品(包括商品和服务、店铺)
	 * @param index
	 * @param type
	 * @param lat
	 * @param lon
	 */
	public static ESVo getNearbyIndex(String index, String type, double lat, double lon,String wd,Long shopId,Long lastCreateTime,Integer pageSize) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(pageSize);//返回多少条记录
			QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			
			//按评分等级排序
			srb.addSort(SortBuilders.scoreSort());
			
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);
			
			//字段优先
			srb.addSort(SortBuilders.fieldSort("productName"));
			srb.addSort(SortBuilders.fieldSort("shopName"));

			BoolQueryBuilder qbmust = QueryBuilders.boolQuery();
			//模糊匹配搜索
			if(!StringUtils.isEmpty(wd)){
				qbmust.must(QueryBuilders.multiMatchQuery(wd, "productName","shopName"));
			}

			if(null != shopId){
				qbmust.must(QueryBuilders.termQuery("shopId", shopId));
			}

			if(null != lastCreateTime){
				qbmust.must(QueryBuilders.rangeQuery("creatTime").lt(lastCreateTime));
			}

			srb.setQuery(qbmust);
			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的商品、服务、店铺(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal(Double.valueOf(hit.getSortValues()[1].toString()));
				hit.getSource().put("distance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	/**
	 * 查找附近的商品
	 * @param index
	 * @param type
	 * @param lat
	 * @param lon
	 * @param name
	 * @param lastCreateTime
	 * @param pageSize
	 * @return
	 */
	public static ESVo getNearbyProduct(String index, String type, double lat, double lon,String wd,Long lastCreateTime,Integer pageSize) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			//设置分页limit 0,size
			srb.setFrom(0).setSize(pageSize);//返回多少条记录
			
			//相当于给SQL语句增加查询条件
			//location = PLANE
			QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			
			//AND  location.lat = lat and  location.lon = lon order by asc
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);
			
			//按评分等级排序
			srb.addSort(SortBuilders.scoreSort());

			
			//productName = wd or  shopName = wd
			BoolQueryBuilder qbmust = QueryBuilders.boolQuery();
			//模糊匹配搜索
			if(!StringUtils.isEmpty(wd)){
				qbmust.must(QueryBuilders.multiMatchQuery(wd, "productName","shopName"));
			}
			if(null != lastCreateTime){
				qbmust.must(QueryBuilders.rangeQuery("creatTime").lt(lastCreateTime));
			}

			
			
			//执行execute方法
			srb.setQuery(qbmust);
			SearchResponse searchResponse = srb.execute().actionGet();

			
			
			//Hits  高亮
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的商品(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			
			
			//对搜索结果进行封装
			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
				hit.getSource().put("distance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	
	
	/**
	 * 查找附近的服务
	 * @param index
	 * @param type
	 * @param lat
	 * @param lon
	 * @param name
	 * @param lastCreateTime
	 * @param pageSize
	 * @return
	 */
	public static ESVo getNearbyService(String index, String type, double lat, double lon,String wd,Long lastCreateTime,Integer pageSize) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(pageSize);//返回多少条记录
			QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);
			
			//按评分等级排序
			srb.addSort(SortBuilders.scoreSort());

			BoolQueryBuilder qbmust = QueryBuilders.boolQuery();
			//模糊匹配搜索
			if(!StringUtils.isEmpty(wd)){
				qbmust.must(QueryBuilders.multiMatchQuery(wd, "serviceName","shopName"));
			}
			if(null != lastCreateTime){
				qbmust.must(QueryBuilders.rangeQuery("creatTime").lt(lastCreateTime));
			}

			srb.setQuery(qbmust);
			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的服务(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
				hit.getSource().put("distance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	/**
	 * 查找附近的店铺
	 * @param index
	 * @param type
	 * @param lat
	 * @param lon
	 * @param name
	 * @param shopId
	 * @param lastCreateTime
	 * @param pageSize
	 * @return
	 */
	public static ESVo getNearbyShop(String index,
			String type, Double lat, Double lon, String wd, Long lastCreateTime, Integer pageSize) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(pageSize);//返回多少条记录
			QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);

			BoolQueryBuilder qbmust = QueryBuilders.boolQuery();
			//模糊匹配搜索
			if(!StringUtils.isEmpty(wd)){
				qbmust.must(QueryBuilders.multiMatchQuery(wd, "productName","shopName"));
			}

			if(null != lastCreateTime){
				qbmust.must(QueryBuilders.rangeQuery("creatTime").lt(lastCreateTime));
			}

			srb.setQuery(qbmust);
			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的店铺(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
				hit.getSource().put("distance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	/**
	 * 附近的小区
	 * @param residentialSearchIndex
	 * @param residentialSearchIndexType
	 * @param lat
	 * @param lon
	 * @param name
	 * @param shopId
	 * @param lastCreateTime
	 * @param pageSize
	 * @return
	 */
	public static ESVo getNearbyResidential(String index,
			String type, Double lat, Double lon,String wd,Long lastCreateTime,Integer pageSize) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(pageSize);//返回多少条记录
			QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);

			BoolQueryBuilder qbmust = QueryBuilders.boolQuery();
			//模糊匹配搜索
			if(!StringUtils.isEmpty(wd)){
				qbmust.must(QueryBuilders.multiMatchQuery(wd,"shortName"));
			}

			if(null != lastCreateTime){
				qbmust.must(QueryBuilders.rangeQuery("creatTime").lt(lastCreateTime));
			}

			srb.setQuery(qbmust);
			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的小区(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
				hit.getSource().put("distance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN) + DistanceUnit.METERS.toString());
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	
	public static ESVo getNearbyCity(String index, String type, Double lat, Double lon) {
		ESVo vo = new ESVo();
		try {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(1000);//返回多少条记录
			/*QueryBuilder postFilter = new GeoDistanceRangeQueryBuilder("location").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(postFilter);
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lat, lon);
			srb.addSort(sort);*/
			
			srb.addSort(SortBuilders.fieldSort("pinyin"));

			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			// 搜索耗时
			Float usetime = searchResponse.getTookInMillis() / 1000f;
			LOG.info("我附近的城市(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");

			List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : searchHists) {
				data.add(hit.getSource());
			}
			vo.setData(data);
			vo.setTotal(hits.getTotalHits());
			vo.setUseTime(usetime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}



	/**
	 * 初次使用创建索引
	 * @param indexName
	 * @param indexType
	 * @throws IOException
	 */
	public static void createIndex(String indexName, String indexType,Map<String,String> mappingMap) throws IOException {
		Client esClient = ESTools.getClient();
		// 创建Mapping
		XContentBuilder mapping = createMapping(indexType,mappingMap);
		LOG.debug("mapping:" + mapping.string());
		// 创建一个空索引
		//相当于创建数据库
		esClient.admin().indices().prepareCreate(indexName).execute().actionGet();
		//创建一个数据库表
		PutMappingRequest putMapping = Requests.putMappingRequest(indexName).type(indexType).source(mapping);
		//建立约束
		PutMappingResponse response = esClient.admin().indices().putMapping(putMapping).actionGet();
		if (!response.isAcknowledged()) {
			LOG.error("Could not define mapping for type [" + indexName + "]/[" + indexType + "].");
		} else {
			LOG.debug("Mapping definition for [" + indexName + "]/[" + indexType + "] succesfully created.");
		}
	}

	/**
	 * 删除并重建索引
	 * @param indexName
	 * @param indexType
	 * @throws IOException
	 */
	public static void deleteIndex(String indexName, String indexType,Map<String,String> mappingMap) throws IOException {
		Client esClient = ESTools.getClient();
		esClient.admin().indices().prepareDelete(indexName).execute().actionGet();
		createIndex(indexName,indexType,mappingMap);
	}


	/**
	 * 创建mapping
	 * @param indexType
	 * @return
	 */
	public static XContentBuilder createMapping(String indexType,Map<String,String> mappingMap) {
		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder().startObject()
					// 索引库名（类似数据库中的表）
					.startObject(indexType).startObject("properties");
			//索引列
			for(String key:mappingMap.keySet()){
				if("string".equalsIgnoreCase(mappingMap.get(key))){
					mapping.startObject(key)
					.field("type", mappingMap.get(key))
					.field("analyzer","ik").field("search_analyzer","ik_smart")
					.endObject();
				}else{
					mapping.startObject(key)
					.field("type", mappingMap.get(key))
					.endObject();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping;
	}


	public static String obj2JsonProductData(ProductVo vo) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("productId", vo.getId()).field("productName", vo.getName()).field("pinyin", vo.getPinyin()).field("stock", vo.getStock())
			.field("unitName", vo.getUnitName()).field("creatTime", vo.getCreateTime()).field("ptype", vo.getPtype())
			.field("price", vo.getPrice()).field("shopId", vo.getShopId()).field("shopName", vo.getShopName()).field("coverImg", vo.getCoverImg())
			.startObject("location").field("lat",vo.getLat()).field("lon",vo.getLon()).endObject()
			.endObject();
			jsonData = jsonBuild.string();
			//System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}
	
	public static String obj2JsonServiceData(ServiceVo vo) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("serviceId", vo.getId()).field("serviceName", vo.getName()).field("pinyin", vo.getPinyin()).field("price", vo.getPrice()).field("stock", vo.getStock())
			.field("creatTime", vo.getCreateTime()).field("shopId", vo.getShopId()).field("shopName", vo.getShopName()).field("coverImg", vo.getCoverImg())
			.startObject("location").field("lat",vo.getLat()).field("lon",vo.getLon()).endObject()
			.endObject();
			jsonData = jsonBuild.string();
			//System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}



	public static String obj2JsonShopData(Shop shop) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("shopId", shop.getId()).field("shopName", shop.getName()).field("creatTime", shop.getCreateTime())
			.field("pinyin", shop.getPinyin()).field("coverImg", shop.getCoverImg())
			.startObject("location").field("lat",shop.getLat()).field("lon",shop.getLon()).endObject()
			.endObject();
			jsonData = jsonBuild.string();
			//System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	public static String obj2JsonResidentialData(Residential residential) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("id", residential.getId()).field("cityNamePath", residential.getCityNamePath()).field("shortName", residential.getShortName()).field("creatTime", residential.getCreateTime()).field("cityPath", residential.getCityPath())
			.field("pinyin", residential.getPinyin()).startObject("location").field("lat",residential.getLat()).field("lon",residential.getLon()).endObject()
			.endObject();
			jsonData = jsonBuild.string();
			//System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	public static String obj2JsonCityData(City city) {
		String jsonData = null;
		try {
			// 使用XContentBuilder创建json数据
			XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
			jsonBuild.startObject().field("shortName", city.getShortName()).field("xpath", city.getXpath())
			.field("pinyin", city.getPinyin()).startObject("location").field("lat",city.getLat()).field("lon",city.getLon()).endObject()
			.endObject();
			jsonData = jsonBuild.string();
			//System.out.println(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

}
