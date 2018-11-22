package com.gupao.course.demo.view;

import java.lang.reflect.Field;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a=1,b=2;  //Integer a=Integer.valueOf(1)
        System.out.println("Before:a="+a+",b="+b+""); //a=1,b=2
        swap(a,b);
        System.out.println("After:a="+a+",b="+b+""); //a=2, b=1

        Integer x=129;  //0x000232
        Integer y=129;  //Integer.valueOf(127)[-128,127]
        System.out.println(x==y);
    }

    public static void swap(Integer i1,Integer i2) throws NoSuchFieldException, IllegalAccessException {
        //TODO
        /*Integer c =i1; //基本类型、引用类型
        i1=i2;
        i2=c;*/
        Field field=Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        int tmp=i1.intValue(); //Integer - int
        field.set(i1,i2.intValue()); //装箱和拆箱
        field.set(i2,tmp);
    }
}
