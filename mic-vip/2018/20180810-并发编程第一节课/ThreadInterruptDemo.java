package com.gupaoedu.pub2018.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadInterruptDemo {

   /* public static void main(String[] args) throws InterruptedException {
        Thread thred=new Thread(()->{
            while(true){
                boolean in=Thread.currentThread().isInterrupted();
                if(in){
                    System.out.println("before:"+in);
                    Thread.interrupted();//设置复位
                    System.out.println("after:"+Thread.currentThread().isInterrupted());
                }
            }
        });
        thred.start();
        TimeUnit.SECONDS.sleep(1);
        thred.interrupt(); //终端
    }*/

   /* public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            while(true){
                try {
                    Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("before:"+thread.isInterrupted());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("after:"+thread.isInterrupted());
    }*/
//   volatile boolean stop=true;

}
