package com.gupao.course.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {

    public static int value=10;


    public static void main(String[] args) {
        System.out.println(value);
        //反射的局限性 int  String
        //对于基本类型的静态常量， java在编译的时候，会把代码中的常量中的引用的地方替换成响应常量的值
        try {
            Field field=Demo.class.getField("value");
            field.setAccessible(true);
            Field modiField=Field.class.getDeclaredField("modifiers");
            modiField.setAccessible(true);
            modiField.setInt(field,field.getModifiers()&~Modifier.FINAL);
            field.set(null,200);

            System.out.println(10);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
