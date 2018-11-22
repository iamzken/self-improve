package com.gupaoedu.serial;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Email email=new Email();
        email.setContent("今天晚上20：00有课程");
        Person p1=new Person("Mic");
        p1.setEmail(email);

//        Person p2=p1.clone();
        Person p2=p1.deepClone();
        p2.setName("黑白");
        p2.getEmail().setContent("今天晚上是20：30上课");

        System.out.println(p1.getName()+"->"+p1.getEmail().getContent());
        System.out.println(p2.getName()+"->"+p2.getEmail().getContent());

    }
}
