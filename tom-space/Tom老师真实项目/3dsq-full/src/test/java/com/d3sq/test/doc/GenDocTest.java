package com.d3sq.test.doc;

import java.util.ArrayList;
import java.util.List;

//import javax.core.maven.plugin.parser.HTMLParser;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class GenDocTest {
	
	@Autowired ApplicationContext app;
	
	@Test
	@Ignore
	public void test(){
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String name : app.getBeanDefinitionNames()) {
			try{
				Object bean = app.getBean(name);
				if(null != bean){
					classes.add(bean.getClass());
				}
			}catch(Exception e){
				
			}
		}
//		HTMLParser.generate("/Users/tom/Desktop/open-api.html",classes.toArray(new Class<?>[classes.size()]), "3dsq.me");
		
		
//		String json = "{\"data\":{\"site\":{\"domain\":\"3dsq.me\",\"config\":{}},\"shop\":{\"address\":\"湖南省长沙市岳麓区潇湘中路349号\",\"certFlag\":0,\"createTime\":1471677363046,\"creatorId\":2,\"id\":590,\"lat\":28.3706,\"lon\":113.23,\"name\":\"步步高超市\",\"pinyin\":\"bubugaochaoshi\",\"state\":1},\"token\":\"4714b88aba831191bc1de60c85f296fa9c403dd34c3b12b2e944ff9dd96f8f1ed7d201edcfd95500548e33e859bb0edfa070878f0c677e4464e792b8d2a05911\",\"roles\":[],\"relation\":{},\"user\":{\"sex\":1,\"certFlag\":2,\"tel\":\"15911125523\",\"state\":1,\"id\":2,\"nickName\":\"玉手藝人\",\"loginName\":\"15911125523\",\"mtype\":1,\"photo\":\"http://resource.3dsq.me/uploads/anonymous/15911125523/_1471851035273.png\",\"currLoginTime\":1472466221579,\"auditFlag\":1,\"currLoginIp\":\"220.168.106.214\"},\"options\":{}},\"msg\":\"登录成功\",\"status\":1}";
//		System.out.println(JSON.toJSONString(JSON.parse(json),true).replaceAll("\n","<br/>").replaceAll("\t","&nbsp;&nbsp;&nbsp;&nbsp;"));
	}
	
}
