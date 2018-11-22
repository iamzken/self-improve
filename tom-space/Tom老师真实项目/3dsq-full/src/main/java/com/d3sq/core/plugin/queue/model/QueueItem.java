package com.d3sq.core.plugin.queue.model;

import java.io.Serializable;

/**
 * 队列项
 *
 */
public class QueueItem{
	private Object processor;	//执行目标
	private String alias;		//任务别名
	private int opt;			//操作类型
	private boolean optResult;	//返回结果
	private Serializable id;	//id值
	private Object data;		//数据内容
	
	/**
	 * 新建一个队列数据项
	 * @param alias 自定义别名
	 * @param opt	操作类型
	 * @param data	操作数据
	 */
	public QueueItem(Object processor,String alias,int opt,Serializable id,Object data){
		this.processor = processor;
		this.id = id;
		this.alias = alias;
		this.opt = opt;
		this.data = data;
	}

	public int getOpt() {
		return opt;
	}

	public QueueItem setOpt(int opt) {
		this.opt = opt;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public QueueItem setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public Object getData() {
		return data;
	}

	public QueueItem setData(Object data) {
		this.data = data;
		return this;
	}

	public Serializable getId() {
		return id;
	}

	public QueueItem setId(Serializable id) {
		this.id = id;
		return this;
	}

	public boolean getOptResult() {
		return optResult;
	}

	public QueueItem setOptResult(boolean optResult) {
		this.optResult = optResult;
		return this;
	}

	public Object getProcessor() {
		return processor;
	}

	public void setProcessor(Object processor) {
		this.processor = processor;
	}
	
}
