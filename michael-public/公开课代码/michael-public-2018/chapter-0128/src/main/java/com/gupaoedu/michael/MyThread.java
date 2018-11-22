package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MyThread extends Thread{
    private Object lock;
    private String str;
    private int pos;

    private int printCount = 0;

    volatile private static int num = 1;

    public MyThread(Object lock, String str, int pos) {
        this.lock = lock;
        this.str = str;
        this.pos = pos;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (true) {
                    if (num % 3 == pos) {
                        System.out.println("ThreadName="
                                + Thread.currentThread().getName()
                                + " runCount=" + num + " " + str);
                        lock.notifyAll();
                        num++;
                        printCount++;
                        if (printCount == 5) {
                            break;
                        }
                    } else {
                        lock.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Object lock = new Object();

        MyThread a = new MyThread(lock, "A", 1);
        MyThread b = new MyThread(lock, "B", 2);
        MyThread c = new MyThread(lock, "C", 0);

        a.start();
        b.start();
        c.start();
    }
}