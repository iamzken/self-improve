package com.gupaoedu.michael.lession1;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadNotify extends Thread{

    private Object lock;

    public ThreadNotify(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("开始执行ThreadNotify");
            lock.notify();
            System.out.println("结束执行ThreadNotify");
        }
    }

    public static void main(String[] args) {
        Object object=new Object();
        ThreadWait threadWait=new ThreadWait(object);
        ThreadNotify threadNotify=new ThreadNotify(object);
        threadWait.start();
        threadNotify.start();
    }
}
