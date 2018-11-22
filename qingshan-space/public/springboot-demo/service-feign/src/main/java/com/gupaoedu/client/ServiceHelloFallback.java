package com.gupaoedu.client;

import com.gupaoedu.domain.User;
import org.springframework.stereotype.Component;

/**
 * Feign接口的回退类和回退方法
 */
@Component
public class ServiceHelloFallback implements IServiceHello {
	@Override
	public String sayHello(User user) {
		return "Sorry, "+user.getName()+", error happens in Feign ! ";
	}
}
