package com.gupao.course.demo;

/**
 * Hello world!
 *
 */
public class App 
{

    //synchrnoized表现形式

    /**
     *  synchronized Method 表示锁定当前的实例对象
     *  synchronized (static lock) 锁定当前的class对象
     *  synchronized(){代码块}
     *
     */

    public synchronized void demo(){
        synchronized (this){

        }
    }

    public static void main( String[] args ) throws InterruptedException {
        synchronized (App.class){

        };
    }
}
