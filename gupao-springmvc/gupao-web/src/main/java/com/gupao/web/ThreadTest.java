package com.gupao.web;

/**
 * Created by James on 2017-10-16.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ThreadTest extends Thread {

    private static boolean flag = false;
    //第一种 保证多线程共享变量的可见性
//    volatile private static boolean flag = false;

    @Override
    public void run() {
        System.out.println("t1:" + Thread.currentThread().getId());
        while (!flag) {
            //第二种 加锁 在线程进入synchronized块之前，会把工作存内存中的所有内容映射到主内存上，然后把工作内存清空再从主存储器上拷贝最新的值。而在线程退出synchronized块时，同样会把工作内存中的值映射到主内存，但此时并不会清空工作内存。这样，工作内存中的值和主内存中的值是一致的，保证了数据的一致性！
//            synchronized (ThreadTest.class) {
//
//            }
            //第三种 ？？？是否利用字符串 常量池 拼接时检查常量池
//            System.out.println("flag:" + flag);
            System.out.println(flag);
        }
        System.out.println("quit!");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest t1 = new ThreadTest();
        t1.start();
        Thread.sleep(50);
        ThreadTest.flag = true;
        System.out.println("main:" + Thread.currentThread().getId());
    }
}
