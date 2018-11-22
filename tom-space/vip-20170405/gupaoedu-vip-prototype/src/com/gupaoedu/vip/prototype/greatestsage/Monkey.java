package com.gupaoedu.vip.prototype.greatestsage;

import java.util.Date;


//猴子
public class Monkey {
	//身高
	protected int height;//基本
	//体重
	protected int weight;
	//生日
	protected Date birthday;//不是基本类型
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	
	
}
