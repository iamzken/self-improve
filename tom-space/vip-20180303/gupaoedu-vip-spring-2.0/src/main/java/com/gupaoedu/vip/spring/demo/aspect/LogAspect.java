package com.gupaoedu.vip.spring.demo.aspect;

/**
 * Created by Tom on 2018/5/2.
 */
public class LogAspect {

    //在调用一个方法之前，执行before方法
    public void before(){
        //这个方法中的逻辑，是由我们自己写的
        System.out.println("Invoker Before Method!!!");
    }

    //在调用一个方法之后，执行after方法
    public void after(){
        System.out.println("Invoker After Method!!!");
    }
}
