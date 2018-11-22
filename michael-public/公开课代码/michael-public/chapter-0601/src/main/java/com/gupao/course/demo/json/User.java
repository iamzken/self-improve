package com.gupao.course.demo.json;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class User implements Serializable{

    private static final long serialVersionUID = -6287283596489111610L;
    @Protobuf(fieldType = FieldType.STRING,required = false,order = 1)
    private String userName;

    @Protobuf(fieldType = FieldType.STRING,required = false,order = 2)
    private String sex;

    @Protobuf(fieldType = FieldType.OBJECT,required = false,order = 3)
    private List<User> friends;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", friends=" + friends +
                '}';
    }
}
