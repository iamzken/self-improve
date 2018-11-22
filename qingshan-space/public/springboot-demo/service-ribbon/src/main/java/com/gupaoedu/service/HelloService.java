package com.gupaoedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * 通过RestTemplate调用远程服务接口
	 * @param name
	 * @return
	 */

	/*
		@HystrixCommand(fallbackMethod = "helloError",
					threadPoolKey = "printNameThreadPool",
					threadPoolProperties = {
						@HystrixProperty(name="coreSize",value="30"),
						@HystrixProperty(name="maxQueueSize",value="10")
					})
	*/
	@HystrixCommand(fallbackMethod = "helloError")
	public String helloService(String name) {
		//return restTemplate.getForObject("http://127.0.0.1:8922/hello?name=" + name, String.class);
		return restTemplate.getForObject("http://service-gupao/hello?name=" + name, String.class);
	}

	public String helloError(String name) {
        return "Sorry, "+name+", error happens in Ribbon! ";
    }
}