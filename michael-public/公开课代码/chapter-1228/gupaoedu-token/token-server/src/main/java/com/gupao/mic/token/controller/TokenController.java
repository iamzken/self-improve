package com.gupao.mic.token.controller;

import com.gupaoedu.ratelimit.RateLimtClient;
import com.gupaoedu.ratelimit.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@RestController
public class TokenController {

    @Autowired
    RateLimtClient rateLimtClient;

    @GetMapping("/index")
    public String index() throws Exception {
       Token token= rateLimtClient.accquireToken("register");
       if(token.isSuccess()){
           return token.toString();
       }
       //熔断机制
       throw new Exception("error");
    }
}
