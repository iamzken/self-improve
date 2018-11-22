package com.d3sq.test.center.service;

import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.center.service.ICenterService;
import com.d3sq.common.constants.MobileConstant;


@ContextConfiguration(locations = {"classpath*:application-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CenterServiceTest {
	@Autowired ICenterService centerService;
	
	
	@Test
	@Ignore
	public void getOrders(){
		String local = "ZH_CN";
		Long userId = (long)10;
		Integer process = null;
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = centerService.getOrders(local, userId, process, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	
	@Test
	@Ignore
	public void getOrderDetail(){
		String local = "ZH_CN";
		String orderId = "P22016080400000029";
		String enc = MobileConstant.genEnc(orderId);
		ResultMsg<?> result = centerService.getOrderDetail(local, "", orderId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
}
