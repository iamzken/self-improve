package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadLocalDemo2 {


    public static final ThreadLocal<Index> local=new ThreadLocal<Index>(){
        protected Index initialValue(){
            return new Index();
        }
    };
    public static void main(String[] args) {
        Thread[] threads=new Thread[5];
        for(int i=0;i<5;i++){
            threads[i]=new Thread(new Runnable() {
                public void run() {
                    Index num=local.get();
                    num.increase();
                    System.out.println(Thread.currentThread().getName()+":"+local.get().num);
                }
            },"Thread-"+i);
        }
        for(Thread thread:threads){
            thread.start();
        }
    }
    static class Index{
        int num;
        public void increase(){
            num++;
        }
    }
}

