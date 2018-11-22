package com.gupaoedu.scheduler;

import com.gupaoedu.job.MyJob1;
import com.gupaoedu.listener.MyJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * 测试监听器
 */
public class ListenerTest {
	public static void main(String[] args) throws SchedulerException {

		// JobDetail
		JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

		// Trigger
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

		// SchedulerFactory
		SchedulerFactory  factory = new StdSchedulerFactory();

		// Scheduler
		Scheduler scheduler = factory.getScheduler();

		scheduler.scheduleJob(jobDetail, trigger);

		// 创建并注册一个全局的Job Listener
		scheduler.getListenerManager().addJobListener(new MyJobListener(), EverythingMatcher.allJobs());
		// 创建并注册一个指定任务的Job Listener
		// scheduler.getListenerManager().addJobListener(new MyJobListener(), KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1")));

		// 创建并注册一个全局的Trigger Listener
		//scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener1"), EverythingMatcher.allTriggers());

		// 创建并注册一个局部的Trigger Listener
		//scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener2"), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "gourp1")));

		// 创建并注册一个特定组的Trigger Listener
		//scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener3"), GroupMatcher.groupEquals("gourp1"));

		// 创建Scheduler Listener
		//scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());

		scheduler.start();
		
	}

}
