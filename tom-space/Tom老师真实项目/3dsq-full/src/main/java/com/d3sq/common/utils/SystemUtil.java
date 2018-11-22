package com.d3sq.common.utils;

import javax.core.common.utils.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.d3sq.model.helper.ImageItem;

/**
 * 系统工具类
 */
public class SystemUtil {

	/**
	 * 校验json数组字符串是否为有效的图片格式
	 * @param jsonArr
	 * @return
	 */
	public static boolean[] validImgArrByJson(String jsonArr){
		boolean[] result = null;
		try{
			JSONArray arr = JSONArray.parseArray(jsonArr);
			result = new boolean[arr.size()];
			for (int i = 0; i < arr.size(); i ++) {
				result[i] = validImgByJson(JSON.toJSONString(arr.get(i)));
			}
		}catch(Exception e){
			return null;
		}
		return result;
	}
	
	/**
	 * 校验json字符串是否为有效的图片格式
	 * @param json
	 * @return
	 */
	public static boolean validImgByJson(String json){
		try{
			ImageItem item = JSON.parseObject(json,ImageItem.class);
			if(StringUtils.isEmpty(item.getFilePath())){
				return false;
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
