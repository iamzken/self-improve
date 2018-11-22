package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public interface PayMethod {

    /**
     * 支付
     * @param cents
     */
    public void pay(int cents);
}
