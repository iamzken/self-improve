package com.gupao.course.demo.chonggou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TeacherService {

    static List<Teacher> teachers= Arrays.asList(
            new Teacher("男","james",28),
            new Teacher("男","mic",18),
            new Teacher("女","菲菲",18),
            new Teacher("男","sam",24),
            new Teacher("女","木子",20),
            new Teacher("男","tom",35)
    );

    public static void main(String[] args) {
        System.out.println(filterTeachers(teachers,new FilterWithAgeStrategy()));
    }

    // 筛选年龄是18岁的 筛选性别
    private static List<Teacher> filterTeachers(List<Teacher> teachersSexAndAge,FilterStrategy filterStrategy){
        List<Teacher> rs=new ArrayList<>();
        for(Teacher teacher:teachers){
           if(filterStrategy.check(teacher)){
               rs.add(teacher);
           }
        }
        return rs;
    }


}
