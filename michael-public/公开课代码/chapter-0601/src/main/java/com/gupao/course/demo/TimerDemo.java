package com.gupao.course.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TimerDemo extends TimerTask{

    private String jobName;

    public TimerDemo(String jobName){
        this.jobName=jobName;
    }

    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerDemo("jobName1"),1000,5000);
//        timer.scheduleAtFixedRate(new TimerDemo("jobName1"),1000,1000);
    }

    public void run() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.scheduledExecutionTime())+"execute:"+jobName);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
