package com.gupao.course.demo;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProxyMain {

    public static void main(String[] args) throws IOException {
        MicCustomer customer=new MicCustomer();
        customer.setCash(10000000);
        /*FousProxy fp=new FousProxy(customer);
        fp.buyCar();*/

        DyamicProxy dp=new DyamicProxy(customer);

        BuyCarSubject dcs=(BuyCarSubject)Proxy.newProxyInstance(BuyCarSubject.class.getClassLoader(),new Class[]{BuyCarSubject.class},dp);

        dcs.buyCar();

        byte[] data=ProxyGenerator.generateProxyClass("Michael",new Class[]{BuyCarSubject.class});

        FileOutputStream out=new FileOutputStream("Michael.class");

        out.write(data);
    }
}
