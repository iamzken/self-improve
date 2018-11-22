package com.gupaoedu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gupaoedu.demo.mapper")

public class MycatApp {

	public static void main(String[] args) {
		SpringApplication.run(MycatApp.class, args);
	}
}
