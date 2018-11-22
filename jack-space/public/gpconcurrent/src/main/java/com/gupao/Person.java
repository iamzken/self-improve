package com.gupao;

public class Person {
    private String username;
    private Integer age;

    public Person(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public synchronized void change(String username, Integer age){
        this.username=username;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.age=age;
        System.out.println(Thread.currentThread().getName()+"--->username="+this.username+",age="+this.age);
    }
}
