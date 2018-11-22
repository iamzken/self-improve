package com.gupao.course.demo;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SerialRulMainDemo {
    public static void main(String[] args) throws Exception{
        Person person=new Person();

        ObjectOutputStream oo=
                new ObjectOutputStream(new FileOutputStream("person.object"));

        oo.writeObject(person);
        oo.flush();
        System.out.println(new File("person.object").length());

        oo.writeObject(person);
        oo.flush();
        System.out.println(new File("person.object").length());

        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("person.object"));
        Person p1=(Person)ois.readObject();
        Person p2=(Person)ois.readObject();
        ois.close();
        oo.close();
        System.out.println(p1==p2);

    }
}
