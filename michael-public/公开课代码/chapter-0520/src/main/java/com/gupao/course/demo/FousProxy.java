package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FousProxy implements BuyCarSubject{

    private MicCustomer customer;

    public FousProxy(MicCustomer customer){
        this.customer=customer;
    }

    public void buyCar() { //实现为客户买车的动作
        if(customer.getCash()>=1000000) {
            customer.buyCar();
        }else{
            System.out.println("钱都没有，装什么。。");
        }
    }
}
