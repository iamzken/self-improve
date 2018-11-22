package com.gupao.classLoader.demo;

import java.mic.String;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ClassLoaderTreeDemo {

    public static void main(String[] args) {
        ClassLoader loader=ClassLoaderTreeDemo.class.getClassLoader();
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
