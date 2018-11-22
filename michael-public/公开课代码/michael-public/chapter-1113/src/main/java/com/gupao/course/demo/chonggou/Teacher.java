package com.gupao.course.demo.chonggou;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Teacher {

    private String sex;

    private String name;

    private int age;

    public Teacher(String sex, String name, int age) {
        this.sex = sex;
        this.name = name;
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
