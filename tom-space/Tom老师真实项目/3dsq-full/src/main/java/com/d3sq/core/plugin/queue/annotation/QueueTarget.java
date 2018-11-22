package com.d3sq.core.plugin.queue.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记为队列项
 *
 */
@Target(ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface QueueTarget {
	
	/** 查询操作 */
//	public static final int OPT_QUERY = 1; 
	/** 编辑操作 */
	public static final int OPT_EDIT = 1;
	/** 删除操作 */
	public static final int OPT_REMOVE = 2; 
	/** 增加操作 */
	public static final int OPT_ADD = 3; 
	
	/**
	 * 设置ID值
	 * @return
	 */
	public String idField() default "id";
	
	/**
	 * 设置操作类型
	 * @return
	 */
	public int opt() default -1;
	
	/**
	 * 设置别名
	 * @return
	 */
	public String alias() default "";
}
