package com.gupao.course.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo2 {

    static List<Person> repo= Arrays.asList(
            new Person("Mic",18,"male"),
            new Person("sam",35,"female"),
            new Person("tom",30,"male"),
            new Person("james",40,"female")
    );

    public static void main(String[] args) { //java.util.function

        List<Person> rs=filterPersonWithAge(repo,(Person person)->person.getAge()>30);
        System.out.println(rs);
    }

    private static List<Person> filterPersonWithAge(List<Person> repo, Predicate<Person> predicate){
        return repo.parallelStream().filter(predicate).limit(1).collect(Collectors.toList());
    }
}
