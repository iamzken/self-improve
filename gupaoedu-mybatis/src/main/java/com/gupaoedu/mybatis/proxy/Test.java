package com.gupaoedu.mybatis.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by James on 2017-06-02.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class Test {
    public static void main(String[] args) {
        ProxyImpl target = new ProxyImpl();
        IProxyInterface proxyInterface = (IProxyInterface) Proxy.newProxyInstance(
                IProxyInterface.class.getClassLoader(),
                new Class []{IProxyInterface.class},new ProxyDemoProxy(target));
//        proxyInterface.hello2();
        new Outter(proxyInterface).test();
    }
}
