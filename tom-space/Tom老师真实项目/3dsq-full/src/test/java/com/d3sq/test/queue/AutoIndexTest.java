package com.d3sq.test.queue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.core.plugin.queue.QueuePlugin;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Product;
import com.d3sq.shopping.dao.ProductDao;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AutoIndexTest {
	
	@Autowired QueuePlugin plugin1;
	@Autowired QueuePlugin plugin2;
	@Autowired QueuePlugin plugin3;
	@Autowired QueuePlugin plugin4;
	@Autowired QueuePlugin plugin5;

	@Autowired ProductDao productDao;
	
	
	@Test
	@Ignore
	public void testCreateIndex(){
		
		for (int i = 0 ; i < 200; i ++) {
			plugin1.push(new QueueItem(plugin1,"all", QueueTarget.OPT_ADD, 0, new Object()));
		}
		
		for (int i = 0 ; i < 200; i ++) {
			plugin2.push(new QueueItem(plugin2,"all", QueueTarget.OPT_ADD, 0, new Object()));
		}
		for (int i = 0 ; i < 200; i ++) {
			plugin3.push(new QueueItem(plugin3,"all", QueueTarget.OPT_ADD, 0, new Object()));
		}
		for (int i = 0 ; i < 200; i ++) {
			plugin4.push(new QueueItem(plugin4,"all", QueueTarget.OPT_ADD, 0, new Object()));
		}
		for (int i = 0 ; i < 200; i ++) {
			plugin5.push(new QueueItem(plugin5,"all", QueueTarget.OPT_ADD, 0, new Object()));
		}
		
		
		
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		plugin5.restart();
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	@Ignore
	public void testQueue(){
		Product p = new Product();
		p.setId(9999L);
		p.setName("IPhone7");
		//productDao.selectByPtype(2, p);
		
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
