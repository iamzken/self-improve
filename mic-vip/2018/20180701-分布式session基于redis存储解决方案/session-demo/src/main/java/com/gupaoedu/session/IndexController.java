package com.gupaoedu.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */

@RestController
public class IndexController {


    @GetMapping("/index")
    public String index(HttpServletRequest request){
        request.getSession().setAttribute("Mic","value");
        return "success";
    }


    @GetMapping("/getValue")
    public String getValue(HttpServletRequest request){
        String str=(String)request.getSession().getAttribute("Mic");
        return str;
    }

}
