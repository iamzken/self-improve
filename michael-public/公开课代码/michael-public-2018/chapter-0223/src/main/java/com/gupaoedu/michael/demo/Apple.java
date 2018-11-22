package com.gupaoedu.michael.demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Apple {

    private int weitght;
    private String color;

    public Apple(int weitght, String color) {
        this.weitght = weitght;
        this.color = color;
    }

    public int getWeitght() {
        return weitght;
    }

    public void setWeitght(int weitght) {
        this.weitght = weitght;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weitght=" + weitght +
                ", color='" + color + '\'' +
                '}';
    }
}
