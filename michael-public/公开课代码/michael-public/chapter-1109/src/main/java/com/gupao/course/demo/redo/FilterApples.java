package com.gupao.course.demo.redo;

import com.gupao.course.demo.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FilterApples {

    /**
     * 库存
     */
    static List<Apple> repos= Arrays.asList(
            new Apple(50,"green"),
            new Apple(150,"red"),
            new Apple(250,"red"),
            new Apple(100,"green"),
            new Apple(180,"red"),
            new Apple(60,"green")
            );
    public static void main(String[] args) {
        List<Apple> rs=filterApplesSingle(repos,new AppleHeavyStrategy());

        System.out.println(rs);
    }
    /**
     * 筛选颜色
     * 公共方法 common
     * @param repos
     * @return
     */
    private static List<Apple> filterApplesSingle(List<Apple> repos,AppleStrategy strategy){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:repos){
            //可变的行为取决于这个if   行为参数化
            if(strategy.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    private static List<Apple> filterApples(List<Apple> repos,List<AppleStrategy> strategy){
        List<Apple> result=new ArrayList<>();
        for(Apple apple:repos){
            //可变的行为取决于这个if   行为参数化
            for(AppleStrategy strategy1:strategy){
                if(strategy1.test(apple)){
                    result.add(apple);
                }
            }
        }
        return result;
    }
}
