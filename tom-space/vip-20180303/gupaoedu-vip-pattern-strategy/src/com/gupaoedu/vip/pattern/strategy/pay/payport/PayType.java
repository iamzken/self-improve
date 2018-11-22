package com.gupaoedu.vip.pattern.strategy.pay.payport;

/**
 * 姑且把这个枚举当做一个常量去维护
 * Created by Tom on 2018/3/11.
 */
public enum PayType {
    ALI_PAY(new AliPay()),
    WECHAT_PAY(new WechatPay()),
    UNION_PAY(new UnionPay()),
    JD_PAY(new JDPay());

    private Payment payment;
    PayType(Payment payment){
        this.payment = payment;
    }

    public Payment get(){ return  this.payment;}
}
