package com.gupao.course.demo.json;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FastJsonDemo {

    public static void main(String[] args) {
        User user=new User();
        List<User> friends=new ArrayList<User>();
        user.setUserName("mic");
        user.setSex("男");
        friends.add(user);

        User user1=new User();
        user1.setUserName("james");
        user1.setSex("男");
        friends.add(user1);

        User user2=new User();
        user2.setUserName("sam");
        user2.setSex("男");
        friends.add(user2);

        Long t1=System.currentTimeMillis();
        ByteArrayOutputStream out=new ByteArrayOutputStream();


        String text=JSON.toJSONString(user);
        System.out.println(text);
    }

}
