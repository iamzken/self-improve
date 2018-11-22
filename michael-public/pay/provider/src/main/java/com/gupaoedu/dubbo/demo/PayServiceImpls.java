package com.gupaoedu.dubbo.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PayServiceImpls implements PayServices{

    public String pay(String param) {
        System.out.println("parameter："+param);
        return "帅帅的ｍｉｃ";
    }
}
