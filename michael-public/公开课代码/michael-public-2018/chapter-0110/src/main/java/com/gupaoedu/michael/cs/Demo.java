package com.gupaoedu.michael.cs;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {
//    private static Integer num=0;
//  怎么去实现的
//  副本变量是怎么存储的

    public static final ThreadLocal<Integer> local=new ThreadLocal<Integer>(){
        protected Integer initialValue(){
            return 0;
        }
    };
    public static void main(String[] args) {
        Thread[] threads=new Thread[5];
        for(int i=0;i<5;i++){
            threads[i]=new Thread(()->{
                int num=local.get();
                local.set(2);
                num+=5;
                System.out.println(Thread.currentThread().getName()+":"+num);
            },"Thread-"+i);
        }

        for(Thread thread:threads){
            thread.start();
        }
    }

}
