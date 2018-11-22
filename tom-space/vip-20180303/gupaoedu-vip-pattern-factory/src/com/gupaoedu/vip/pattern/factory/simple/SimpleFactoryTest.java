package com.gupaoedu.vip.pattern.factory.simple;

import com.gupaoedu.vip.pattern.factory.Telunsu;

/**
 * 小作坊式的工厂模型
 * Created by Tom on 2018/3/4.
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {

        //这个new的过程实际上一个比较复杂的过程
        //有人民币及不需要自己new了
       // System.out.println(new Telunsu().getName());

        //小作坊式的生产模式
        //用户本身不再关心生产的过程，而只需要关心这个结果

        //假如：特仑苏、伊利、蒙牛
        //成分配比都是不一样的
        SimpleFactory factory = new SimpleFactory();

        //把用户的需求告诉工厂
        //创建产品的过程隐藏了，对于用户而且完全不清楚是怎么产生的
        System.out.println(factory.getMilk("AAA"));

        //知其然，知其所以然，知其所必然

    }
}
