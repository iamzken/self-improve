package com.gupaoedu.vip.prototype.greatestsage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 齐天大圣
 * @author Tom
 *
 */
public class TheGreatestSage  extends Monkey implements Cloneable,Serializable{
	
	//金箍棒
	private GoldRingedStaff staff;
	
	//从石头缝里蹦出来
	public TheGreatestSage(){
		this.staff = new GoldRingedStaff();
		this.birthday = new Date();
		this.height = 150;
		this.weight = 30;
		System.out.println("------------------------");
	}
	
	//分身技能
	public Object clone(){
		//深度克隆
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			//return super.clone();//默认浅克隆，只克隆八大基本数据类型和String
			//序列化
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			
			//反序列化
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			TheGreatestSage copy = (TheGreatestSage)ois.readObject();
			copy.birthday = new Date();
			
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//变化
	public void change(){
		TheGreatestSage copySage = (TheGreatestSage)clone();
		System.out.println("大圣本尊生日是：" + this.getBirthday().getTime());
		System.out.println("克隆大圣的生日是:" + copySage.getBirthday().getTime());
		System.out.println("大圣本尊和克隆大圣是否为同一个对象:" + (this == copySage));
		System.out.println("大圣本尊持有的金箍棒跟克隆大圣持有金箍棒是否为同一个对象:" + (this.getStaff() == copySage.getStaff()));
	}
	
	public GoldRingedStaff getStaff() {
		return staff;
	}

	public void setStaff(GoldRingedStaff staff) {
		this.staff = staff;
	}
	
	
	
	
}
