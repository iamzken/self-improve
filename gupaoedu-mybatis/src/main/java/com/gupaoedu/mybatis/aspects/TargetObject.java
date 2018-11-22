package com.gupaoedu.mybatis.aspects;

public class TargetObject {
    public void foo() {
        System.out.println("foo ...");
        bar();
    }

    public void bar() {
        System.out.println("bar ...");
    }
}