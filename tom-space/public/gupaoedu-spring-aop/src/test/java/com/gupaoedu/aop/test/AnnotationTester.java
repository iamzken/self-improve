package com.gupaoedu.aop.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.aop.aspect.AnnotaionAspect;
import com.gupaoedu.aop.service.MemberService;
import com.gupaoedu.model.Member;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnnotationTester {
	@Autowired MemberService annotationService;
	@Autowired ApplicationContext app;
	
	
	@Test
//	@Ignore
	public void test(){
		System.out.println("=====这是一条华丽的分割线======");
		
		AnnotaionAspect aspect = app.getBean(AnnotaionAspect.class);
		System.out.println(aspect);
		annotationService.save(new Member());
		
		System.out.println("=====这是一条华丽的分割线======");
		try {
			annotationService.delete(1L);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
}
