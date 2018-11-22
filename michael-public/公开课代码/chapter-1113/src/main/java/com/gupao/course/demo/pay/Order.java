package com.gupao.course.demo.pay;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Order {

    private List<Product> products=new ArrayList<>(); //订单中的商品

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    //获得总的订单金额
    public int getSumPrice(){
        return products.parallelStream().mapToInt(prod ->prod.getPrice()).sum();
    }


}
