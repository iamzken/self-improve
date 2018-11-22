package lession3;

import java.lang.reflect.Field;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SwapDemo {




    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a=1,b=2;  //装箱  Integer a=1  ->  Integer.valueOf(1)
        System.out.println("before: a="+a+",b="+b+"");
        swap(a,b);
        System.out.println("after: a="+a+",b="+b+"");
    }

    private static void swap(Integer i1,Integer i2) throws NoSuchFieldException, IllegalAccessException {//Integer/Double/String
        Field field=Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer tmp=new Integer(i1.intValue());
        field.set(i1,i2.intValue()); //装箱操作 i1= Integer.valueOf(i2.intValue).intValue();  ->2
        field.set(i2,tmp); //tmp=Integer.valueOf(tmp).intValue();  ->  i2.value= 2
    }
}
