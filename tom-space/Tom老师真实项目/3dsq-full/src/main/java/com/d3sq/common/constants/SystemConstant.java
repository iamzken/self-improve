package com.d3sq.common.constants;

/**
 * 系统通用常量
 *
 */
public class SystemConstant {
	
	/**
	 * 超级管理员标识
	 */
	public static final int IS_ROOT = 1;
	
	/**
	 * 站点管理员标识
	 */
	public static final int IS_ADMIN = 2;
	
	/**
	 * 普通用户
	 */
	public static final int IS_NORMAL = 0;
	
	/**
	 * 已生效、已激活、已删除等，表示肯定。
	 */
	public static final int ENABLE = 1;
	/**
	 * 未生效、已激活、已删除等，表示否定。
	 */
	public static final int DISABLE = 0;
	/**
	 * 订单商品有差价
	 */
	public static final int RESULT_RESULT_ORDER_ERROR = -1;
	/**
	 * 请求参数错误。
	 */
	public static final int RESULT_PARAM_ERROR = 9;
	/**
	 * 返回结果正常。
	 */
	public static final int RESULT_STATUS_SUCCESS = 1;
	/**
	 * 返回结果失败
	 */
	public static final int RESULT_STATUS_FAILED = 2;
	/**
	 * 返回结果有错误，服务器内部异常。
	 */
	public static final int RESULT_STATUS_ERROR = 0;
	/**
	 * 请求超时。
	 */
	public static final int RESULT_STATUS_TIMEOUT = 3;
	/**
	 * 不允许访问，没有权限。
	 */
	public static final int RESULT_STATUS_UNALLOW = 4;
	/**
	 * 密码重置
	 */
	public static final int RESULT_STATUS_RESET = 97;
	/**
	 * 未登录。
	 */
	public static final int RESULT_STATUS_UNLOGIN = 99;
	/**
	 * 没有权限
	 */
	public static final int RESULT_STATUS_NO_PERMI = 98;
	
	/** redis缓存配置key */
	public static final String REDIS_REF_HOSTS = "redis_ref_hosts";
	
	/** 登录Session标识键 */
	public static final String SYSTEM_LOGIN_FLAG = "userinfo";
	/** 登录Cookie标识键 */
	public static final String SYSTEM_LOGIN_COOKIE = "userkey";
	/** 登录验证码 */
	public static final String SYSTEM_LOGIN_AUTHCODE = "authcode";
	/** 机构模拟登录 */
	public static final String SYSTEM_LOGIN_MOCK = "company_mock_";
	/** 自动登录标识 */
	public static final Integer SYSTEM_LOGIN_AUTO = 5;
	
	/**
	 * 随机验证码Session标识
	 */
	public static final String RAND_CODE_KEY = "SE_KEY_MM_CODE";
	
	/**
	 * 本地化语言环境Session标识
	 */
	public static final String LNG_LOCAL = "sys_user_language";
	
	/**
	 * 登录密码二次加密静态key
	 */
	public static final String PASS_ENC = "";//"&*^9&%&$-1348253";
	
	/**
	 * easyUI 取分页参数 ,每页显示多少行
	 */
	public static final String EASY_UI_DATAGRID_PAGE_SIZE_KEY = "pageSize";
	/**
	 * easyUI 取分页参数 ,当前第几页
	 */
	public static final String EASY_UI_DATAGRID_CURR_PAGE_KEY = "page";
	
	/**
	 * easyUI 取 json list 的键
	 */
	public static final String EASY_UI_DATAGRID_JSON_ROWS_KEY = "rows";
	/**
	 * easyUI 取 json list 的大小
	 */
	public static final String EASY_UI_DATAGRID_JSON_TOTAL_KEY = "total";
	/**
	 * easyUI 取排序字段名
	 */
	public static final String EASY_UI_DATAGRID_SORT = "sort";
	/**
	 * easyUI 取排序规则,降序或升序
	 */
	public static final String EASY_UI_DATAGRID_ORDER = "order";
}
