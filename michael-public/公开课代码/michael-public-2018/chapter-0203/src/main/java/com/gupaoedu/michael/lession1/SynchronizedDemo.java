package com.gupaoedu.michael.lession1;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SynchronizedDemo {

    /**
     * 作用域
     * 静态实例  全局锁
     * 对象锁 Object a= new Object()  Object b=new Object()
     * 代码块
     */

    public synchronized void demo(){
        synchronized (this){

        }
    }

    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class){

        }
    }
}
