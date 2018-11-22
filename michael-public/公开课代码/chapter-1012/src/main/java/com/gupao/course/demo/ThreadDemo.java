package com.gupao.course.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadDemo {

    //volatile int i;
    AtomicInteger i=new AtomicInteger(0);

    public void add(){
       i.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo demo=new ThreadDemo();
        CountDownLatch latch=new CountDownLatch(1000);
        for(int i=0;i<1000;i++){
            new Thread(()->{
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.add();
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(demo.i);
    }
}
