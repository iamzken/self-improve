package com.gupaoedu.vip.prototype.simple;

import java.util.ArrayList;
import java.util.List;

public class CloneTest {

	
	
	public static void main(String[] args) {
		
		ConcretePrototype cp = new ConcretePrototype();
		cp.setAge(18);
		cp.setName("Tom");
		
		//cp.list.add("Tom");
		
		try {
			ConcretePrototype copy = (ConcretePrototype)cp.clone();
			
			System.out.println(copy.list  == cp.list);
			System.out.println(copy.getAge() + "," + copy.getName() + copy.list.size());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//就是一个现成的对象，这个对象里面有已经设置好的值
		//当我要新建一个对象，并且要给新建的对象赋值，而且赋值内容要跟之前的一模一样
		
		
		//ConcretePrototype cp = new ConcretePrototype();
		//cp.setAge(18);
		
		//ConcretePrototype copy = new ConcretePrototype();
		//copy.setAge(cp.getAge());
		//copy.setName(cp.getName());
		//用循环，用反射，确实可以的（反射性能并不高）
		//字节码复制newInstance()
		
		//ConcretePrototype copy = cp;
		//ORM的时候经常用到的
		
		
		//能够直接拷贝其实际内容的数据类型/只支持9种,八大基本数据类型+String 浅拷贝
		//深拷贝
	}
	
}
