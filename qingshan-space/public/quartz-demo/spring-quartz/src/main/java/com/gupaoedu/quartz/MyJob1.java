package com.gupaoedu.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 需要执行的任务必须实现 Job接口
 * 如果是MethodInvokingJobDetailFactoryBean方式产生Job，则不用实现Job接口。
 */
public class MyJob1 implements Job{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("任务1执行了： "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
	}

}
