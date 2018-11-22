package com.gupaoedu.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: qingshan
 * @Date: 2018/9/18 23:13
 * @Description: 咕泡学院，只为更好的你
 */
public class MyJob2 implements Job {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("任务2执行了： "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    }

}
