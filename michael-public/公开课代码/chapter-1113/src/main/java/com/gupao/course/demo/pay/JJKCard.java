package com.gupao.course.demo.pay;

import com.sun.org.apache.bcel.internal.classfile.Constant;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JJKCard extends Card{


    public JJKCard(String number, String cvv, String expire) {
        super(number, cvv, expire);
    }

    @Override
    protected int getType() {
        return Contant.JIEJIKA;
    }

    @Override
    protected void execTrans(int cents) {
        System.out.println("1. 判断【借记卡】");
        System.out.println("2. 渠道支付【借记卡】");
        System.out.println("3. 支付完成【借记卡】");
    }
}
