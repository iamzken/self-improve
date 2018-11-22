package com.gupaoedu.controller;

import com.gupaoedu.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qingshan
 * @Date: 2018/09/14 11:27
 * @Description: 咕泡学院，只为更好的你
 *
 * 用于供其他服务调用
 */
@RestController
public class Client {
	@Value("${server.port}")
	String port;

	@RequestMapping("/hello")
	public String home(@RequestParam String name) {
		return "Hello, " + name + " , call successfully, service on port : " + port;
	}

	@RequestMapping("/helloBody")
	public String helloBody(@RequestBody User user) {
		return "HelloBody, " + user.getName() + " , call successfully, service on port : " + port;
	}

}
