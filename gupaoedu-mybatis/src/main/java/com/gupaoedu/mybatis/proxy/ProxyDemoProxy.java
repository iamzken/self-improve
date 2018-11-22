package com.gupaoedu.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by James on 2017-06-02.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ProxyDemoProxy implements InvocationHandler {
    private IProxyInterface target;

    public ProxyDemoProxy(IProxyInterface target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy before >" + method.getName());
        return method.invoke(target,args);
    }
}
