package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class XykCard extends Card{


    public XykCard(String number, String cvv, String expire) {
        super(number, cvv, expire);
    }

    @Override
    protected int getType() {
        return Contant.XINGYONGKA;
    }

    @Override
    protected void execTrans(int cents) {
        System.out.println("1. 判断【信用卡】");
        System.out.println("2. 渠道支付【信用卡】");
        System.out.println("3. 支付完成【信用卡】");
    }
}
