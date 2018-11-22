package com.gupao.course.demo.mf;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * //静态引用
 * //对象实例引用
 * //引用某个类型的任意对象的方法引用
 * //类构造函数
 */
public class MfDemo {
    public static void main(String[] args) {
        PersonFactory factory=new PersonFactory(Person::new); //类构造函数

        List<Person> personList=new ArrayList<>();

        Person p1=factory.getPerson();
        p1.setName("Mic");
        personList.add(p1);

        Person p2=factory.getPerson();
        p2.setName("James");
        personList.add(p2);

        Person p3=factory.getPerson();
        p3.setName("Tom");
        personList.add(p3);

        print(personList);

       // personList.sort(MfDemo::myCompare); //静态方法引用

     //   personList.sort(p1::compare); //方法引用

         personList.sort(Person::compareTo);// 用特定的对象的实例方法
        print(personList);
    }

    private static int myCompare(Person p1,Person p2){
        return p2.getName().compareTo(p1.getName());
    }

    private static void print(List<Person> personList){
        personList.forEach((Person p)->{
            System.out.print(p.getName()+"--");
        });
        System.out.println();
    }
}
