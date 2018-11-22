package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ThreadDemo {

    //ThreadDemo t1=new ThreadDemo()
    //ThreadDemo t2=new ThreadDemo()

    // mutex

    public synchronized void test(){

    }

    public void demo1(){
        synchronized (this){

        }
    }

    public void demo2(){
        synchronized (ThreadDemo.class){  //全局锁

        }
    }




    public   static void main(String[] args) throws InterruptedException {
        //业务逻辑处理
        Thread t1=new Thread(()->{  //子任务
            System.out.println("1");
        });

        Thread t2=new Thread(()->{//子任务
            System.out.println("2");
        });

        Thread t3=new Thread(()->{//子任务
            System.out.println("3");
        });
        t1.start();  //wait 和 notify   thread.cpp
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}
