package com.gupaoedu.michael;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TokenDemo {

    //分布式限流




    RateLimiter rateLimiter=RateLimiter.create(10);

    public void doSomething(){
        if(rateLimiter.tryAcquire()){ //尝试获得令牌
            System.out.println("正常处理");
        }else{
            System.out.println("处理失败");
        }
    }


    public static void main(String[] args) throws IOException {

        CountDownLatch latch=new CountDownLatch(1);
        Random random=new Random(10);
        TokenDemo tokenDemo=new TokenDemo();
        for(int i=0;i<20;i++){
            new Thread(()->{
                try {
                    latch.await();
                    Thread.sleep(random.nextInt(1000));
                    tokenDemo.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        latch.countDown();
        System.in.read();
    }
}
