package com.gupao.course.demo.demo;

/**
 * Created by bjp-yxkj-gul on 2018/1/4.
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public Adapter handleRequest() {
        super.request();
        return this;
    }
}
