package com.gupaoedu.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 咕泡学院，只为更好的你
 */
public class DataUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static String getDateYYYYMMDD2(){
		return getDate2String(getTimestamp(),  YYYY_MM_DD);
	}

	public static Timestamp getTimestamp(){
		Date date = new Date();
		Timestamp nousedate = new Timestamp(date.getTime());
		return nousedate;
	}

	public static String getDate2String(java.util.Date date ,String type){
		if(type==null){
			type = YYYY_MM_DD;
		}
		SimpleDateFormat df = new SimpleDateFormat(type);//设置日期格式
		return df.format(date);
	}
}
