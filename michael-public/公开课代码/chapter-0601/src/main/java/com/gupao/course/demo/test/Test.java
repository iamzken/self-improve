package com.gupao.course.demo.test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Test {

    public static void main(String[] args) {
//        testNoRateLimiter();
        testWithRateLimiter();
    }

    private static void testNoRateLimiter(){
        Long start=System.currentTimeMillis();
        for(int i=0;i<10;i++){
            System.out.println("执行："+i);
        }
        Long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
    private static void testWithRateLimiter(){
        Long start=System.currentTimeMillis();
        RateLimiter limiter=RateLimiter.create(10.0);//每秒不超过10个任务
        for(int i=0;i<10;i++){
            limiter.acquire();
            System.out.println("执行："+i);
        }
        Long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}
