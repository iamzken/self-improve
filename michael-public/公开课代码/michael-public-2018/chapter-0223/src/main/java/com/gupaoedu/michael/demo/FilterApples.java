package com.gupaoedu.michael.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class FilterApples {

    static List<Apple> repos= Arrays.asList(
            new Apple(80,"green"),
            new Apple(120,"green"),
            new Apple(150,"red"),
            new Apple(70,"red")
    );

    public static void main(String[] args) {
        System.out.println(filterApples(apple -> apple.getWeitght()>100));
    }

    /**
     * Predicate  接收一个T对象返回boolean
     * Consumer   接收一个T对象，不返回值
     * Function   接收一个T对象，返回R对象
     * Supplier   提供T对象，不接收值
     */


    private static List<Apple> filterApples(Predicate<Apple> predicate){
        List<Apple> result=new ArrayList<>();
        for(Apple app:repos){
            if(predicate.test(app)){
                result.add(app);
            }
        }
        return result;
    }

}
