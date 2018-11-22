package com.gupaoedu.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob1 implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		System.out.println( " " + sf.format(date) + " 任务1执行了，" + dataMap.getString("gupao"));
		
	}

}
