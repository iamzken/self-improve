package com.gupaoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序，会创建对象，开始监听
 */
@SpringBootApplication
public class RabbitConsumerApp {
	public static void main(String[] args) {
		SpringApplication.run(RabbitConsumerApp.class, args);
	}
}
