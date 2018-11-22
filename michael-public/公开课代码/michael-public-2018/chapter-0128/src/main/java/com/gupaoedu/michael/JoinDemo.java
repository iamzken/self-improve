package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JoinDemo implements Runnable{

    public static int count=1;

    static Object lock=new Object();

    @Override
    public synchronized void run() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new JoinDemo(),"t1").start();
        new Thread(new JoinDemo(),"t2").start();
    }
}
