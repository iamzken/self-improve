package com.gupaoedu.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 咕泡学院
 *   做技术人的指路明灯，做职场生涯的精神导师
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        CountDownLatch countDownLatch=new CountDownLatch(10);
        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    DistributedLock distributedLock=new DistributedLock();
                    distributedLock.lock(); //获得锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"Thread-"+i).start();
            countDownLatch.countDown();
        }
        System.in.read();
    }
}
