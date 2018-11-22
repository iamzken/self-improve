package com.gupaoedu.vip.pattern.proxy.staticed;

public class StaticProxyTest {

    public static void main(String[] args) {

        //只能帮儿子找对象
        //不能帮表妹、不能帮陌生人
        Father father = new Father(new Son());

        father.findLove();

    }
}
