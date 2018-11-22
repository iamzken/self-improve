package com.gupaoedu.michael.tp;

import com.gupaoedu.michael.PaymentMethod;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Cash implements PaymentMethod {

    @Override
    public void pay(int cents) {
        System.out.println("使用现金支付：["+cents+"]");
    }
}
