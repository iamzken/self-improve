package com.gupao.course.demo;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        try {
            SerializePerson();  //序列化操作
            Person.num=10;
            Person person=DeserializePerson();

            System.out.println(person.num+" - 反序列后的结果： "+person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //序列化方法
    private static void SerializePerson() throws Exception {
        Person person=new Person();
        person.setName("Michael");
        person.setAge(18);
        person.setSex("boy");
        person.setPhone("13838383838");
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("person")));

        oos.writeObject(person);
        System.out.println("Person对象序列化成功:"+person);
        oos.close();
    }

    //反序列化方法
    private static Person DeserializePerson() throws Exception{
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("person")));

        Person person=(Person)ois.readObject();

        ois.close();
        return person;
    }
}
