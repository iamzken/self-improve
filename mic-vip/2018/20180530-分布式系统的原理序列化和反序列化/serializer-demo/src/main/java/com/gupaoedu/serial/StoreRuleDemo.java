package com.gupaoedu.serial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class StoreRuleDemo {

    public static void main(String[] args) throws IOException {
        ObjectOutputStream outputStream=
                new ObjectOutputStream(new FileOutputStream(new File("user")));
        User user=new User();
        user.setAge(18);
        user.setName("Mic");
        user.setHobby("菲菲");
        user.setSex("男");
        outputStream.flush();
        outputStream.writeObject(user);
        System.out.println(new File("user").length());
        outputStream.writeObject(user);
        outputStream.flush();
        outputStream.close();
        System.out.println(new File("user").length());

    }
}
