package com.d3sq.test.search.service;

import javax.core.common.ResultMsg;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.search.service.ICreateIndexService;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateIndexServiceTest {

	Logger LOG = Logger.getLogger(this.getClass());
	
	@Autowired ICreateIndexService createIndexService;
	
	@Ignore
	@Test
	public void createHomeIndex(){
		System.out.println(JSONObject.toJSONString(createIndexService.createHomeIndex()));
	}
	
	@Ignore
	@Test
	public void createServiceIndex(){
		System.out.println(JSONObject.toJSONString(createIndexService.createServiceIndex()));
	}
	
	@Ignore
	@Test
	public void createResidentialIndex(){
		System.out.println(JSONObject.toJSONString(createIndexService.createResidentialIndex()));
	}
	
	
	@Test
	@Ignore
	public void testCreateProductIndex(){
		LOG.info("开始重建索引 ...");
		long start = System.currentTimeMillis();
		try{
			ResultMsg<?> result = createIndexService.createServiceIndex();
			if(result.getStatus() == SystemConstant.RESULT_STATUS_SUCCESS){
				LOG.info("重建索引成功!");
			}else{
				LOG.info("重建索引失败，下次重试！");
			}
		}catch(Exception e){
			LOG.info("创建索引时出现异常" + e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		LOG.info("重建索引结束，耗时" + (end - start) + "ms.");
	}
	
}
