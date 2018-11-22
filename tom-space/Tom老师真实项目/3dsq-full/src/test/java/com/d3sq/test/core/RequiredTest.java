package com.d3sq.test.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.core.dao.RequiredDao;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RequiredTest {

	@Autowired RequiredDao requiredDao;
	
	@Test
	@Ignore
	public void testSelectMemberById(){
		requiredDao.selectMemberById(1L);
	}
	
	@Test
	@Ignore
	public void testSelectMemberByIds(){
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		requiredDao.selectMemberByIds(ids);
	}
}
