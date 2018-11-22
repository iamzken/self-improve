package com.gupaoedu.schedule.service;

import javax.core.common.ResultMsg;

import com.gupaoedu.schedule.entity.Task;


public interface ITaskService {
	
	/**
	 * 获取所有的表达式
	 * @return
	 */
	public ResultMsg<?> getAllCron();

	/**
	 * 获取任务列表
	 * @return
	 */
	public ResultMsg<?> getAllTask();
	
	/**
	 * 分页查询
	 * @return
	 */
	public ResultMsg<?> getTaskForPage(int pageNo,int pageSize);
	
	/**
	 * 根据任务ID获取一个任务
	 * @return
	 */
	public ResultMsg<Task> getTaskById(String taskId);
	
	/**
	 * 新建一个任务
	 * @param taskName 任务名称
	 * @param taskClassName	任务Class名称
	 * @param triggerName 触发器名称
	 * @param cron	执行表达式
	 * @throws Exception
	 */
	public ResultMsg<?> createTask(String taskName,String taskClassName,String triggerName,String cron);
  
	
	public ResultMsg<?> createTask(Task task,String type,String paramArray);
	
    /**
     * 新建一个任务
     * @param taskName 任务名称
     * @param taskGroupName 任务组名
     * @param taskClassName 任务类名
     * @param triggerGroupName 触发器组名
     * @param triggerName 触发器方法名
     * @param cron 执行表达式
     * @throws Exception
     */
    public ResultMsg<?> createTask(String taskName, String taskGroupName, String taskClassName,String triggerGroupName, String triggerName,String cron);
  
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public ResultMsg<?> modifyTaskCron(String taskId, String cron);
  
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     *  
     */  
    public ResultMsg<?> removeTask(String taskId);
  
    /**
     * 重启任务
     * @param taskId
     * @return
     */
    public ResultMsg<?> restartTask(String taskId);
    
    /**
     * 暂停定时任务
     * @param taskId
     * @return
     */
    public ResultMsg<?> pauseTask(String taskId);
    
    /**
     * 禁用
     * @param taskId
     * @return
     */
    public ResultMsg<?> disableTask(String taskId);
    
    /**
     * 关闭定时任务
     * @param taskId
     * @return
     */
    public ResultMsg<?> shutdownTask(String taskId);
    
    
    /** 
     * 启动所有定时任务 
     *  
     */
    public ResultMsg<?> startAllTask();
  
    /** 
     * 关闭所有定时任务 
     */  
    public ResultMsg<?> shutdownAllTask();
    
    /** 
     * 重启所有定时任务 
     */
    public ResultMsg<?> restartAllTask();
	
}
