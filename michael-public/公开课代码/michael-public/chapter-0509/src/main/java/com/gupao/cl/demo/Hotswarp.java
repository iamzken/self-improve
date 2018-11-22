package com.gupao.cl.demo;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Hotswarp {

    public static void main(String[] args) throws Exception {
        doLoader();

        //动态替换
        System.gc();

        Thread.sleep(1000);

        File old=new File("D:\\workspace\\michael-public" +
                "\\chapter-0509\\target\\classes\\com\\gupao\\cl\\demo" +
                "\\HelloWorld.class");

        if(old.exists()){
            old.delete();
        }

        File ne=new File("HelloWorld.class");
        ne.renameTo(old);

        doLoader();

    }
    private static void doLoader() throws Exception {
        MyClassLoader myClassLoader=new MyClassLoader();

        Class<?> clazz=myClassLoader.findClass("com.gupao.cl.demo.HelloWorld");

        Object object=clazz.newInstance();

        Method method=clazz.getMethod("sayHi");

        method.invoke(object);

        System.out.println(object.getClass().getClassLoader());

    }
}
