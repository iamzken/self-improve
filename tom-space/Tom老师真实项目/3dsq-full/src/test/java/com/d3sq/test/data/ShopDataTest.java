package com.d3sq.test.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.d3sq.core.dao.CityDao;
import com.d3sq.model.entity.City;
import com.d3sq.model.entity.Shop;
import com.d3sq.shopping.dao.ShopDao;


@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShopDataTest {

	@Autowired CityDao cityDao;
	@Autowired ShopDao shopDao;
	
	
	@Test
	@Ignore
	public void testAddForChongqing(){
		
		Map<String,City> all = new HashMap<String,City>();
		
		List<City> a = cityDao.selectAll();
		for (City city : a) {
			all.put(city.getShortName(), city);
		}
		
	
		for (int i = 0; i < 10; i++) {
		
			File f = new File("/WORKSPACES/YS_WORKSPACE/3dsq-full/src/test/java/com/d3sq/test/data/chongqing/shop/" + (i + 1) + ".html");
			
			List<Shop> result = new ArrayList<Shop>();
			
			try {
				Document doc = Jsoup.parse(f, "utf-8");
				
				Elements tiles = doc.select(".tile");
				
				for (Element t : tiles) {
					
					List<Node> child = t.childNodes();
					
					if(child.get(3).attr("cv").matches("name")){
						continue;
					}
					
					Shop r = new Shop();
					
					r.setName(child.get(3).attr("cv"));
					r.setCoverImg(child.get(16).attr("cv"));
					r.setTel(child.get(10).attr("cv").trim());
					City c = all.get(child.get(2).attr("cv"));
					r.setCityPath(c == null ? null : c.getXpath());
					r.setPinyin(PinyinUtil.converterToSpell(r.getName()).split(",")[0]);
					r.setCreateTime(System.currentTimeMillis());
					r.setLat(Float.valueOf(child.get(8).attr("cv")));
					r.setLon(Float.valueOf(child.get(9).attr("cv")));
					r.setAddress(child.get(4).attr("cv"));
					r.setState(SystemConstant.ENABLE);
					r.setIntro(child.get(18).attr("cv"));
					result.add(r);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			shopDao.saveAll(result);
		}
		
	}
	
}
