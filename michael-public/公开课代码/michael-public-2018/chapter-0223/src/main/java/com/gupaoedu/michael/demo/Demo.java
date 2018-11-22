package com.gupaoedu.michael.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {

    public static void main(String[] args) {
        //构造引用    方法引用实际上是对lambda表达式的进一步简化
        PersonFactory factory=new PersonFactory(Person::new);
        Person p1=factory.getPerson();
        List<Person> personList=new ArrayList<>();
        p1.setName("Mic");
        personList.add(p1);
        Person p2=factory.getPerson();
        p2.setName("James");
        personList.add(p2);
        Person p3=factory.getPerson();
        p3.setName("Tom");
        personList.add(p3);
        print(personList);  //排序前
//        personList.sort(Demo::myCompare);  //静态方法引用
//        personList.sort(p1::compare);  //特定对象的实例方法
        personList.sort(Person::compareTo);  //引用某个对象的任意对象的实例方法
        print(personList);  //排序前
    }
    private static void print(List<Person> personList){
        personList.forEach(p-> System.out.print(p.getName()+"  "));
        System.out.println();
    }

    private static int myCompare(Person p1,Person p2){
        return p2.getName().compareTo(p1.getName());
    }
}
