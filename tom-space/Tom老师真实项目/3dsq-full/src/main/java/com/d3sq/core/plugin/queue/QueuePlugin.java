package com.d3sq.core.plugin.queue;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.aspectj.lang.JoinPoint;

import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;

/**
 * 队列处理插件，单例对象
 *
 */
public abstract class QueuePlugin{

	//保存队列信息
	private static BlockingQueue<QueueItem> queue = null;
	//用于控制服务的启动、停止、重启
	private static boolean isRunning = false;
	//保存当前执行任务的线程
	private static Thread currThread;
	
	private static Map<Object,Method> processorCache;
	
	public QueuePlugin(){
		if(queue == null){
			queue = new LinkedBlockingQueue<QueueItem>();
		}
		processorCache = new HashMap<Object,Method>();
		start();
	}
	
	/**
	 * 切点执行后，执行此方法，加入自动创建索引队列
	 * @param point
	 */
	public synchronized void execute(JoinPoint point,Object retValue){
		
		String method = point.getSignature().getName();
		
		Method[] ms = point.getSignature().getDeclaringType().getMethods();
		for (Method m : ms) {
			if(!m.getName().equals(method)){ continue;}
			try {
				Annotation[][] aa = m.getParameterAnnotations();
				int i = 0;
				for (Annotation[] an : aa) {
					for (Annotation a : an) {
						if(a instanceof QueueTarget){
							
							//获取注解中设定的值
							String alias = a.getClass().getDeclaredMethod("alias", null).invoke(a).toString();
							int opt = Integer.valueOf(a.getClass().getDeclaredMethod("opt", null).invoke(a).toString());
							String idField = a.getClass().getDeclaredMethod("idField", null).invoke(a).toString();
							
							//分析id字段的值
							Object value = point.getArgs()[i];
							Field f = value.getClass().getDeclaredField(idField);
							f.setAccessible(true);
							Serializable id = (Serializable)f.get(value);
							
							//分析返回值
							QueueItem item = new QueueItem(point.getThis(),alias, opt, id, point.getArgs()[i]);
							if(retValue == null || retValue.getClass() == void.class){
								item.setOptResult(true);
							}else if(retValue instanceof Boolean || retValue.getClass() == boolean.class){
								item.setOptResult(Boolean.valueOf(retValue.toString()));
							}else if(retValue instanceof Long || retValue.getClass() == long.class ||
									retValue instanceof Integer || retValue.getClass() == int.class){
								Long r = Long.valueOf(retValue.toString());
								item.setOptResult(r > 0);
							}else{
								item.setOptResult(true);
							}
							
							//加入处理队列
							push(item);
						}
					}
					i ++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 逻辑处理
	 * @param item
	 */
	protected abstract void process(QueueItem item);
	
	/**
	 * 往队列中添加任务
	 */
	public synchronized void push(QueueItem item) {
		if(item == null){return;}
		
		if(item.getData() == null){ return; }
		
		try {
			queue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重启队列服务
	 */
	public void restart(){
		if(currThread == null){ return; }
		
		if(currThread.interrupted()){
			currThread.stop();
			currThread.destroy();
			currThread = null;
		}
		isRunning = false;
		start();
	}
	
	/**
	 * 启动队列服务
	 */
	public void start(){
		if(isRunning){ return;}
		isRunning = true;
		currThread = new Thread(){
			public void run(){
				while(isRunning){
					if(queue.size() == 0){ continue; }
					try {
						QueueItem item = queue.take();
						if(null != item.getProcessor()){
							if(!processorCache.containsKey(item.getProcessor())){
								Method m = item.getProcessor().getClass().getDeclaredMethod("process", new Class[]{QueueItem.class});
								m.setAccessible(true);
								processorCache.put(item.getProcessor(), m);
							}
							processorCache.get(item.getProcessor()).invoke(item.getProcessor(),item);
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
		};
		currThread.start();
	}
	
	/**
	 * 停止队列服务
	 */
	public void stop(){
		isRunning = false;
	}
	
}
