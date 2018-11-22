package com.gupaoedu.vip.mongo.explorer.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统通用常量
 * @author Tom
 *
 */
public class SystemConstant {

	/**
	 * 返回结果正常。
	 */
	public static final int RESULT_STATUS_SUCCESS = 1;
	/**
	 * 返回结果失败
	 */
	public static final int RESULT_STATUS_FAILED = 2;
	
	/**
	 * APP别名-黄金眼
	 */
	public static final String APP_ALIAS_HJY = "hjy";
	
	/**
	 * APP别名-铁金刚
	 */
	public static final String APP_ALIAS_TJG = "tjg";
	
	/**
	 * APP别名-金手指
	 */
	public static final String APP_ALIAS_JSZ = "jsz";
	
	/**
	 * APP别名-金算盘
	 */
	public static final String APP_ALIAS_JSP = "jsp";

	/**
	 * 
	 */
	public static final Map<String,String> getAppNo = new HashMap<String,String>();
	
	static{
		getAppNo.put(APP_ALIAS_HJY, "01280002");
		getAppNo.put(APP_ALIAS_TJG, "01280003");
		getAppNo.put(APP_ALIAS_JSP, "01280008");
		getAppNo.put(APP_ALIAS_JSZ, "01280007");
	}
	
}
