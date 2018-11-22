package com.d3sq.test.explorer;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.common.config.CustomConfig;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExplorerTest {

	@Test
	@Ignore
	public void testMode(){
		System.out.println("cert".matches(CustomConfig.getString("explorer.upload.mode")));
	}
	
}
