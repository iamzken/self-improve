package com.gupaoedu.mybatis.proxy;

/**
 * Created by James on 2017-07-09.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class Outter {
    private IProxyInterface proxyInterface;

    public Outter(IProxyInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public void test(){
        proxyInterface.hello();
    }

    public void test2(){
        proxyInterface.hello2();
    }
}
