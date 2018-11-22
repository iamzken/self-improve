package com.gupao.course.demo.pay;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Main {

    public static void main(String[] args) {
        Order order=new Order();
        order.addProduct(new Product(198,"娃娃"));
        order.addProduct(new Product(98,"麦克风"));
        try {
            PayMethodFactory.getPayMethod(Contant.XINGYONGKA).pay(order.getSumPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
