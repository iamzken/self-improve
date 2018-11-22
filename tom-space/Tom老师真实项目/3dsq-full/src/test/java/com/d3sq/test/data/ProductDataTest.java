package com.d3sq.test.data;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.model.entity.Product;
//import com.d3sq.model.entity.Shop;
import com.d3sq.shopping.dao.ProductDao;
import com.d3sq.shopping.dao.ShopDao;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductDataTest {

	@Autowired ProductDao productDao;
	
	@Autowired ShopDao shopDao;
	
	/**
	 * 随机添加商品
	 */
	@Test
	@Ignore
	public void testAddForRandom(){
		List<Product> p = productDao.selectAll();
		
//		List<Shop> s = shopDao.selectAll();
		
//		int i = 0;
		for (Product pro : p) {
			
//			if(i > s.size() - 1){
//				i = s.size() -1;
//			}
//			pro.setShopId(s.get(i).getId());
//			i ++;
			
			pro.setPinyin(PinyinUtil.converterToSpell(pro.getName()).split(",")[0]);
			
			
			productDao.update(pro);
		}
		
	}
	
}
