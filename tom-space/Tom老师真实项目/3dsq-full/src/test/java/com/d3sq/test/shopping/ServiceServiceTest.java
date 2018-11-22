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
import com.d3sq.model.helper.RuleItem;
import com.d3sq.model.helper.TimeDualItem;
import com.d3sq.shopping.service.IServiceService;
import com.d3sq.shopping.vo.AddServiceVo;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceServiceTest {
	@Autowired IServiceService serviceService ;

	@Ignore
	@Test
	public void addService(){
		String local = "ZH_CN";

		Long userId = (long)1;

		AddServiceVo vo = new AddServiceVo();
		vo.setServiceName("换锁开门");
		vo.setShopId((long)1);
		vo.setCoverImg("http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100");

		//服务分类
		vo.setKindId((long)97);
		vo.setKindPath("/82/97/");
		vo.setContent("服务内容");

		//服务详图
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
		vo.setIntro(photos);

		//收费标准
		List<RuleItem> ruleItems = new ArrayList<RuleItem>();
		RuleItem ruleItem = new RuleItem();
		ruleItem.setTitle("规格1");
		ruleItem.setPrice((float)50);
		ruleItems.add(ruleItem);

		RuleItem ruleItem2 = new RuleItem();
		ruleItem2.setTitle("规格2");
		ruleItem2.setPrice((float)100);
		ruleItems.add(ruleItem2);
		String feeStand = JSONObject.toJSONString(ruleItems);
		vo.setFeeStand(feeStand);

		//服务时段
		List<TimeDualItem> timeDualItems = new ArrayList<TimeDualItem>();

		TimeDualItem timeDualItem = new TimeDualItem();
		timeDualItem.setStartTime("09:00");
		timeDualItem.setEndTime("11:30");
		timeDualItem.setStock(5);
		timeDualItems.add(timeDualItem);
		TimeDualItem timeDualItem2 = new TimeDualItem();
		timeDualItem2.setStartTime("14:00");
		timeDualItem2.setEndTime("18:30");
		timeDualItem2.setStock(8);
		timeDualItems.add(timeDualItem2);

		String timesDual = JSONObject.toJSONString(timeDualItems);
		vo.setTimesDual(timesDual);

		String enc = MobileConstant.genEnc(userId+""+vo.getShopId()+vo.getKindId());
		ResultMsg<?> result = serviceService.addService(local, userId, vo, enc);
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
	public void getServiceDetail(){
		String local = "ZH_CN";
		Long serviceId = (long)2;
		String enc = MobileConstant.genEnc(serviceId+"");
		ResultMsg<?> result = serviceService.getService(local, serviceId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	

}
