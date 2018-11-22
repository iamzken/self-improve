package com.d3sq.center.vo;

import java.util.List;
import java.util.Map;


/**
 * 搜索输出vo对象
 *
 */
public class ESVo {
	private Long total;//记录总数
	private Float useTime;//搜索花费时间
	private String distance;//距离(m)
	
	private List<Map<String, Object>> data;//数据集合

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Float getUseTime() {
		return useTime;
	}

	public void setUseTime(Float useTime) {
		this.useTime = useTime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	
}
