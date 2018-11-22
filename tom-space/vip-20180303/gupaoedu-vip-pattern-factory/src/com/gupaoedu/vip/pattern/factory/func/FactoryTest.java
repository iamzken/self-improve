package com.gupaoedu.vip.pattern.factory.func;

/**
 * Created by Tom on 2018/3/4.
 */
public class FactoryTest {
    public static void main(String[] args) {
        //System.out.println(new Factory().getMilk(););


        //货比三家
        //不知道谁好谁好谁坏
        //配置，可能会配置错
        Factory factory = new SanluFactory();
        System.out.println(factory.getMilk());

    }
}
