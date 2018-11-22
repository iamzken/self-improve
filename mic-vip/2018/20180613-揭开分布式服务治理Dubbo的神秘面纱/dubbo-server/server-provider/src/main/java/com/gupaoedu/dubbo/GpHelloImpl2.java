package com.gupaoedu.dubbo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class GpHelloImpl2 implements IGpHello{

    @Override
    public String sayHello(String msg) {
        return "Hello,i'm server 2:"+msg;
    }
}
