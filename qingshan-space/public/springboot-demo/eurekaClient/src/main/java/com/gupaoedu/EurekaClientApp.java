package com.gupaoedu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 服务提供方，同时也是一个Eureka Client
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaClientApp {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApp.class, args);
	}

}
