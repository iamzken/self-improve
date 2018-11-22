package com.gupao.web;

/**
 * Created by James on 2018-01-22.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public enum DemoEnum {
    stay("stay",0),
    djj("djj",0),;

    private String name;
    private int i;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DemoEnum(String name, int i) {
        this.name = name;
        this.i = i;
    }
}
