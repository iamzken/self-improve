package com.d3sq.test.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.core.dao.ResidentialDao;
import com.d3sq.model.entity.Residential;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ResidentialDataTest {
	
	@Autowired ResidentialDao residentialDao;
	
	
	/**
	 * 录入广州市居民小区信息
	 */
	@Test
	@Ignore
	public void testAddForGuangzhou(){
		File f = new File("/WORKSPACES/YS_WORKSPACE/3dsq-full/src/test/java/com/d3sq/test/data/guangzhou/resi/1.html");
		
		try {
			Document doc = Jsoup.parse(f, "utf-8");
			
			Elements tiles = doc.select(".tile");
			
			List<Residential> result = new ArrayList<Residential>();
			
			for (Element t : tiles) {
				
				List<Node> child = t.childNodes();
				
				Residential r = new Residential();
				
				r.setName(child.get(0).attr("cv"));
				r.setShortName(child.get(0).attr("cv"));
				r.setCityPath("/440000/440100/");
				r.setPinyin(PinyinUtil.converterToSpell(r.getName()).split(",")[0]);
				r.setCreateTime(System.currentTimeMillis());
				r.setLon(Float.valueOf(child.get(25).attr("cv")));
				r.setLat(Float.valueOf(child.get(26).attr("cv")));
				r.setAddress(child.get(2).attr("cv"));
				r.setState(SystemConstant.ENABLE);
				
				result.add(r);
			
			}
			
			residentialDao.saveAll(result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * 录入广州市居民小区信息
	 */
	@Test
	@Ignore
	public void testAddForBeijing(){
		
		for (int i = 0; i < 16; i++) {
		
			File f = new File("/WORKSPACES/YS_WORKSPACE/3dsq-full/src/test/java/com/d3sq/test/data/beijing/resi/" + (i + 1) + ".html");
			
			List<Residential> result = new ArrayList<Residential>();
			
			try {
				Document doc = Jsoup.parse(f, "utf-8");
				
				Elements tiles = doc.select(".tile");
				
				for (Element t : tiles) {
					
					List<Node> child = t.childNodes();
					
					Residential r = new Residential();
					
					r.setName(child.get(0).attr("cv"));
					r.setShortName(child.get(0).attr("cv"));
					r.setCityPath("/110000/110100/");
					r.setPinyin(PinyinUtil.converterToSpell(r.getName()).split(",")[0]);
					r.setCreateTime(System.currentTimeMillis());
					r.setLon(Float.valueOf(child.get(25).attr("cv")));
					r.setLat(Float.valueOf(child.get(26).attr("cv")));
					r.setAddress(child.get(2).attr("cv"));
					r.setState(SystemConstant.ENABLE);
					result.add(r);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			residentialDao.saveAll(result);
		}
	}
	
	
}
