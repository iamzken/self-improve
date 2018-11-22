package com.gupaoedu.michael;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {


    public static void main(String[] args) {
        Object obj=new Object();
        ThreadWait t1=new ThreadWait(obj);
        ThreadNotify t2=new ThreadNotify(obj);

        t1.start();
        t2.start();
    }
}
