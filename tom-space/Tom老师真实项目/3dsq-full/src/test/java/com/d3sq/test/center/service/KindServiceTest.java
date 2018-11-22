package com.d3sq.test.center.service;


import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.shopping.service.IKindService;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class KindServiceTest {
	@Autowired IKindService kindService;

	@Test
	@Ignore
	public void getKindForProduct(){
		String local = "ZH_CN";
		String domain = "";
		String enc = MobileConstant.genEnc("");
		System.out.println(enc);
		ResultMsg<?> result = kindService.getForProduct(local, domain, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	
	@Test
	@Ignore
	public void getForService(){
		String local = "ZH_CN";
		String domain = "";
		String enc = MobileConstant.genEnc("");
		System.out.println(enc);
		ResultMsg<?> result = kindService.getForService(local, domain, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	

	@Test
	@Ignore
	public void getByParentId(){
		String local = "ZH_CN";
		String domain = "";
		Long parentId = (long)1;
		String enc = MobileConstant.genEnc(parentId+"");
		ResultMsg<?> result = kindService.getByParentId(local, domain, parentId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
}
