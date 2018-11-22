package com.gupaoedu.schedule.proxy;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.gupaoedu.schedule.entity.Task;

/**
 * 
 * @author Tom
 *
 */
public class TriggerProxy implements Job{
	
	public static final String DATA_TARGET_KEY = "target";
	public static final String DATA_TRIGGER_KEY = "trigger";
	public static final String DATA_TRIGGER_PARAMS_KEY = "trigger_params";
	public static final String DATA_TASK_KEY = "task";
	
	private ThreadLocal<Entry> local = new ThreadLocal<Entry>();
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			local.set(new Entry());
			//获取参数信息
			JobDataMap data = context.getTrigger().getJobDataMap();
			Object target = data.get(DATA_TARGET_KEY);
			Method method = (Method)data.get(DATA_TRIGGER_KEY);
			Object[] params = (Object[])data.get(DATA_TRIGGER_PARAMS_KEY);
			
			//修改任务执行次数
			Task task = (Task)data.get(DATA_TASK_KEY);
			task.setExecute(task.getExecute() + 1);
			
			local.get().start = System.currentTimeMillis();
			
			//调用触发器
			method.invoke(target,params);
			
			local.get().end = System.currentTimeMillis();
			
			task.setLastExeTime(local.get().start);
			task.setLastFinishTime(local.get().end);
			
			
			if(task.getPlanExe()!=0){
				if(task.getExecute()==task.getPlanExe()){
					try {
						context.getScheduler().pauseTrigger(task.getId(),task.getTrigger());	// 停止触发器  
						context.getScheduler().unscheduleJob(task.getId(),task.getGroup());		// 移除触发器  
						context.getScheduler().deleteJob(task.getId(),task.getGroup());			// 删除任务 
						task.setState(4);
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	class Entry{
		public long start = 0L;
		public long end = 0L;
	}
	
}
