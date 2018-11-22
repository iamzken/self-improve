package com.gupaoedu.pub2018.chapter4;

import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CountDownLatchDemo {



    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(3);
        new Thread(()->{
            try {
                Thread.sleep(2000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();  //递减

        }).start();

        new Thread(()->{
            try {
                Thread.sleep(2000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();

        }).start();

        new Thread(()->{
            countDownLatch.countDown();
            try {
                Thread.sleep(2000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await(); //阻塞
        System.out.println("执行完毕 ");
    }
}
