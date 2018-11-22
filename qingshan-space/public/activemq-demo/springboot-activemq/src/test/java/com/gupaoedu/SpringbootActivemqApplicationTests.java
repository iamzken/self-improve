package com.gupaoedu;

import com.gupaoedu.producer.MyProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootActivemqApplicationTests {

	@Autowired
	private MyProducer producer;

	@Test
	public void contextLoads() {
		producer.send();
	}

}
