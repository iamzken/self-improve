package com.gupaoedu.demo.entity;

public class Student {

    private Integer sid;
    private String name;
    private String qq;

    public Student() {
    }

    public Student(Integer sid, String name, String age) {
        this.sid = sid;
        this.name = name;
        this.qq = qq;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
