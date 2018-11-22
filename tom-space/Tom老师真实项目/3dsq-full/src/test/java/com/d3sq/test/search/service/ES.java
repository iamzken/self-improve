package com.d3sq.test.search.service;



import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.GeoDistanceRangeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.test.model.entity.Shop;


/**
 * 实现附近的商家功能，最大限额1000人，1米到100米范围内的商家列表
 */
@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ES {

		/**
		 * 初次使用创建索引
		 * @param indexName
		 * @param indexType
		 * @throws IOException
		 */
		public static void createIndex(String indexName, String indexType) throws IOException {
			Client esClient = ESTools.getClient();
			// 创建Mapping
			XContentBuilder mapping = createMapping(indexType);
			System.out.println("mapping:" + mapping.string());
			// 创建一个空索引
			esClient.admin().indices().prepareCreate(indexName).execute().actionGet();
			PutMappingRequest putMapping = Requests.putMappingRequest(indexName).type(indexType).source(mapping);
			PutMappingResponse response = esClient.admin().indices().putMapping(putMapping).actionGet();
			if (!response.isAcknowledged()) {
				System.out.println("Could not define mapping for type [" + indexName + "]/[" + indexType + "].");
			} else {
				System.out.println("Mapping definition for [" + indexName + "]/[" + indexType + "] succesfully created.");
			}
		}
		
		
		/**
		 * 删除并重建索引
		 * @param indexName
		 * @param indexType
		 * @throws IOException
		 */
		public static void deleteIndex(String indexName, String indexType) throws IOException {
			Client esClient = ESTools.getClient();
			esClient.admin().indices().prepareDelete(indexName).execute().actionGet();
			createIndex(indexName,indexType);
		}
		
		
		
		public static String obj2JsonShopData(Shop shop) {
			String jsonData = null;
			try {
				// 使用XContentBuilder创建json数据
				XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
				jsonBuild.startObject().field("id", shop.getId()).field("name", shop.getName()).startObject("location").field("lat",shop.getLat()).field("lon",shop.getLon()).endObject()
						.endObject();
				jsonData = jsonBuild.string();
				System.out.println(jsonData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonData;
		}

		/**
		 * 创建mapping
		 * @param indexType
		 * @return
		 */
		public static XContentBuilder createMapping(String indexType) {
			XContentBuilder mapping = null;
			try {
				mapping = XContentFactory.jsonBuilder().startObject()
						// 索引库名（类似数据库中的表）
						.startObject(indexType).startObject("properties")
						// ID
						.startObject("id").field("type", "long").endObject()
						// 姓名
						.startObject("name").field("type", "string").endObject()
						// 位置
						.startObject("location").field("type", "geo_point").endObject()
				.endObject().endObject().endObject();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mapping;
		}

		/**
		 * 添加100000条模拟数据
		 * @param indexName
		 * @param indexType
		 * @return
		 */
		public static Integer addIndexData100000(String indexName, String indexType) {
			Client client = ESTools.getClient();
			List<String> shopList = new ArrayList<String>();

			double lat = 39.929986;
			double lon = 116.395645;
			for (int i = 0; i < 100000; i++) {
				double max = 0.00001;
				double min = 0.000001;
				Random random = new Random();
				double s = random.nextDouble() % (max - min + 1) + max;
				DecimalFormat df = new DecimalFormat("######0.000000");
				// System.out.println(s);
				String lons = df.format(s + lon);
				String lats = df.format(s + lat);
				Double dlon = Double.valueOf(lons);
				Double dlat = Double.valueOf(lats);

				Shop shop = new Shop((long)i, "商家"+i, dlat, dlon);
				shopList.add(obj2JsonShopData(shop));
			}
			// 创建索引库
			List<IndexRequest> requests = new ArrayList<IndexRequest>();
			for (int i = 0; i < shopList.size(); i++) {
				IndexRequest request = client.prepareIndex(indexName, indexType).setSource(shopList.get(i)).request();
				requests.add(request);
			}

			// 批量创建索引
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (IndexRequest request : requests) {
				bulkRequest.add(request);
			}

			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				System.out.println("批量创建索引错误！");
			}
			return bulkRequest.numberOfActions();
		}

		/**
		 * 获取附近的商家
		 * @param index
		 * @param type
		 * @param lat
		 * @param lon
		 */
		public static void getNearbyShop(String index, String type, double lat, double lon) {
			Client client = ESTools.getClient();
			SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
			srb.setFrom(0).setSize(1000);//1000人
			// lon, lat位我的坐标，查询距离我1米到100米
			
			
			QueryBuilder builder = new GeoDistanceRangeQueryBuilder("location").point(lon, lat).from("1m").to("100m").optimizeBbox("memory").geoDistance(GeoDistance.PLANE);
			srb.setPostFilter(builder);
			// 获取距离多少公里 这个才是获取点与点之间的距离的
			GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
			sort.unit(DistanceUnit.METERS);
			sort.order(SortOrder.ASC);
			sort.point(lon, lat);
			srb.addSort(sort);

			SearchResponse searchResponse = srb.execute().actionGet();

			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHists = hits.getHits();
			 // 搜索耗时
	        Float usetime = searchResponse.getTookInMillis() / 1000f;
			System.out.println("我附近的商家(" + hits.getTotalHits() + "个)，检索耗时("+usetime+"秒)：");
			for (SearchHit hit : searchHists) {
				System.out.println(JSONObject.toJSONString(hit));
				String name = (String) hit.getSource().get("name");
				Map<String,Double> location = (Map<String,Double>)hit.getSource().get("location");
				// 获取距离值，并保留两位小数点
				BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
				Map<String, Object> hitMap = hit.getSource();
				// 在创建MAPPING的时候，属性名的不可为geoDistance。
				hitMap.put("geoDistance", geoDis.setScale(0, BigDecimal.ROUND_HALF_DOWN));
				System.out.println(name+"的坐标："+location + "距离我" + hit.getSource().get("geoDistance") + DistanceUnit.METERS.toString());
			}

		}
		
		
		
		@Test
		public void execute() {
			
			try{
				String index = "es"; //相当于数据库名称
				String type = "shop";	//相当于表名
				
				//1.首次创建索引
//				createIndex(index,type);
				
				//2.删除索引
//				deleteIndex(index,type);
				
				//3、初始化数据
//				addIndexData100000(index, type);
				
				
				//4、获取附近的商家
				double lat = 116.395645; //经度
				double lon = 39.929986;	 //纬度
				long start = System.currentTimeMillis();
				getNearbyShop(index, type, lat, lon);
				long end = System.currentTimeMillis();
				System.out.println("总共耗时" + (end - start) + "毫秒");
				
				
				//client.close();// 1.5.2版本用完不用关闭
			}catch(Exception e){
				e.printStackTrace();
			}
			 
		}
	
}
