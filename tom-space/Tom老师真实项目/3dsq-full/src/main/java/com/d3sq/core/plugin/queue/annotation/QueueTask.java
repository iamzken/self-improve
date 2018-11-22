package com.d3sq.core.plugin.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在方法执行后加入队列
 *
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface QueueTask {
	/**
	 * 在方法体执行之后加入队列
	 */
	public static final String WHEN_AFTER = "after";
	/**
	 * 在方法体执行之前加入队列
	 */
	public static final String WHEN_BEFORE = "before";
	
	/**
	 * 在何时加入队列
	 * @return
	 */
	public String when() default WHEN_AFTER;
	
}
