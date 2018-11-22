package com.gupao.course.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DyamicProxy implements InvocationHandler{

    private MicCustomer customer;

    public DyamicProxy(MicCustomer customer){
        this.customer=customer;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        method.invoke(customer,args);
        System.out.println("after");
        return null;
    }
}
