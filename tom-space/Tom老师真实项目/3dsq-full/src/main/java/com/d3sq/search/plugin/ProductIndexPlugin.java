package com.d3sq.search.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.core.plugin.queue.QueuePlugin;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.search.utils.ES;
import com.d3sq.search.utils.ESTools;
import com.d3sq.shopping.vo.ProductVo;

/**
 * 更新商品索引
 *
 */
@Component
public class ProductIndexPlugin extends QueuePlugin {

	@Override
	protected void process(QueueItem item) {
		//判断索引是否存在
		if(!ESTools.isExistsType(IndexConstant.COMMODITY_SEARCH_INDEX)){
			final Map<String,String> mappingMap = new HashMap<String, String>();
			mappingMap.put("productId", "long");
			mappingMap.put("productName", "string");
			mappingMap.put("pinyin", "string");
			mappingMap.put("stock", "Integer");
			mappingMap.put("unitName", "string");
			mappingMap.put("creatTime", "long");
			mappingMap.put("price", "float");
			mappingMap.put("shopId", "long");
			mappingMap.put("shopName", "string");
			mappingMap.put("coverImg", "string");
			mappingMap.put("location", "geo_point");
			//创建索引
			try {
				ES.createIndex(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE,mappingMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Client client = ESTools.getClient();
		//新增
		if(item.getOpt() == QueueTarget.OPT_ADD){
			//json转对象
			ProductVo vo = JSONObject.toJavaObject((JSONObject)JSON.toJSON(item.getData()), ProductVo.class);
			String jsonData = ES.obj2JsonProductData(vo);
			IndexRequest request = client.prepareIndex(IndexConstant.COMMODITY_SEARCH_INDEX, IndexConstant.COMMODITY_SEARCH_INDEX_TYPE).setSource(jsonData).request();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			bulkRequest.add(request);
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				item.setOptResult(false);
			}
			item.setOptResult(true);
		}
		System.out.println(JSONObject.toJSONString(item));
	}

}
