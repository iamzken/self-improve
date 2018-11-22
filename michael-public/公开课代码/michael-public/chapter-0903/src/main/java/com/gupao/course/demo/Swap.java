package com.gupao.course.demo;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Hello world!
 *
 */
public class Swap{


    public static void main( String[] args ){
        Integer a=1,b=2; // 装箱 和拆箱 Integer.valueOf(1) ; Integer.valueOf(2)
        // 引用传递和值传递
        // Integer的缓存  -128到127
        //反射以及反射的局限性、反射对final变量修改的操作
        //装箱和拆箱操作
       System.out.println("before: a="+a+",b="+b);
        swap(a,b);
        System.out.println("after:a="+a+",b="+b);
    }

    public static void swap(Integer i1,Integer i2){
        //反射
        try {
            Field field=Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            Integer temp=new Integer(i1.intValue());

            field.set(i1,i2.intValue());  //Integer.valueOf(i2.intValue).intValue();   -> 2

            field.set(i2,temp);  //Integer.valueOf(tmp).intValue();


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
