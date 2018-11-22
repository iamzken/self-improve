package com.gupaoedu.michael;

/**
 * Hello world!
 *
 */
public class App 
{



    public static void main( String[] args ) throws InterruptedException {
        Object object=new Object();
        ThreadWait threadWait=new ThreadWait(object);
        threadWait.start();
        ThreadNotify threadNotify=new ThreadNotify(object);
        threadNotify.start();


        /*Thread t1=new Thread(()->{
            System.out.println("1");
        });

        Thread t2=new Thread(()->{
            System.out.println("2");
        });
        Thread t3=new Thread(()->{
            System.out.println("3");
        });
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();*/

    }
}
