package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadLocalDemo {

    private static Integer num=0;

    public static void main(String[] args) {
        Thread[] threads=new Thread[5];
        for(int i=0;i<5;i++){
            threads[i]=new Thread(new Runnable() {
                public void run() {
                    num+=5;
                    System.out.println(Thread.currentThread().getName()+":"+num);
                }
            },"Thread-"+i);
        }
        for(Thread thread:threads){
            thread.start();
        }
    }
}
