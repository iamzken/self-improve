package com.gupaoedu.michael;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class HelloService {

    //分布式限流    短信验证码怎么防刷；
    // 加图形验证码 ；
    // 对ip做限制 ；
    // 对请求时间间隔做限制（60 1s ;  120s）

    RateLimiter rateLimiter=RateLimiter.create(10); //qps = 10

    public void doRequest(){
        if(rateLimiter.tryAcquire()){
            System.out.println("请求成功");
        }else{
            System.out.println("请求过多，请稍后重试");
        }
    }
}
