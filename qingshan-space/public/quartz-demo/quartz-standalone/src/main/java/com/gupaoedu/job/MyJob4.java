package com.gupaoedu.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qingshan
 * @Date: 2018/10/15 19:40
 * @Description: 咕泡学院，只为更好的你
 * 这是一个不能并行执行的任务
 */
@DisallowConcurrentExecution
public class MyJob4 implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println(" " + sf.format(date) + "   任务4开始执行了，请等待3秒");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
