package com.gupaoedu.schedule.service;

import javax.core.common.ResultMsg;

/**
 * 日志分析接口
 * @author Tom
 *
 */
public interface ILogService {

	/**
	 * 获取普通的信息
	 * @return
	 */
	public ResultMsg<?> getInfo(String taskId,long seek);
	
	/**
	 * 获取错误信息
	 * @return
	 */
	public ResultMsg<?> getError(String taskId,long seek);
	
	/**
	 * 获取告警信息
	 * @return
	 */
	public ResultMsg<?> getWarn(String taskId);
	
	/**
	 * 获取控制台信息
	 * @return
	 */
	public ResultMsg<?> getConsole(String taskId);
	
}
