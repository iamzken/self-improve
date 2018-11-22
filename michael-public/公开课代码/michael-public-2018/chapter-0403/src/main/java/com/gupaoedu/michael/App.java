package com.gupaoedu.michael;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class App {

    RateLimiter rateLimiter=RateLimiter.create(10);

    public void doSomething(){
        if(rateLimiter.tryAcquire()){
            System.out.println(Thread.currentThread().getName()+"-获得令牌成功");
        }else{
            System.out.println("----------------请求失败--------------");
        }
    }

    public static void main(String[] args) {
        App app=new App();
        CountDownLatch countDownLatch=new CountDownLatch(1);
        Random random=new Random(10);
        //lambda () -> {}
        for(int i=0;i<20;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                app.doSomething();
            },"t"+i).start();
        }
        countDownLatch.countDown();
    }
}
