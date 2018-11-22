package com.gupao.course.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MicCustomer implements BuyCarSubject{

    private int cash;

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void buyCar() {
        System.out.println("老司机Mic买一辆车：保时捷，费用:" + getCash() + ", 向女神表白");
    }
}
