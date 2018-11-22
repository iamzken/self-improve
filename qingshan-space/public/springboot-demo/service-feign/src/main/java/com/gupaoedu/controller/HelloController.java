package com.gupaoedu.controller;

import com.gupaoedu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gupaoedu.client.IServiceHello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController {

    @Autowired
    IServiceHello serviceHello;
    
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHi( HttpServletRequest request, HttpServletResponse response){
        User user  = new User();
        user.setName(request.getParameter("name"));
        return serviceHello.sayHello(user);
    }
}
