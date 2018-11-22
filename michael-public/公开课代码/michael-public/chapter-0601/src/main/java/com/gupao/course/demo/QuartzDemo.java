package com.gupao.course.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class QuartzDemo implements Job{

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("type"));
    }

    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler=schedulerFactory.getScheduler();

        JobDetail jobDetail=new JobDetail("myJob","myJobGroup",QuartzDemo.class);
        jobDetail.getJobDataMap().put("type","type");

        Trigger trigger=TriggerUtils.makeWeeklyTrigger(4,21,46);
        trigger.setGroup("myTriggerGroup");
        trigger.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
        trigger.setName("myTrigger");

        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
