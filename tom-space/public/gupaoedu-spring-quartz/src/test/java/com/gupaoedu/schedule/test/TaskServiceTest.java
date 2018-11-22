package com.gupaoedu.schedule.test;

import java.util.List;

import javax.core.common.ResultMsg;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.schedule.entity.Task;
import com.gupaoedu.schedule.service.ITaskService;



@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTest {

	@Autowired ITaskService taskService;
	
	@Test
	@Ignore
	public void testCreate(){
		try {
			ResultMsg<?> msg = taskService.createTask("动态创建的任务", "com.gupaoedu.timer.AnnotationTimer", "a", "0/5 * * * * ?");
			
			String taskId = msg.getData().toString();
			
			System.out.println("创建一个任务，任务池中的任务数量：" + taskService.getAllTask().getData());
			
			Thread.sleep(1000 * 10);
			
			Task task = taskService.getTaskById(taskId).getData();
			
			
			
			
			taskService.pauseTask(taskId);
			
			System.out.println("暂停任务\"" + task.getName() + "\"，任务池中的任务数量：" + taskService.getAllTask().getData());
			
			Thread.sleep(1000 * 10);
			
			
			
			taskService.restartTask(task.getId());
			
			System.out.println("重新启动任务\"" + task.getName() + "\"，任务池中的任务数量：" + taskService.getAllTask().getData());
			
			Thread.sleep(1000 * 10);
			
			
			
			taskService.modifyTaskCron(task.getId(), "0/20 * * * * ?");
			
			System.out.println("修改任务\"" + task.getName() + "\"，任务池中的任务数量：" + taskService.getAllTask().getData());
			
			Thread.sleep(1000 * 30);
			
			
			
			taskService.removeTask(task.getId());
			
			System.out.println("删除任务\"" + task.getName() + "\"，任务池中的任务数量：" + taskService.getAllTask().getData());
			
			Thread.sleep(1000 * 60 * 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
