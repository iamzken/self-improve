package com.gupaoedu.schedule.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.core.common.Page;
import javax.core.common.ResultMsg;
import javax.core.common.annotation.Comment;
import javax.core.common.utils.ListUtils;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.common.constants.CronExpression;
import com.gupaoedu.common.constants.SystemConstant;
import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.proxy.TriggerProxy;
import com.gupaoedu.schedule.service.ITaskService;


/**
 * 任务管理，负责输出和管理日志
 * @author Tom
 */
@Service
public class TaskService implements ITaskService,ApplicationContextAware{
	
	@Autowired private SchedulerFactoryBean schedulerFactory;
	
	//保存所有任务表达式的描述
	private Map<String,String> cronDesc = new LinkedHashMap<String,String>(); 
	
	private List<Method> triggerList = new ArrayList<Method>();
	
	//保存所有的任务列表
	private static Map<String,Task> allTask = new LinkedHashMap<String,Task>();
	
	public static Map<String, Task> getRecordMap() {
		return recordMap;
	}
	
	Random random = new Random();
	Scheduler sched ;

	private static Map<String,Task> recordMap = new HashMap<String,Task>();
	
	private static ApplicationContext app;
	
	
	//匹配定时任务的触发器语法规则
	private final String regex = "(.+)\\((.*)\\)";
	
	//预定义java中八大基本数据类型 
	private static Map<String,Class<?>> classTypes = new HashMap<String,Class<?>>(){{
		put("boolean", boolean.class);
		put("byte", byte.class);
		put("short", short.class);
		put("int", int.class);
		put("long", long.class);
		put("float", float.class);
		put("double", double.class);
		put("char", char.class);
		
		put("java.lang.Boolean", Boolean.class);
		put("java.lang.Byte", Byte.class);
		put("java.lang.Short", Short.class);
		put("java.lang.Integer", Integer.class);
		put("java.lang.Long", Long.class);
		put("java.lang.Float", Float.class);
		put("java.lang.Double", Double.class);
		put("java.lang.Character", Character.class);
	}};
	
	public TaskService(){
		try{	
			Field [] fields = CronExpression.class.getFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Comment.class)){
					Annotation a = field.getAnnotation(Comment.class);
					if(null != a){
						Method m = a.getClass().getMethod("value",null);
						String desc = m.invoke(a, null).toString();
						cronDesc.put(field.get(CronExpression.class).toString(), desc);
					}
				}
			}
		}catch(Exception e){	e.printStackTrace();	}
	}
	
	/**
	 * 创建一个调度任务
	 * @param m
	 * @return
	 * @throws Exception
	 */
	private Task createTask(Method m,String autoCron) throws Exception{
		Task task = new Task("" + (System.currentTimeMillis()+random.nextInt(100000000)) );
		Class clazz = m.getDeclaringClass();
		
		//设置任务组
		task.setGroup(clazz.getName());
		if(clazz.isAnnotationPresent(Comment.class)){
			Annotation taskAnn = clazz.getAnnotation(Comment.class);
			Method taskAnnM = taskAnn.getClass().getMethod("value",null);
			String groupDesc = taskAnnM.invoke(taskAnn, null).toString();
			task.setGroupDesc(groupDesc);
		}
		
		List<String> params = new ArrayList<String>();
		for (Class<?> param : m.getParameterTypes()) {
			params.add(param.getName());
		}
		
		//设置触发器信息
		task.setTrigger(clazz.getName() + "." + m.getName() + (params.size() == 0 ? "()" : ("(" +ListUtils.join(params, ",") + ")")));
		
		if(!"".equals(autoCron)){
			task.setTriggerDesc("自定义cron,暂无描述!");
			task.setCron(autoCron);
			task.setCronDesc("自定义cron,暂无描述!");
		}else if(m.isAnnotationPresent(Scheduled.class)){
			Annotation sc = m.getAnnotation(Scheduled.class);
			Method cronM = sc.getClass().getMethod("cron",null);
			String cron = cronM.invoke(sc, null).toString();
			task.setTriggerDesc(cronDesc.get(cron));
			task.setCron(cron);
			task.setCronDesc(cronDesc.get(cron));
		}
		
		//设置任务表述
		if(m.isAnnotationPresent(Comment.class)){
			Annotation mAnn = m.getAnnotation(Comment.class);
			Method mAnnM = mAnn.getClass().getMethod("value",null);
			String desc = mAnnM.invoke(mAnn, null).toString();
			task.setDesc(desc);
		}
		return task;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext app) throws BeansException {
		sched = schedulerFactory.getScheduler();
		this.app = app;
		//加载所有任务到任务队列
 		for(String name : this.app.getBeanDefinitionNames()){
			try{		
				Class<?> c = this.app.getBean(name).getClass();
				for(Method m : c.getMethods()){		
					if(m.isAnnotationPresent(Scheduled.class)){	
						
						Scheduled sc = m.getAnnotation(Scheduled.class);
						triggerList.add(m);		
						Task task = createTask(m,sc.cron());
						addToList(task,null);	//将任务加入队列准备启动
					}
				}
			}catch(Exception e){
				continue;
			}
		}	
	}
	
	public ResultMsg<Task> getTaskById(String taskId) {
		return new ResultMsg<Task>(SystemConstant.RESULT_STATUS_SUCCESS, "", allTask.get(taskId));
	}
	
	@Override
	public ResultMsg<?> getAllTask() {
		ResultMsg<Object> result = new ResultMsg<Object>();
		if(allTask.size() == 0){
			result.setMsg("当前还没有任务在执行");
			return result;
		}
		result.setStatus(SystemConstant.RESULT_STATUS_SUCCESS);
		result.setData(allTask.values());
		return result;
	}
	
	/**
	 * 获取所有的表达式
	 */
	public ResultMsg<?> getAllCron(){
		ResultMsg<Object> result = new ResultMsg<Object>();
		if(cronDesc.size() == 0){
			result.setMsg("当前没找到表达式");
			return result;
		}
		result.setStatus(SystemConstant.RESULT_STATUS_SUCCESS);
		result.setData(cronDesc);
		return result;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public ResultMsg<?> getTaskForPage(int pageNo,int pageSize){
		ResultMsg<Object> result = new ResultMsg<Object>();
		if(allTask.size() == 0){
			result.setStatus(SystemConstant.RESULT_STATUS_ERROR);
			result.setMsg("当前还没有任务在执行");
			return result;
		}
		List<Task> list = new ArrayList<Task>();
		list.addAll(allTask.values());
		List<Task> rows = ListUtils.pagination(list, pageNo, pageSize);
		int start = Page.getStartOfPage(pageNo, pageSize);
		Page<Task> page = new Page<Task>(start, allTask.size(), pageSize, rows);
		result.setStatus(SystemConstant.RESULT_STATUS_SUCCESS);
		result.setData(page);		
		return result;
	}
	
	
	private ResultMsg<?> addToList(Task task,List<Object> params){
		try{
			if(null == task.getGroup() || task.getGroup().trim().length() == 0){ return null; }
			
			Class<?> clazz = Class.forName(task.getGroup());
			//先从容器中获取
			Object target = null;
			try{
				target = app.getBean(clazz);
			}catch(Exception e){	e.printStackTrace();	}
			//如果没有，自己创建实例
			if(target == null){
				target = clazz.newInstance();
			}
			
			String triggerName = task.getTrigger().replaceAll(task.getGroup() + ".", "");
			String methodName = triggerName;	
			List<Class<?>> ps = new ArrayList<Class<?>>();
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(triggerName);
			while(matcher.find()){
				methodName = matcher.group(1);
				String paramStr = matcher.group(2);
				String [] paramNames = paramStr.split(",");
				for (String param : paramNames) {
					if(param.equals("")){continue;}
					if(classTypes.containsKey(param)){
						ps.add(classTypes.get(param));
					}else{
						ps.add(Class.forName(param));
					}
				}
			}
			
			Class<?> [] param = new Class<?>[ps.size()];
			Method m = clazz.getMethod(methodName,ps.toArray(param));
			
			String taskId = task.getId();	
			
			Object[] proxyParams = null ;
			
	        JobDetail taskDetail = new JobDetail(taskId, task.getGroup(), TriggerProxy.class);// 任务名，任务组，任务执行类  
	        // 触发器  
	        CronTrigger trigger = new CronTrigger(taskId, task.getTrigger());// 触发器名,触发器组 
	        trigger.setCronExpression(task.getCron());	// 触发器时间设定  
	        trigger.getJobDataMap().put(TriggerProxy.DATA_TARGET_KEY, target);
	        trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_KEY, m);
	        trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_KEY, proxyParams);
	        trigger.getJobDataMap().put(TriggerProxy.DATA_TASK_KEY, task);
	        
	        sched.scheduleJob(taskDetail, trigger);
	        // 启动  
	        if (!sched.isShutdown()) {  
	            sched.start();  
	        }
	        if(!allTask.containsKey(taskId)){
				allTask.put(taskId, task);
				recordMap.put(taskId, task);
			}
	        return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "", task.getId());
		}catch(Exception e){
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加失败");
		}
	}
	
	/** 
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     */  
    public ResultMsg<?> createTask(String taskName,String taskClassName,String triggerName,String cron){
    	return createTask(taskName,null,taskClassName,null,triggerName,cron);
    }
  
    
    /** 
     * 添加一个定时任务 
     */  
    public ResultMsg<?> createTask(String taskName, String taskGroupName, String taskClassName,String triggerGroupName, String triggerName,String cron){  
    	try{
	    	Class<?> clazz = Class.forName(taskClassName);
	    	Method m = clazz.getMethod(triggerName);
	    	Task task = createTask(m,null);
	    	task.setName(taskName);
	    	if(null != taskGroupName){
	    		task.setGroup(taskGroupName);
	    	}
	    	task.setCron(cron);
	    	return addToList(task,null);
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "创建失败");
    	}
    }
  
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public ResultMsg<?> modifyTaskCron(String taskId, String cron) {  
    	
    	Task task = allTask.get(taskId);
        try {
        	
            Scheduler sched = schedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(taskId,task.getTrigger());  
            if (trigger == null) {  
                return new ResultMsg<Object>();  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) {  
                
                removeTask(taskId);
                
                //重新生成ID
                task.setId("" + System.currentTimeMillis());
                task.setCron(cron);
                addToList(task,null);
                
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
        return new ResultMsg<Task>(SystemConstant.RESULT_STATUS_SUCCESS, "",task);
    }
    
    
    public ResultMsg<?> createTask(Task task,String type,String paramArray){
    	try{
			if(null == task.getGroup() || task.getGroup().trim().length() == 0){ return null; }
			Class<?> clazz = Class.forName(task.getGroup());
	//		//先从容器中获取
			Object target = null;	
			try{	
				target = app.getBean(clazz);
			}catch(Exception e){	e.printStackTrace();	}
			if(target == null){		//	如果没有，自己创建实例
				target = clazz.newInstance();
			}
			String triggerName = task.getTrigger().replaceAll(task.getGroup() + ".", "");
			String methodName = triggerName;	
			List<Class<?>> params = new ArrayList<Class<?>>();
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(triggerName);
			while(matcher.find()){
				methodName = matcher.group(1);
				String paramStr = matcher.group(2);
				String [] paramNames = paramStr.split(",");
				for (String param : paramNames) {
					if(param.equals("")){continue;}
					if(classTypes.containsKey(param)){
						params.add(classTypes.get(param));
					}else{
						params.add(Class.forName(param));
					}
				}
			}
			
	
			Class<?> [] param = new Class<?>[params.size()];	
			Method m = clazz.getMethod(methodName,params.toArray(param));
			Object[] proxyParams = null ;
	
			String taskId = ("" + System.currentTimeMillis());
			task.setExecute(0);
			
	        JobDetail taskDetail = new JobDetail(taskId, task.getGroup(), TriggerProxy.class);// 任务名，任务组，任务执行类  
	        if("simple".equals(type)){	// simple 触发器  
	        		SimpleTrigger simpleTrigger = new SimpleTrigger(taskId,task.getTrigger());  
	            	simpleTrigger.setRepeatInterval(2000);			
	                simpleTrigger.setRepeatCount(task.getPlanExe()==0?SimpleTrigger.REPEAT_INDEFINITELY:task.getPlanExe());
	                simpleTrigger.getJobDataMap().put(TriggerProxy.DATA_TARGET_KEY, target);
	                simpleTrigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_KEY, m);
	                simpleTrigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_KEY,proxyParams );
	                simpleTrigger.getJobDataMap().put(TriggerProxy.DATA_TASK_KEY, task);
	                
	                sched.scheduleJob(taskDetail, simpleTrigger);
	        }else{	// cron 触发器  
	        		CronTrigger trigger = new CronTrigger(taskId, task.getTrigger());// 触发器名,触发器组 
	                trigger.setCronExpression(task.getCron());// 触发器时间设定  
	                trigger.getJobDataMap().put(TriggerProxy.DATA_TARGET_KEY, target);	
	                trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_KEY, m);
	                trigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_KEY,proxyParams );	//	new Object[]{}
	                trigger.getJobDataMap().put(TriggerProxy.DATA_TASK_KEY, task);
	                
	                sched.scheduleJob(taskDetail, trigger);
	        }	
	        if (!sched.isShutdown()) {  	// 启动  
	            sched.start();  
	        }	
	        if(!allTask.containsKey(taskId)){
				allTask.put(taskId, task);
			}
	        return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "", task.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    		return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "创建失败");
    	}
	}
    
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     */  
    public ResultMsg<?> removeTask(String taskId) {  
    	JSONObject json = new JSONObject();
    	json.put("taskId",taskId);
        try {
        	Task task = allTask.get(taskId);
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.pauseTrigger(taskId, task.getTrigger());// 停止触发器  
            sched.unscheduleJob(taskId, task.getGroup());// 移除触发器  
            sched.deleteJob(taskId, task.getGroup());// 删除任务 
            task.setState(2);
            allTask.remove(taskId);		
            json.put("flag",true);
            json.put("msg","移除任务成功");		
        } catch (Exception e) {  
        	json.put("flag",false);
            json.put("msg","移除任务失败-"+e.getMessage());
            throw new RuntimeException(e);  
        }
        return getResult(json);
    }
  
    
    /**
     * 暂停任务
     * @param taskId
     */
    public ResultMsg<?> pauseTask(String taskId){
    	JSONObject json = new JSONObject();
    	json.put("taskId",taskId);
    	try {
        	Task task = allTask.get(taskId);
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.pauseTrigger(task.getId(),task.getTrigger());// 停止触发器  
            task.setState(3);
            json.put("flag",true);
            json.put("msg","暂停任务成功");		
        } catch (Exception e) {  
            json.put("flag",false);
            json.put("msg","暂停任务失败-"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);  
        }
    	return getResult(json);
    }
    
    /** 
     * 重启任务
     * @param taskId 
     */
    public ResultMsg<?> restartTask(String taskId) {
    	JSONObject json = new JSONObject();
    	json.put("taskId",taskId);
		try {
			Task task = allTask.get(taskId);
			Scheduler sched = schedulerFactory.getScheduler();  
			sched.resumeTrigger(task.getId(),task.getTrigger());	// 重启触发器  
            task.setState(1);
			json.put("flag",true);
            json.put("msg","启动任务成功");		
		} catch (Exception e) {  
			json.put("flag",false);
            json.put("msg","启动任务失败-"+e.getMessage());
            e.printStackTrace();
			throw new RuntimeException(e);  
		} 
		return getResult(json);
    }
    
    
    /**
     * 关闭任务
     * @param taskId
     */
    public ResultMsg<?> shutdownTask(String taskId){
    	 try {
         	Task task = allTask.get(taskId);
         	
             Scheduler sched = schedulerFactory.getScheduler();  
             sched.pauseTrigger(taskId, task.getTrigger());// 停止触发器  
             sched.unscheduleJob(taskId, task.getGroup());// 移除触发器  
             sched.deleteJob(taskId, task.getGroup());// 删除任务 
             
         } catch (Exception e) {  
             throw new RuntimeException(e);  
         }
    	 return new ResultMsg<String>(SystemConstant.RESULT_STATUS_SUCCESS, "",taskId);
    }
    
    
    /**
     * 禁用任务
     * @param taskId
     */
    public ResultMsg<?> disableTask(String taskId){
    	JSONObject json = new JSONObject();
    	json.put("taskId",taskId);
    	try {
          	Task task = allTask.get(taskId);
          	Scheduler sched = schedulerFactory.getScheduler();  
            sched.pauseTrigger(taskId, task.getTrigger());// 停止触发器  
            sched.unscheduleJob(taskId, task.getGroup());// 移除触发器  
            task.setState(0);
            json.put("flag",true);
            json.put("msg","禁用任务成功");		  
        }catch (Exception e) {  
        	json.put("flag",false);
            json.put("msg","禁用任务失败-"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);  
        }
    	return getResult(json);
    }
    
    /** 
     * 重启所有定时任务 
     */
    public ResultMsg<?> restartAllTask(){
    	Scheduler sched = schedulerFactory.getScheduler();  
    	JSONObject json = new JSONObject();
    		try {
    			for(Map.Entry<String, Task> taskMap:allTask.entrySet()){
    				sched.pauseTrigger(taskMap.getKey(),taskMap.getValue().getTrigger());	// 停止触发器  
    				sched.unscheduleJob(taskMap.getKey(),taskMap.getValue().getGroup());	// 移除触发器  
    				sched.deleteJob(taskMap.getKey(),taskMap.getValue().getGroup());		// 删除任务 
    			}			
    			allTask.clear();	
    			recordMap.clear();
    			for(Method m : triggerList){
					if(m.isAnnotationPresent(Scheduled.class)){
						try {
							Task task = createTask(m,null);
							addToList(task,null);	//将任务加入队列准备启动
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
    	    	json.put("flag",true);
                json.put("msg","重启所有定时任务成功");		
			} catch (SchedulerException e) {
				json.put("flag",false);
	            json.put("msg","重启所有定时任务失败-"+e.getMessage());
	            e.printStackTrace();
	            throw new RuntimeException(e);  
			}
    	return getResult(json);
    }
  
    /** 
     * 启动所有定时任务 
     */
    public ResultMsg<?> startAllTask() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
        return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "");
    }
  
    /** 
     * 关闭所有定时任务 
     */  
    public ResultMsg<?> shutdownAllTask() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        } 
        return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "");
    }
    
    public ResultMsg<?> getResult(JSONObject json){
    	if(json.getBooleanValue("flag")){
    		return new ResultMsg<String>(SystemConstant.RESULT_STATUS_SUCCESS, "",json.toString());
    	}else{
    		return new ResultMsg<String>(SystemConstant.RESULT_STATUS_ERROR, "",json.toString());
    	}
    }
    

}
