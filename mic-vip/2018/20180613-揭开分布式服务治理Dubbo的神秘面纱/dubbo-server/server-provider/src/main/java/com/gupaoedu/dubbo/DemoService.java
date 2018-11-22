package com.gupaoedu.dubbo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DemoService implements IDemoService{


    @Override
    public String protocolDemo(String msg) {
        return "I'm Protocol Demo:"+msg;
    }
}
