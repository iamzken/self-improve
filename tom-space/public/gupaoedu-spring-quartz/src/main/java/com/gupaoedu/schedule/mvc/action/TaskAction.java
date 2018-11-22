package com.gupaoedu.schedule.mvc.action;

import javax.core.common.ResultMsg;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.core.mvc.action.BaseAction;
import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.service.ITaskService;
import com.gupaoedu.schedule.service.impl.TaskService;


/**
 * 任务管理，可动态新建、修改、和删除任务
 * @author Tom
 */
@Controller
@RequestMapping("/web/task")
public class TaskAction extends BaseAction{

	@Autowired ITaskService taskService;
	
	/**
	 * 获取任务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAll.json")
	public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response){
		ResultMsg<?> result = taskService.getAllTask();
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 获取任务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getPage.json")
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNo", defaultValue = "1",required=false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10",required=false) int pageSize){
		ResultMsg<?> result = taskService.getTaskForPage(pageNo, pageSize);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 删除任务
	 * @param request
	 * @param response
	 * @param taskId
	 * @return
	 */
	@RequestMapping("remove.json")
	public ModelAndView remove(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId){
		ResultMsg<?> result = taskService.removeTask(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 修改任务
	 * @param request
	 * @param response
	 * @param task
	 * @return
	 */
	@RequestMapping("modify.json")
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response,
		   @ModelAttribute Task task){
		ResultMsg<?> result = taskService.modifyTaskCron(task.getId(),task.getCron());
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 重启任务
	 * @param request
	 * @param response
	 * @param taskId
	 * @return
	 */
	@RequestMapping("restart.json")
	public ModelAndView restart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId){
		ResultMsg<?> result = taskService.restartTask(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 创建任务 , 根据type判断是临时还是永久
	 */
	@RequestMapping("add.json")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,	
			@RequestParam(value = "task") String task,
			@RequestParam(value = "paramsArray") String paramsArray,
			@RequestParam(value = "type") String type){
			
			Task t = JSON.parseObject(task,Task.class);
			ResultMsg<?> result = taskService.createTask(t,type,paramsArray);
			return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	/**
	 * 暂停
	 * @param request @param response @param taskId
	 * @return
	 */
	@RequestMapping("pause.json")
	public ModelAndView pause(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId){
		ResultMsg<?> result = taskService.pauseTask(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 停止任务
	 * @param request
	 * @param response
	 * @param taskId
	 * @return
	 */
	@RequestMapping("shutdown.json")
	public ModelAndView shutdown(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId){
		ResultMsg<?> result = taskService.shutdownTask(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 禁用任务
	 * @param request
	 * @param response
	 * @param taskId
	 * @return
	 */
	@RequestMapping("disable.json")
	public ModelAndView disable(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId){
		ResultMsg<?> result = taskService.disableTask(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 重启所有任务
	 * @param request
	 * @param response
	 * @param taskId
	 * @return
	 */
	@RequestMapping("restartAll.json")
	public ModelAndView restartAll(HttpServletRequest request, HttpServletResponse response){
		ResultMsg<?> result = taskService.restartAllTask();
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 获取所有的表达式
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getCrons.json")
	public ModelAndView getCrons(HttpServletRequest request, HttpServletResponse response){
		ResultMsg<?> result = taskService.getAllCron();
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	@RequestMapping("getRecordMap.json")
	public ModelAndView getRecordMap(HttpServletRequest request, HttpServletResponse response){
		return super.callBackForJsonp(request, response, JSON.toJSONString(TaskService.getRecordMap()));
	}
	
}
