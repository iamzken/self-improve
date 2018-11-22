package com.d3sq.test.data;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.express.dao.ExpressDao;
import com.d3sq.model.entity.Express;


@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExpressTest {

	@Autowired private ExpressDao expressDao;
	
	@Test
	@Ignore
	public void testAdd(){
		
		File f = new File("/WORKSPACES/YS_WORKSPACE/3dsq-full/src/test/java/com/d3sq/test/data/express/index.html");
		
		try {
			Document doc = Jsoup.parse(f, "utf-8");
			
			Elements items = doc.select("#tellist dd a");
			
			for (Element i : items) {
				
				Express e = new Express();
				e.setName(i.select("h4").text().trim().replaceAll("电话$", ""));
				e.setShortName(e.getName());
				e.setPinyin(PinyinUtil.converterToSpell(e.getName()).split(",")[0]);
				e.setTel(i.select("b").text().trim());
				e.setState(SystemConstant.ENABLE);
				e.setHotStore(0);
				e.setCreateTime(System.currentTimeMillis());
				
				Long id = expressDao.insertAndReturnId(e);
				e.setId(id);
				e.setParentId(id);
				e.setXpath("/" + id + "/");
				
				expressDao.update(e);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
