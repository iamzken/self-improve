package com.gupaoedu.michael.lock;

import com.gupaoedu.michael.demo.DistributedLock1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TestDemo {

    public static void main(String[] args) throws IOException {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    DistributedLock1 distributedLock1=new DistributedLock1();
                    distributedLock1.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"thread-"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();
    }
}
