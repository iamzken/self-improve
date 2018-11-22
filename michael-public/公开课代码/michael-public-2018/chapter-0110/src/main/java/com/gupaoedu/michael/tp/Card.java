package com.gupaoedu.michael.tp;

import com.gupaoedu.michael.PaymentMethod;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public abstract class Card implements PaymentMethod {

    private String name;
    private String number;

    //用来记录当前抽象类型的所有支付渠道
    public static Map<String,Card> cardMap=new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        cardMap.put(getType(),this);
    }


    public Card() {
    }

    public Card(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public void pay(int cents) {
        execTransaction(cents);
    }

    protected abstract String getType(); //当前的支付类型

    protected abstract void execTransaction(int cents);
}
