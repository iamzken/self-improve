package com.d3sq.test.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.core.common.ResultMsg;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.model.helper.ImageItem;
import com.d3sq.search.utils.ES;
import com.d3sq.shopping.service.IProductService;
import com.d3sq.shopping.vo.AddProductVo;
import com.d3sq.shopping.vo.ProductVo;
import com.d3sq.test.search.service.ESTools;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {
	@Autowired IProductService productService;


	@Ignore
	@Test
	public void addProduct(){
		String local = "ZH_CN";
		Long userId = (long)1;
		AddProductVo vo = new AddProductVo();
		vo.setProductName("可口可乐");
		vo.setBrand("可口可乐公司");
		vo.setPrice((float)3.5);
		vo.setShopId((long)1);
		vo.setStock(1000);
		vo.setUnitName("500ml");
		vo.setCoverImg("http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100");
		vo.setKindId((long)5);
		vo.setKindPath("/1/5/");
		
		List<ImageItem> imageItems = new ArrayList<ImageItem>();
		
		ImageItem imageItem = new ImageItem();
		imageItem.setFileName("图片1");
		imageItem.setFilePath("http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100");
		imageItem.setOrderNum(1);
		imageItems.add(imageItem);
		
		ImageItem imageItem2 = new ImageItem();
		imageItem2.setFileName("图片2");
		imageItem2.setFilePath("http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100");
		imageItem2.setOrderNum(2);
		imageItems.add(imageItem2);
		
		String photos = JSONObject.toJSONString(imageItems);
		vo.setPhotos(photos);
		String enc = MobileConstant.genEnc(userId+""+vo.getShopId()+vo.getKindId());
		ResultMsg<?> result = productService.addProduct(local,userId, vo, enc);
		System.out.println(JSONObject.toJSONString(result));
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Ignore
	@Test
	public void getProductByCatalogueId(){
		String local = "ZH_CN";
		String domain = "";
		Long lastProductId = (long)1;
		Integer pageSize = 10;
		double dlat = 116.395645; //经度
		double dlot = 39.929986;	 //纬度
		String catalogueId="/1";
		String enc = MobileConstant.genEnc(catalogueId+""+lastProductId);
		ResultMsg<?> result = productService.getProductByCatalogueId(local, domain, dlot, dlat, catalogueId, lastProductId, pageSize, enc);
		System.out.println(JSONObject.toJSONString(result));
	}


	@Ignore
	@Test
	public void getProdcutList(){
		final String indexName = "3dsq_shopping";
		final String indexType = "product";
		String local = "ZH_CN";
		String domain = "";
		String enc = MobileConstant.genEnc("");
		ResultMsg<?> result = productService.getProductList(local, domain, enc);
		List<ProductVo> list = (List<ProductVo>)result.getData();
		try {
			Client client = ESTools.getClient();
			//创建索引
			//ES.createIndex(indexName, indexType);
			//往索引添加数据
			// 创建索引库
			List<IndexRequest> requests = new ArrayList<IndexRequest>();
			for(ProductVo vo : list){
				String jsonData = ES.obj2JsonProductData(vo);
				IndexRequest request = client.prepareIndex(indexName, indexType).setSource(jsonData).request();
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
			System.out.println("成功创建"+bulkRequest.numberOfActions()+"条记录索引");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void getProduct(){
		String local = "ZH_CN";
		String domain = "";
		Long productId = (long)1;
		String enc = MobileConstant.genEnc(productId+"");
		ResultMsg<?> result = productService.getProduct(local, domain, productId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}


	public long getRandomShop(){
		int max=999;
		int min=1;
		Random random = new Random();

		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}


	public double getRandomPrice(){
		int max=10000;
		int min=1;
		Random random = new Random();

		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}

	public int getRandomStock(){
		int max=2000;
		int min=1;
		Random random = new Random();

		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
}
