package com.gupaoedu.essearch.test;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.essearch.model.SearchResult;
import com.gupaoedu.essearch.search.SearchNearbyService;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchNearbyServiceTest {
	
	@Autowired SearchNearbyService service;
	
	//这是我所在的坐标值，湖南长沙
	private double myLon = 112.938815;//东经
	private double myLat = 28.227878; //北纬
	private String myName = "Tom老师";//我的名字	
	
//	@Before
//	@Ignore
//	@Test
	public void initData(){
		int total = 100000;
		try{
			//建库建表
			service.recreateIndex();
			//随机产生10W条数据
			service.addDataToIndex(myLat, myLon, total);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("\n========= 数据初始化工作完毕,共随机产生" + total + "条数据 =========\n");
	}
	
	@Test
//	@Ignore
	public void searchNearby(){
		
		int size = 10;
		
		System.out.println("开始获取距离" + myName + "50米以内人");
		
		SearchResult result = service.search(myLon, myLat, 50, size, "女");
		
		System.out.println("共找到" + result.getTotal() + "个人,优先显示" + size + "人，查询耗时" + result.getUseTime() + "秒");
		for (Map<String,Object> taxi : result.getData()) {
			
			String nickName = taxi.get("nickName").toString();
			
			String location = taxi.get("location").toString();
			Object geo = taxi.get("geoDistance");
			
			
			System.out.println(nickName + "，" + 
							"微信号:" + taxi.get("wxNo") + 
							"，性别:" + taxi.get("sex") + 
							",距离" + myName + geo + "米" +
							"(坐标：" + location + ")");
		}
		
		System.out.println("以上" + size + "人显示在列表中......");
		System.out.println("各位老司机们使出你们的撩妹技巧！！！");
		
	}
	
	
}
