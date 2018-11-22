package com.gupao.course.demo;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PayService {
    RateLimiter rateLimiter = RateLimiter.create(9); //TPS为10
    public void doRequest(String threadName) {
        if (rateLimiter.tryAcquire()) {
            System.out.println(threadName + ":支付成功");
        } else {
            System.out.println(threadName + ":当前支付人数过多，请稍后再试");
        }
    }
}
