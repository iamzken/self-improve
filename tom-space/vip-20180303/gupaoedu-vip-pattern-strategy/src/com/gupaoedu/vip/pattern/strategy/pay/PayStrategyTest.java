package com.gupaoedu.vip.pattern.strategy.pay;

import com.gupaoedu.vip.pattern.strategy.pay.Order;
import com.gupaoedu.vip.pattern.strategy.pay.payport.PayType;
import com.gupaoedu.vip.pattern.strategy.pay.payport.Payment;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Tom on 2018/3/11.
 */
public class PayStrategyTest {

    public static void main(String[] args) {

        //省略把商品添加到购物车，再从购物车下单
        //直接从点单开始
        Order order = new Order("1","20180311001000009",324.45);

        //开始支付，选择微信支付、支付宝、银联卡、京东白条、财付通
        //每个渠道它支付的具体算法是不一样的
        //基本算法固定的

        //这个值是在支付的时候才决定用哪个值
        System.out.println(order.pay(PayType.WECHAT_PAY));

        //BeanFactory

        //根据url去自动选择
        //爬取百度的数据     BaiduParser
        //                 SinaParser
        //                 SougouParser
        //返回一个解析好的Json格式，统一好了，保存入库


    }

}
