package com.gupaoedu.michael;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException, IOException {

        HelloService helloService=new HelloService();
        CountDownLatch latch=new CountDownLatch(1);
        Random random=new Random(10);
        for(int i=0;i<20;i++){
            Thread t=new Thread(()->{
                try {
                    latch.await();
                    Thread.sleep(random.nextInt(1000));
                    helloService.doRequest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        latch.countDown();
        System.in.read();
    }

}
