package com.d3sq.test.search.service;

import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.search.service.INearbyService;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NearbyServiceTest {
	@Autowired INearbyService nearbyService;
	
	
	/**
	 * 获取附近商品信息
	 */
	@Ignore
	@Test
	public void getProduct(){
		String local = "ZH_CN";
		String domain = "";
		String  lon = "105.887"; //经度
		String lat = "29.3534";	 //纬度
		Integer type =  2;
		String name = "清洁";
		Long shopId = null;
		Long lastCreateTime = null;
		Integer pageSize = 12;
		String enc = MobileConstant.genEnc(lon+""+lat);
		System.out.println(enc);
		ResultMsg<?> result = nearbyService.getProduct(local, domain, lon, lat, type, name, shopId, lastCreateTime, pageSize, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
}
