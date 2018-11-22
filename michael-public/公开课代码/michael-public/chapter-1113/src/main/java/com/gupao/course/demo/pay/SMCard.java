package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SMCard extends Card {


    public SMCard(String number, String cvv, String expire) {
        super(number, cvv, expire);
    }

    @Override
    protected int getType() {
        return 0;
    }

    @Override
    protected void execTrans(int cents) {

    }
}
