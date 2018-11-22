package com.d3sq.sns.utils;

import java.util.ArrayList;
import java.util.List;

import javax.core.common.utils.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.utils.SystemUtil;
import com.d3sq.sns.vo.ImageVo;

public class ImagesUtil {
	
	public static List<ImageVo> imgs2List(String imgs){
		List<ImageVo> list = new ArrayList<ImageVo>();
		if (!StringUtils.isEmpty(imgs)) {
			String[] imgUrls = imgs.split(",");
			if (imgUrls != null) {
				for (String imgUrl : imgUrls) {
					if (!StringUtils.isEmpty(imgUrl)) {
						ImageVo io = new ImageVo();
						io.setImgUrl(imgUrl);
						list.add(io);
					}
				}
			}
		}
		return list;
	}

	public static String validImgArr(String contentImgs){
		if(StringUtils.isEmpty(contentImgs)) return "";
		contentImgs = StringUtils.decode(contentImgs, "utf-8");
		List<JSONObject> result = new ArrayList<JSONObject>();
		JSONArray arr = null;
		try {
			arr = JSONArray.parseArray(contentImgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null == arr) return "";
		for (int i = 0; i < arr.size(); i++) {
			JSONObject imgObj = arr.getJSONObject(i);
			if(null == imgObj) continue;
			boolean ststus = SystemUtil.validImgByJson(imgObj.toJSONString());
			if(!ststus) continue;
			result.add(imgObj);
		}
		return JSONObject.toJSONString(result);
		
	}
}
