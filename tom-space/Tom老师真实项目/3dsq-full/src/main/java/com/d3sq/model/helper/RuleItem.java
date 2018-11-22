package com.d3sq.model.helper;

/**
 * 规则
 */
public class RuleItem {
	
	private String id;			//ID
	private String parentId;	//父级ID
	private String title;		//标题
	private Float price;		//价格
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
}
