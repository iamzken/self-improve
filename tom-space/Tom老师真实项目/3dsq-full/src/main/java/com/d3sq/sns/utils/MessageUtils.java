package com.d3sq.sns.utils;

import javax.core.common.utils.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class MessageUtils {
	
	private static JSONObject error = new JSONObject();
	
	public static JSONObject errorMsg(String info){
		error.clear();
		if(StringUtils.isEmpty(info)) return null;
		error.put("errorInfo", info);
		return error;
	}

}
