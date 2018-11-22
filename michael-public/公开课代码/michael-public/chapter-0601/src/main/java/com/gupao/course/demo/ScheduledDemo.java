package com.gupao.course.demo;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ScheduledDemo implements Runnable{


    public void run() {
        System.out.println("Execute success");
    }

    public static void main(String[] args) {
        ScheduledExecutorService service= Executors.newScheduledThreadPool(10);
        //每周四晚上21:30分执行一次操作
        Calendar currentDate=Calendar.getInstance();
        System.out.println("当前时间："+currentDate.getTime().toString());
        long currentDateLong=currentDate.getTime().getTime();
        Calendar earliestDate=Calendar.getInstance();
        earliestDate.set(Calendar.DAY_OF_WEEK,4);
        earliestDate.set(Calendar.HOUR_OF_DAY,21);
        earliestDate.set(Calendar.MINUTE,35);
        earliestDate.set(Calendar.SECOND,0);
        long earliestDateLong=earliestDate.getTime().getTime();
        long delay=earliestDateLong-currentDateLong;
        long period=7*24*60*60*1000;
        service.scheduleWithFixedDelay(new ScheduledDemo(),delay,period, TimeUnit.MILLISECONDS);
    }
}
