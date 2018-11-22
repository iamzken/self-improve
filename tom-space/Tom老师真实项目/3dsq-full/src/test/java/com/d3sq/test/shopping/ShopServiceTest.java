package com.d3sq.test.shopping;

import java.text.DecimalFormat;
import java.util.Random;

import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.shopping.service.IShopService;
import com.d3sq.shopping.vo.AddShopVo;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShopServiceTest {
	@Autowired IShopService shopService;
	
	@Test
	@Ignore
	public void addShop(){
		String local = "ZH_CN";
		String domain = "";
		
		double lat = 39.929986;
		double lon = 116.395645;
		AddShopVo shopVo = new AddShopVo();
		long creatorId = 4;
		double max = 0.00001;
		double min = 0.000001;
		Random random = new Random();
		double s = random.nextDouble() % (max - min + 1) + max;
		DecimalFormat df = new DecimalFormat("######0.000000");
		
		String shopName = "家具";
		String address = "湖南省长沙市岳麓区";
		String lons = df.format(s + lon);
		String lats = df.format(s + lat);
		String dlon = "112.960037";
		String dlat = "28.163630";

		shopVo.setShopName(shopName);
		shopVo.setCreatorId(creatorId);
		shopVo.setLat(dlat);
		shopVo.setLon(dlon);
		shopVo.setAddress(address);
		
		String enc = MobileConstant.genEnc(creatorId+""+dlon+dlat);
		System.out.println(enc);
		ResultMsg<?> result = shopService.addShop(local, domain, shopVo, enc);
		System.out.println(JSONObject.toJSONString(result));
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
