package com.gupao.course.demo.stream;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Dish {

    private String name; //名称

    private int cs;  //卡路里

    public Dish(String name, int cs) {
        this.name = name;
        this.cs = cs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cs=" + cs +
                '}';
    }
}
