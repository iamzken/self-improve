package com.gupaoedu.michael;

/**
 * Hello world!
 *
 */
public class App {

    static Thread t1=new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread1");
        }
    });
    static Thread t2=new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread2");
        }
    });
    static Thread t3=new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread3");
        }
    });

    public static void main( String[] args ) throws InterruptedException {
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
    }
}
