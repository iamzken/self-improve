package com.gupaoedu.vip.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;


  
public class Hotel implements Serializable {  
    private ObjectId id; 	
      
    private String hotelName;	
      
    private String hotelAddress;	
    private int price;
    private Date createTime;
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
    
    
}
