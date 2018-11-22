package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SynchronizedDemo {
    static Object lock=new Object();
    public synchronized void demo() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo sd1=new SynchronizedDemo();
//        SynchronizedDemo sd2=new SynchronizedDemo();

        new Thread(()->{sd1.demo();}).start();

        new Thread(()->{sd1.demo();}).start();

    }
}
