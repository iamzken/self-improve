package com.d3sq.test.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.core.dao.CityDao;
import com.d3sq.model.entity.City;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CityDataTest {

	@Autowired CityDao cityDao;
	
	@Test
	@Ignore
	public void testUpdate(){
		
		Map<Long,City> all = new HashMap<Long,City>();
		
		List<City> a = cityDao.selectAll();
		for (City city : a) {
			all.put(city.getId(), city);
		}
		
		List<City> citys = cityDao.selectByLevelType(3);
		
		for (City city : citys) {
			city.setXpath(all.get(city.getParentId()).getXpath() + city.getId() + "/");
			cityDao.update(city);
		}
		
	}

	
}
