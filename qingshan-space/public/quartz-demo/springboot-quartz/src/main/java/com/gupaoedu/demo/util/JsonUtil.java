package com.gupaoedu.demo.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 封装json对象
 * 
 *
 */
public class JsonUtil {
	/**
	 * 返回前端方法
	 * @param msg 成功提示或错误信息
	 * @param code 成功时code=200，失败时code=500
	 * @return
	 */
	public static JSONObject returnFront(Object msg, String code){
		JSONObject JSON=new JSONObject();
		JSON.put("code",code);
		JSON.put("msg",msg);
		return JSON;
	}
}
