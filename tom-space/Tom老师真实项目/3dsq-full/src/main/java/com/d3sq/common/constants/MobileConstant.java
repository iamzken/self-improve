package com.d3sq.common.constants;

import javax.core.common.encrypt.DESStaticKey;
import javax.core.common.encrypt.MD5;

/**
 * 移动端接口常量
 *
 */
public class MobileConstant {
	/**
	 * 请求授权码加密静态Key
	 */
	private static final String ENC_CODE = "@!#^^!&@^@#&()*2";
	
	/** 自动登录Token */
	private static final String TOKEN = "&*%@($@&!^#)*@";
	
	/** 普通版 */
	public static final String VER_NOMAL = "nomal";
	
	/** 商家版 */
	public static final String VER_SHOP = "shop";
	
	
	/**
	 * 加密生成token
	 * @param params
	 * @return
	 */
	public static String genToken(String params){
		return DESStaticKey.encrypt(params, TOKEN);
	}
	
	/**
	 * 解密token字符串
	 * @param token
	 * @return
	 */
	public static String decToken(String token){
		return DESStaticKey.decrypt(token, TOKEN);
	}
	
	/**
	 * 生成授权码
	 * @param params
	 * @return
	 */
	public static String genEnc(String params){
		return MD5.calcMD5(ENC_CODE + params);
	}
}
