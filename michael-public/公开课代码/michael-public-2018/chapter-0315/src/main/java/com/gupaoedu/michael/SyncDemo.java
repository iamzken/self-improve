package com.gupaoedu.michael;

import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SyncDemo implements Runnable{
    static int x=1;
    @Override
    public void run() {
        synchronized (SyncDemo.class) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "-" + x++);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SyncDemo sd=new SyncDemo();
        new Thread(sd,"t1").start();
        new Thread(sd,"t2").start();
    }
}
