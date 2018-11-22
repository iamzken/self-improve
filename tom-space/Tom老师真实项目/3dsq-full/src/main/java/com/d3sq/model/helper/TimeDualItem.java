package com.d3sq.model.helper;

/**
 * 时间段定义
 *
 */
public class TimeDualItem {
	
	private String startTime;	//开始时间
	private String endTime;		//结束时间
	private Integer stock;		//库存（提供服务次数）
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	
}
