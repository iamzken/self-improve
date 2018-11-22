package com.gupaoedu.vip.mongo.explorer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 短链接实体类定义
 * @author Tom
 *
 */
@Document(collection="t_short_url")
public class ShortUrl implements Serializable{

	//短Url
	@Id
	private String surl;
	//全url
	private String url;

	public ShortUrl(){}
	
	public ShortUrl(String surl,String url){
		this.surl = surl;
		this.url = url;
	}

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
