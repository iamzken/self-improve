package com.d3sq.test.shopping;


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
import com.d3sq.shopping.service.ICartService;
import com.d3sq.shopping.service.IShoppingService;
import com.d3sq.shopping.vo.MallOrderVo;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShoppingServiceTest {
	@Autowired IShoppingService shoppingService;
	
	/**
	 * 购买商品下单
	 */
	@Ignore
	@Test
	public void addOrderForProduct(){
		String local = "ZH_CN";
		String domain = "";
		Long userId = (long)2;
		MallOrderVo mallOrderVo = new MallOrderVo();
		mallOrderVo.setAddrDetail("湖南省长沙市岳麓区");
		mallOrderVo.setCustomName("Tom");
		mallOrderVo.setCustomTel("13800138000");
		mallOrderVo.setOtype(FieldConstant.PAY_OTYPE_SHOPPING);
		
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = shoppingService.addOrder(local, domain, userId, mallOrderVo, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	
	
	/**
	 * 预约服务
	 */
	@Ignore
	@Test
	public void addOrderForService(){
		String local = "ZH_CN";
		String domain = "";
		Long userId = (long)2;
		MallOrderVo mallOrderVo = new MallOrderVo();
		mallOrderVo.setAddrDetail("湖南省长沙市岳麓区");
		mallOrderVo.setCustomName("Tom");
		mallOrderVo.setCustomTel("13800138000");
		//mallOrderVo.setOtype(FieldConstant.PAY_OTYPE_SERVICE);
		
		mallOrderVo.setServiceId((long)9);
		mallOrderVo.setFeeStandIndex(1);
		mallOrderVo.setTimesDualIndex(0);
		
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = shoppingService.addOrder(local, domain, userId, mallOrderVo, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	
	
	@Ignore
	@Test
	public void cancelOrder(){
		String local = "ZH_CN";
		Long userId = (long)10;
		String orderId = "P22016080500000038";
		String enc = MobileConstant.genEnc(userId+orderId);
		ResultMsg<?> result = shoppingService.cancelOrder(local, userId, orderId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
}
