package com.d3sq.test.queue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Product;
import com.d3sq.model.entity.Service;
import com.d3sq.model.entity.Shop;
import com.d3sq.search.plugin.ProductIndexPlugin;
import com.d3sq.search.plugin.ServiceIndexPlugin;
import com.d3sq.search.plugin.ShopIndexPlugin;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class QueueTest {

	@Autowired ProductIndexPlugin product;
	@Autowired ServiceIndexPlugin service;
	@Autowired ShopIndexPlugin shop;
	
	@Test
	@Ignore
	public void pushToQueue(){
		QueueItem p = new QueueItem(product,"PRODUCT", QueueTarget.OPT_ADD, "", new Product());
		product.push(p);
		product.push(p);
		product.push(p);
		product.push(p);
		
		QueueItem ser = new QueueItem(service,"SERVICE", QueueTarget.OPT_ADD, "", new Service());
		service.push(ser);
		
		QueueItem sh = new QueueItem(shop,"SHOP", QueueTarget.OPT_ADD, "", new Shop());
		shop.push(sh);
		
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
