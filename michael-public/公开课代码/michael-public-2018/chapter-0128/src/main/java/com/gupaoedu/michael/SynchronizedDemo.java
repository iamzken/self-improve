package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SynchronizedDemo{
    private static String A="a";
    private static String B="b";

    private void dealLock(){
        Thread t1=new Thread(()->{
           synchronized (A){
               synchronized (B){
                   System.out.println("1");
               }
           }
        });
        Thread t2=new Thread(()->{
            synchronized (B){
                synchronized (A){
                    System.out.println("1");
                }
            }
        });
        t1.start();
        t2.start();
    }


    public static void main(String[] args) {
        new SynchronizedDemo().dealLock();
    }
}
