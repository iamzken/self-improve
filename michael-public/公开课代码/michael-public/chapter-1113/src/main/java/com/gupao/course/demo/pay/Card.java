package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public abstract class Card implements PayMethod{
    private  String number;

    private  String cvv;  //cvv

    private  String expire; // 过期时间

    public Card(String number, String cvv, String expire) {
        this.number = number;
        this.cvv = cvv;
        this.expire = expire;
    }

    @Override
    public void pay(int cents) {
        execTrans(cents);
        System.out.println("卡支付："+cents);
    }

    protected abstract int getType(); //

    protected abstract void execTrans(int cents);
}
