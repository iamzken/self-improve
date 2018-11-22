package com.gupaoedu.mybatis.proxy;

/**
 * Created by James on 2017-06-02.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ProxyImpl implements IProxyInterface {

    public void hello() {
        System.out.println("ProxyImpl hello");
    }

    @Override
    public void hello2() {
        hello();
        System.out.println("ProxyImpl hello2");
    }
}
