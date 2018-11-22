package com.gupao.course.demo;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a=129,b=129; //Integer a=Integer.valueOf(1)
        System.out.println(a==b);
        System.out.println("before:a="+a+",b="+b);
        swap(a,b);
        System.out.println("after:a="+a+",b="+b);
    }
    public static void swap(Integer i1,Integer i2) throws NoSuchFieldException, IllegalAccessException {
        Field field=Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer tmp=new Integer(i1.intValue()); //1
        field.set(i1,i2.intValue()); //Integer.valueOf(i2.intValue).intValue()
        field.set(i2,tmp);
    }
}
