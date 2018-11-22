package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadLocalDemo {

    //ThreadLocal

    // ThreadLocal提供了线程局部变量，跟普通变量不同的是，访问某个变量的每个线程都拥有自己的局部变量

    /**
     * 1. 每个线程都有自己的局部变量
     * 2. 每个线程的成员变量的值都独立于变量的初始化副本
     * 3. 状态与某一个线程关联
     *
     */

    private static Integer num=0;

    private static final ThreadLocal<Integer> threadNum=new ThreadLocal<Integer>(){
        protected Integer initialValue(){
            return 0;
        }
    };

    /**
     *
     * 提供的方法
     * 1. get()
     * 2. remove()
     * 3. initialValue()
     * 4. set()
     */

    public static void main(String[] args) {
        Thread[] threads=new Thread[5];
        for(int i=0;i<threads.length;i++){
            threads[i]=new Thread(()->{
//                num+=10; //?
                int num=threadNum.get();
                num+=10;
                threadNum.set(num);
                System.out.println(Thread.currentThread().getName()+":"+threadNum.get());
            },"Thread-"+i);
        }

        for(Thread thread:threads){
            thread.start();
        }
    }


}
