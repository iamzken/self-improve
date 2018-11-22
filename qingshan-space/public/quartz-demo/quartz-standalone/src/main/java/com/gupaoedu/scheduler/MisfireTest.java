package com.gupaoedu.scheduler;

import com.gupaoedu.job.MyJob2;
import com.gupaoedu.job.MyJob3;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 用来测试任务Misfire的情况，线程池不够用
 */
public class MisfireTest {
	public static void main(String[] args) throws SchedulerException {
/*
		// JobDetail1
		JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("job1", "group1").build();

		// Trigger1
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().
						withMisfireHandlingInstructionNowWithExistingCount().
						withIntervalInSeconds(1).
						repeatForever()).build();

		// JobDetail 2
		JobDetail jobDetail2 = JobBuilder.newJob(MyJob3.class).withIdentity("job2", "group1").build();

		// Trigger 2  占用掉5个线程，只占用一次
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().
						withIntervalInMilliseconds(1).
						withRepeatCount(5)).build();

		// SchedulerFactory
		SchedulerFactory  factory = new StdSchedulerFactory();

		// Scheduler
		Scheduler scheduler = factory.getScheduler();

		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.scheduleJob(jobDetail2, trigger2);
		scheduler.start();*/
		
	}

}
