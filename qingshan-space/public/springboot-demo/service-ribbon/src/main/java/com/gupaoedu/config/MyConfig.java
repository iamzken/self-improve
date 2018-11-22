package com.gupaoedu.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: qingshan
 * @Date: 2018/9/27 11:15
 * @Description: 咕泡学院，只为更好的你
 */
@Configuration
public class MyConfig {
    /**
     * 在客户端启用负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
