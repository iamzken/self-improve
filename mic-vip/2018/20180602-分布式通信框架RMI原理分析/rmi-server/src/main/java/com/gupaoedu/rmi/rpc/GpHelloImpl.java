package com.gupaoedu.rmi.rpc;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 */
public class GpHelloImpl implements IGpHello{
    @Override
    public String sayHello(String msg) {
        return "Hello , "+msg;
    }
}
