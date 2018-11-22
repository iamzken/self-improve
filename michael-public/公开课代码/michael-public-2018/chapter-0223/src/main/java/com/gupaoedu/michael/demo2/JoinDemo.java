package com.gupaoedu.michael.demo2;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JoinDemo implements Serializable{
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            System.out.println("t1");
        });
        Thread t2=new Thread(()->{
            System.out.println("t2");
        });
        Thread t3=new Thread(()->{
            System.out.println("t3");
        });
        t1.start();
        t1.join();  //wait /notify

        t2.start();
        t2.join();

        t3.start();
        t3.join();
    }
}
