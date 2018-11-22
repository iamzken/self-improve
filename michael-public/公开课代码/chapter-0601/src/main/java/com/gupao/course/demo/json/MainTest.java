package com.gupao.course.demo.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MainTest {
    private static final long count=1000000001;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    //并发执行
    private static void concurrency() throws InterruptedException{
        long start=System.currentTimeMillis();
        Thread thread=new Thread(()->{
            int a=0;
            for(long i=0;i<count;i++){
                a+=5;
            }
        });
        thread.start();
        int b=0;
        for(long i=0;i<count;i++){
            b--;
        }
        long time=System.currentTimeMillis()-start;
        thread.join();
        System.out.println("concurrency : "+time+"ms, b="+b);

    }
    //串行执行
    private static void serial(){
        long start=System.currentTimeMillis();
        int a=0;
        for(long i=0;i<count;i++){
            a+=5;
        }
        int b=0;
        for(long i=0;i<count;i++){
            b--;
        }
        long time=System.currentTimeMillis()-start;
        System.out.println("serial:"+time+"ms,b="+b+",a="+a);
    }
}
