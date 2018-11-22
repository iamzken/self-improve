package com.gupaoedu.vip.mongo.transaction.entity;

import org.bson.types.ObjectId;

/**
 * Created by Tom on 2018/9/2.
 */
public class Member {


    private ObjectId  id;
    private String name;
    private int age;
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
