package com.gupaoedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gupaoedu.service.HelloService;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;
    
    @RequestMapping("/hello")
    public String hello(@RequestParam String name){
        return helloService.helloService(name);
    }

}