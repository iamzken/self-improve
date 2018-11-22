package com.gupaoedu.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @Author: qingshan
 * @Date: 2018/9/19 15:08
 * @Description: 咕泡学院，只为更好的你
 */
public class MyJobListener implements JobListener {

    public String getName() {
        String name = getClass().getSimpleName();
        System.out.println("获取到监听器名称："+name);
        return name;
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println(jobName + " ——任务即将执行 ");
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println(jobName + " ——任务被否决 ");
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println(jobName + " ——执行完毕 ");
    }
}
