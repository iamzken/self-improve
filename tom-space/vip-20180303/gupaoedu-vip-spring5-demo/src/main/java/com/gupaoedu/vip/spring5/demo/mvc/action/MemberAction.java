package com.gupaoedu.vip.spring5.demo.mvc.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MemberAction {


    //1、HTML页面（模板页面）
    //2、Json格式的字符串
    //3、任意字符串
    @RequestMapping("/get")
    @ResponseBody
    public ModelAndView get(HttpServletRequest request, HttpServletResponse response){
//        response.getWriter().println();
        return null;
    }

}
