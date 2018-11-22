package com.gupao.course.demo.mf;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Person implements Comparable<Person>{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compare(Person p1,Person p2){
        return p1.getName().compareTo(p2.getName());
    }

    @Override
    public int compareTo(Person o) {
        return o.getName().compareTo(this.getName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
