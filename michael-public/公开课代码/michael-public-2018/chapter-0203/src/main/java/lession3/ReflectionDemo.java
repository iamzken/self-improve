package lession3;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ReflectionDemo {

    public static final Integer VALUE=100;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println(VALUE);

        /**
         * 通过反射去修改这个Value
         */
        Field field=ReflectionDemo.class.getDeclaredField("VALUE");
        field.setAccessible(true);
        Field modifyField=Field.class.getDeclaredField("modifiers");
        modifyField.setAccessible(true);
        modifyField.setInt(field,field.getModifiers()&~Modifier.FINAL);
        /******/
        field.set(null,200);
        System.out.println(VALUE);

    }
}
