package com.gupaoedu.michael.demo2;

import java.io.IOException;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
      User user=new User();
      user.setName("Mic");
      ISerializer iSerializer=new HessianSerializer();
      byte[] rs=iSerializer.serialize(user);
      System.out.println(new String(rs));
      User user1=iSerializer.deserialize(rs,User.class);
      System.out.println(user1.getName());

    }
}
