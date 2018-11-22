package com.d3sq.test.shopping;


import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.shopping.service.ICartService;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CartService {
	@Autowired ICartService cartService;
	
	@Ignore
	@Test
	public void addCart(){
		String local = "ZH_CN";
		Long productId = (long)4;
		Integer buyCount = -1;
		Float unitPrice = (float)33.33;
		Long userId = (long)3;
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = cartService.addCart(local, productId, buyCount,unitPrice, userId, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	
	
	@Ignore
	@Test
	public void getList(){
		String local = "ZH_CN";
		Long userId = (long)3;
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = cartService.getList(local, userId, enc);
		
		System.out.println(JSONObject.toJSONString(result));
	}
	//[{"products":[{"addPrice":9198,"buyCount":3,"check":1,"coupons":[],"coverImg":"http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100","createTime":1469783544191,"productId":469,"productName":"测试商品469"}],"shopId":469,"shopName":"菠萝蜜tea"}]
	@Ignore
	@Test
	public void mergeCart(){
		String local = "ZH_CN";
		Long userId = (long)4;
		String shops = "[{\"products\":[{\"addPrice\":9198,\"buyCount\":3,\"check\":1,\"coupons\":[],\"coverImg\":\"http://q.qlogo.cn/qqapp/1105405813/5442DE8F64DA2AAC75340C6F81360520/100\",\"createTime\":1469783544191,\"productId\":469,\"productName\":\"测试商品469\"}],\"shopId\":469,\"shopName\":\"菠萝蜜tea\"}]";
		String enc = MobileConstant.genEnc(userId+"");
		ResultMsg<?> result = cartService.mergeCart(local, userId, shops, enc);
		System.out.println(JSONObject.toJSONString(result));
	}
	

}
