package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SynchornizedDemo {
    Object lock=new Object();

    //三种作用域（作用范围）

    /**
     * 静态同步  全局锁
     * 普通同步 ， 锁定当前对象的实例 同一个对象
     * 同步方法块
     *
     */
    public synchronized void test(){

    }

    public synchronized static void main(String[] args) {
        synchronized (SynchornizedDemo.class){
            System.out.println("......");
        }
        //TODO
    }
}
