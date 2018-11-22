package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Service service=new Service();
        ThreadA a=new ThreadA(service);
        a.start();
        Thread.sleep(1000);
        ThreadB b=new ThreadB(service);
        b.start();
        System.out.println("start");
    }


}
