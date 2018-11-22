package com.gupao.cl.demo;

import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MyClassLoader extends ClassLoader{

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String fileName =name.substring(name.lastIndexOf(".")+1)+".class";
            InputStream is=getClass().getResourceAsStream(fileName);
            byte[] b=new byte[is.available()];
            is.read(b);
            return defineClass(name,b,0,b.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }
}
