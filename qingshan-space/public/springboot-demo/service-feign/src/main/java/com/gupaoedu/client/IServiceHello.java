package com.gupaoedu.client;

import com.gupaoedu.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// 调用 service-gupao 的/helloBody接口，传递一个对象
@FeignClient(value = "service-gupao",fallback = ServiceHelloFallback.class)
public interface IServiceHello {
    @RequestMapping(value = "/helloBody",method = RequestMethod.GET)
    String sayHello(@RequestBody User user);
}
