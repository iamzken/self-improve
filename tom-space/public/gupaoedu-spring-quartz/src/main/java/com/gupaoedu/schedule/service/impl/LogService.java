package com.gupaoedu.schedule.service.impl;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.core.common.ResultMsg;
import javax.core.common.config.CustomConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.service.ILogService;
import com.gupaoedu.schedule.service.ITaskService;



/**
 * 日志分析
 * @author Tom
 */
@Service
public class LogService implements ILogService{
	
	private long lastInfoPos = 0; //最后读取的位置
	private long lastConsolePos = 0; //最后读取的位置
	private long lastErrorPos = 0; //最后读取的位置
	private long lastWarnPos = 0; //最后读取的位置
	
	@Autowired private ITaskService taskService;
	
	@Override
	public ResultMsg<?> getInfo(String taskId,long seek) {	
		ResultMsg<Object> result = new ResultMsg<Object>();
		ResultMsg<Task> task = taskService.getTaskById(taskId);
		if(task.getData() == null){ return result; }
		String rootPath = CustomConfig.getString("log.rootPath");
		File file = new File(rootPath + "info.log");
		if(file.exists()){
			try{
				RandomAccessFile randomFile = new RandomAccessFile(file,"r");
				randomFile.seek(seek);		
				List<String> arr = new ArrayList<String>();
				String line = "";	int tempSeekNum = 0;
				while((line = randomFile.readLine()) != null){ 
					tempSeekNum+=line.trim().length();
					if(arr.size()==100) break;
					if(line.indexOf(task.getData().getGroup()) == -1){ continue; }	
					arr.add(new String(line.getBytes("iso8859-1"),"utf-8"));
				}
//				lastInfoPos = file.length();
				randomFile.close();
				JSONObject tempJson = new JSONObject();
				tempJson.put("data",arr);
				tempJson.put("currSeek",arr.size()==0?0:(seek+tempSeekNum));
				result.setData(tempJson);	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public ResultMsg<?> getError(String taskId,long seek) {
		ResultMsg<Object> result = new ResultMsg<Object>();
		ResultMsg<Task> task = taskService.getTaskById(taskId);
		if(task.getData() == null){ return result; }
		String rootPath = CustomConfig.getString("log.rootPath");
		File file = new File(rootPath + "error.log");
		if(file.exists()){
			try{
				RandomAccessFile randomFile = new RandomAccessFile(file, "r");
				randomFile.seek(seek);
				List<String> arr = new ArrayList<String>();
				String line = "";	int tempSeekNum = 0;
				while((line = randomFile.readLine()) != null){
					tempSeekNum+=line.trim().length();
					if(arr.size()==100) break;
					if(line.indexOf(task.getData().getGroup()) == -1){ continue; }
					arr.add(new String(line.getBytes("iso8859-1"),"utf-8"));
				}
//				lastErrorPos = file.length();
				randomFile.close();
				JSONObject tempJson = new JSONObject();
				tempJson.put("data",arr);
				tempJson.put("currSeek",arr.size()==0?0:(seek+tempSeekNum));
				result.setData(tempJson);	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	
	@Override
	public ResultMsg<?> getWarn(String taskId) {
		lastWarnPos = 0;
		System.out.println(lastWarnPos);
		return null;
	}

	
	@Override
	public ResultMsg<?> getConsole(String taskId) {
		ResultMsg<Object> result = new ResultMsg<Object>();
		ResultMsg<Task> task = taskService.getTaskById(taskId);
		if(task.getData() == null){ return result; }
		String rootPath = CustomConfig.getString("log.rootPath");
		File file = new File(rootPath + "out.log");
		if(file.exists()){
			try{
				RandomAccessFile randomFile = new RandomAccessFile(file, "r");
				randomFile.seek(lastConsolePos);
				List<String> arr = new ArrayList<String>();
				String line = "";
				while((line = randomFile.readLine()) != null){
					if(line.indexOf(task.getData().getGroup()) == -1){ continue; }
					arr.add(line);
				}
				lastConsolePos = file.length();
				randomFile.close();
				result.setData(arr);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
