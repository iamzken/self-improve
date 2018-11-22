package com.gupaoedu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gupaoedu.demo.mapper")

public class QuartzApp {

	public static void main(String[] args) {
		SpringApplication.run(QuartzApp.class, args);
	}
}
