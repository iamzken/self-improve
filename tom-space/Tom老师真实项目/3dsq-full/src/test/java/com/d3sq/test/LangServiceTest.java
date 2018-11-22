package com.d3sq.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.core.service.ILangService;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class LangServiceTest {

	
	@Autowired ILangService langService;
	
	@Test
	@Ignore
	public void testGetOrder(){
		String no = langService.getOrderNum(false, 1);
		System.out.println(no);
	}
	
}
