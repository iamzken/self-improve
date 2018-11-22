package com.gupaoedu.quartz;

/**
 * @Author: qingshan
 * @Date: 2018/10/18 19:50
 * @Description: 咕泡学院，只为更好的你
 */
public class TestClass extends TestAbstrctClass {
    public void test(String msg){
        super.test(msg);
        System.out.println(super.getClass().hashCode());
    }

    public static void main(String[] args) {
        TestClass aa = new TestClass();
        aa.test("fjdlskjafldksj");

    }
}
