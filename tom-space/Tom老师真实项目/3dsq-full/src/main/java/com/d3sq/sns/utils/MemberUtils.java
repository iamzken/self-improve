package com.d3sq.sns.utils;

import java.io.UnsupportedEncodingException;

import javax.core.common.utils.StringUtils;

import com.d3sq.model.entity.Member;

/**
 * 用户工具类
 *
 */
public class MemberUtils {
	
	public static String getName(Member member){
		if(null == member) return "";
		String name = member.getNickName();
		if(!StringUtils.isEmpty(name)) return name;
		name = member.getLoginName();
		if(!StringUtils.isEmpty(name)) {
			try {
				return StringUtils.getPartString(name, 7, "****");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}

}
