package com.gupaoedu.vip.pattern.proxy.cglib;

/**
 * Created by Tom on 2018/3/10.
 */
public class CglibTest {

    public static void main(String[] args) {

        try {
            ZhangSan obj = (ZhangSan)new CglibMeipo().getInstance(ZhangSan.class);
            obj.findLove();
            System.out.println("--------------------------------");
           // System.out.println(obj.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
