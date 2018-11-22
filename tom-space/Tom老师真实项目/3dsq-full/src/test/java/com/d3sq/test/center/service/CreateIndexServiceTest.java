package com.d3sq.test.center.service;

import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.search.service.ICreateIndexService;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateIndexServiceTest {
	@Autowired ICreateIndexService createIndexService;
	
	@Test
	@Ignore
	public void createHomeIndex(){
		ResultMsg<Object> result = createIndexService.createHomeIndex();
		System.out.println(JSONObject.toJSONString(result));
	}
	
	@Test
	@Ignore
	public void createCommodityIndex(){
		ResultMsg<Object> result = createIndexService.createCommodityIndex();
		System.out.println(JSONObject.toJSONString(result));
	}

	@Test
	@Ignore
	public void createShopIndex(){
		ResultMsg<Object> result = createIndexService.createShopIndex();
		System.out.println(JSONObject.toJSONString(result));
	}
	
	@Test
	@Ignore
	public void createResidentialIndex(){
		ResultMsg<Object> result = createIndexService.createResidentialIndex();
		System.out.println(JSONObject.toJSONString(result));
	}

}
