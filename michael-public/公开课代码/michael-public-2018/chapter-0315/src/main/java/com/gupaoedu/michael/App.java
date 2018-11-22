package com.gupaoedu.michael;

/**
 * Hello world!
 *
 */
public class App
{

    static Object obj=new Object();
    static Object obj1=new Object();
    public void  demo(String param){
        synchronized (obj){  //实例锁

        }
    }
    public void  demo1(String param){
        synchronized (obj1){  //实例锁

        }
    }

    public static void main( String[] args )
    {
       App app=new App();
       App app1=new App();
       new Thread(()->{
           app.demo(Thread.currentThread().getId()+"");
        }).start();
        new Thread(()->{
            app1.demo(Thread.currentThread().getId()+"");
        }).start();
    }
}
