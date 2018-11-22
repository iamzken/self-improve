package com.gupao.course.demo.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class StreamDemo {

    public static final List<Dish> dishes=Arrays.asList(
            new Dish("猪肉",800),
            new Dish("牛肉",600),
            new Dish("鸡肉",700),
            new Dish("薯条",500),
            new Dish("米饭",400),
            new Dish("水果",60)
    );

    public static void main(String[] args) {
       // dishes.forEach(System.out::print);

       /* List<Integer> nb=Arrays.asList(1,2,3,4,5,6,7,8,9);
        nb.parallelStream().filter(i->i%2==0).distinct().forEach(System.out::print);*/

        System.out.println(dishes.stream().filter(dish -> dish.getCs()>400).limit(2).collect(Collectors.toList()));
    }
    private static List<String> getLowCsName(List<Dish> dishes){
         return dishes.stream().filter(dish->dish.getCs()>400).
                sorted(Comparator.comparing(Dish::getName)).
                map(Dish::getName).collect(Collectors.toList());
         //像研究技术一样研究迷茫的本质:对未来的不确定性
        //几个问题
        //1. 自己的兴趣，越早越好; 第三方的职业评测
        //2. 认清自己的性格
        //3. 职业规划
    }


/*
    //cs低于400， 那么把名称返回, 最好能够按照名称排序
    private static List<String> getLowCsName(List<Dish> dishes){
        //筛选
        List<Dish> lowCsList=new ArrayList<>();
        for(Dish dish:dishes){
            if(dish.getCs()>400){
                lowCsList.add(dish);
            }
        }
        //排序
        List<String> lowCsNameList=new ArrayList<>();
        Collections.sort(lowCsList, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        //获得符合结果的名称
        for(Dish d:lowCsList){
            lowCsNameList.add(d.getName());
        }
        return lowCsNameList;

    }*/

}
