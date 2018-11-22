package com.gupaoedu.schedule.mvc.action;

import javax.core.common.ResultMsg;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.core.mvc.action.BaseAction;
import com.gupaoedu.schedule.service.ILogService;

/**
 * 日志输出
 * @author Tom
 */
@Controller
@RequestMapping("/web/logs")
public class LogAction extends BaseAction {
	
	@Autowired ILogService logService;
	
	/**
	 * 获取日志
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getAllLog.json")
	public ModelAndView getAllLog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "taskId") String taskId) {
		ResultMsg<?> infoResult = logService.getInfo(taskId,0);
		ResultMsg<?> errorResult = logService.getError(taskId,0);
		JSONObject json = new JSONObject();		
		json.put("info", infoResult);
		json.put("error", errorResult);		//	System.out.println( taskId + " - " + json.toJSONString() );
		return super.callBackForJsonp(request, response,json.toJSONString());
	}
	
	/**
	 * 获取普通日志
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getInfo.json")
	public ModelAndView getInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "seek") String seek,
			@RequestParam(value = "taskId") String taskId) {	
		ResultMsg<?> result = logService.getInfo(taskId,Long.parseLong(seek));
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 获取错误日志
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getError.json")
	public ModelAndView getError(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "seek") String seek,
			@RequestParam(value = "taskId") String taskId) {
		ResultMsg<?> result = logService.getError(taskId,Long.parseLong(seek));
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 获取告警日志
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getWarn.json")
	public ModelAndView getWarn(HttpServletRequest request, HttpServletResponse response,
			String taskId) {
		ResultMsg<?> result = logService.getWarn(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}
	
	/**
	 * 获取控制台日志
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getConsole.json")
	public ModelAndView getConsole(HttpServletRequest request, HttpServletResponse response,
			String taskId) {
		ResultMsg<?> result = logService.getConsole(taskId);
		return super.callBackForJsonp(request, response, JSON.toJSONString(result));
	}

}
