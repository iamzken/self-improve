package com.gupao.course.demo.demo;

/**
 * Created by bjp-yxkj-gul on 2018/1/4.
 */
public class AdapterClient {
    public void test1(Target target){
        target.handleRequest();
    }

    public static void main(String[] args) {
        Target targer=Adapter::new;
        targer.handleRequest().handleRequest();
//        new AdapterClient().test1(Adapter::new);//不打印
//        new AdapterClient().test1(new Adapter());
    }
}
