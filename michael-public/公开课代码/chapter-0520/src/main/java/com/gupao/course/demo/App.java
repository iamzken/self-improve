package com.gupao.course.demo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassLoader loader=App.class.getClassLoader();
        while(loader!=null){
            System.out.println(loader.toString());
            loader=loader.getParent();
        }
        System.out.println(loader);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
