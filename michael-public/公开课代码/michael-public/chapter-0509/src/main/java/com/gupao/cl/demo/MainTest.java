package com.gupao.cl.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MainTest {

    public static void main(String[] args) throws ClassNotFoundException {

        MyClassLoader myClassLoader=new MyClassLoader();
        Object object=myClassLoader.loadClass("com.gupao.cl.demo.Person");
        Object object2=myClassLoader.loadClass("com.gupao.cl.demo.Person");
//        System.out.println(object.getClass().getClassLoader());
/*
        System.out.println(object.getClass().getClassLoader());

        Person person=new Person();
        System.out.println(person.getClass().getClassLoader());*/
    }
}
