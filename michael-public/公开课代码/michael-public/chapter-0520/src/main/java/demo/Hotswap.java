package demo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Hotswap {

    public static void main(String[] args) throws Exception {
        load();
        System.gc();

        Thread.sleep(1000);

        File fv2=new File("HelloWorld.class");
        File fv1=new File("D:\\workspace\\michael-public\\chapter-0520\\target\\classes\\demo\\HelloWorld.class");
        if(fv1.exists()) {
            fv1.delete();
        }
        fv2.renameTo(fv1);
        System.out.println();
        load();
    }

    private static void load() throws Exception {
        MyClassLoader loader=new MyClassLoader();
        Class<?> clazz=loader.findClass("demo.HelloWorld");
        Object object=clazz.newInstance();
        Method method=clazz.getMethod("sayHello");
        method.invoke(object);
        System.out.println(object.getClass()+"  "+object.getClass().getClassLoader());
    }
}
