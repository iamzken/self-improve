package com.gupao.course.demo;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Parent implements Serializable{


    private static final long serialVersionUID = 3542423565661701131L;
    private transient String phone;  //string类型的值默认是null ， int类型的值默认是0

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "phone='" + phone + '\'' +
                '}';
    }
}
