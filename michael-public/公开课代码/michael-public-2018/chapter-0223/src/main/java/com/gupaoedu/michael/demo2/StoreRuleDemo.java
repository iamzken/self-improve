package com.gupaoedu.michael.demo2;

import java.io.*;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class StoreRuleDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("user.txt"));
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("user.txt"));
        User user=new User();
        user.setName("mic");
        out.writeObject(user);
        User user1=(User) in.readObject(); //第一次读取对象
        out.flush();
        System.out.println(new File("user.txt").length());
        user.setName("James");
        out.writeUnshared(user);
        out.close();
        User user2=(User) in.readObject(); //第二次读取对象
        System.out.println(new File("user.txt").length());
        System.out.println(user1==user2);
        System.out.println(user2.getName());
    }
}
