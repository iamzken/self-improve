package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadDemo extends Thread{


    @Override
    public void run() {
        System.out.println("主动创建一个线程"+Thread.currentThread().getName()+":"+
        Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getId());
        ThreadDemo td1=new ThreadDemo();
        td1.setName("Thread A");
        td1.start();
        ThreadDemo td2=new ThreadDemo();
        td2.run();
    }
}
