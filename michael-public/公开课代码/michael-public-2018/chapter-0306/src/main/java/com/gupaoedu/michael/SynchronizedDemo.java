package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SynchronizedDemo {

    /**
     *  synchronized(作用域)
     *  静态对象  全局锁
     *  SynchronizedDemo sd1=new SynchronizedDemo();
     *  SynchronizedDemo sd2=new SynchronizedDemo();
     *
     *  实例对象
     *  SynchronizedDemo sd2=new SynchronizedDemo();
     *  sd2.test();  sd2.demo();  sd2.demo2();
     *  代码块
     */
    static Object lock=new Object();


/*
    private synchronized void test() throws InterruptedException {
            lock.wait(); // InteljMonitorExcetpiokn
    }
*/


    public void demo2() throws InterruptedException {
        synchronized (this){
            lock.wait();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            System.out.println("t1");
        });
        t1.start();
        // Thread.sleep(5000);



        t1.join(); //阻塞,等待t1线程执行完毕
        //逻辑，
        Thread t2=new Thread(()->{
            System.out.println("t2");
        });
        t2.start();
    }
}
