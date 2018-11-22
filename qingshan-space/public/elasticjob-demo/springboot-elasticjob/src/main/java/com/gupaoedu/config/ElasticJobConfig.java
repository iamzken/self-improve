package com.gupaoedu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: qingshan
 * @Date: 2018/11/13 14:03
 * @Description: 咕泡学院，只为更好的你
 */
@Configuration
@PropertySource("classpath:application.properties")
@ImportResource("classpath:spring-elasticjob.xml")
public class ElasticJobConfig {
}