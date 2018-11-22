package com.gupaoedu.entity;

/**
 * 用户实体类
 */
public class User {

    private Integer id; // ID，数据库自增
    private String account; // 登录账号，UUID生成
    private String name; // 姓名
    private Integer age; // 年龄

    public User() {
    }

    public User(Integer id, String account,String name, Integer age) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
