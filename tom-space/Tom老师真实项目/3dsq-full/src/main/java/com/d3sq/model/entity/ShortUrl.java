package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短链接实体类定义
 *
 */
@Entity
@Table(name="t_short_url")
public class ShortUrl implements Serializable{
	//短Url
	private String surl;
	//全url
	private String url;
	
	public ShortUrl(){}
	
	public ShortUrl(String surl,String url){
		this.surl = surl;
		this.url = url;
	}
	
	@Id
	public String getSurl() {
		return surl;
	}
	public void setSurl(String surl) {
		this.surl = surl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
