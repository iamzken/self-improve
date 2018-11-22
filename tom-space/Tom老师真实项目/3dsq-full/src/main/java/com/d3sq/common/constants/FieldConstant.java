package com.d3sq.common.constants;


/**
 * 实体类字段常量定义词典
 */
public class FieldConstant {
	
	//================  MallOrder.otype 字段常量值  ===================
	/**其他*/
	public static final Integer PAY_OPYPE_OTHER = 1;
	/**购物支付*/
	public static final Integer PAY_OTYPE_SHOPPING = 2;
	/**交物业费*/
	public static final Integer PAY_OTYPE_PROP_FEE = 3;
	/**会员充值支付*/
	public static final Integer PAY_OTYPE_RECH_VIP = 4;
	/** 快递邮费 */
	public static final Integer PAY_OTYPE_EXPRESS = 5;
	/** 充话费 */
	public static final Integer PAY_OTYPE_PHONE = 6;
	/** 预约服务支付 */
	public static final Integer PAY_OTYPE_SERVICE = 7;
	
	
	//================  MallOrder.num 订单号前缀 ===================
	/** 子订单 */
	public static final String PAY_NUM_PREFIX_C = "C";
	/** 父订单 */
	public static final String PAY_NUM_PREFIX_P = "P";
	
	
	
	//================  MallOrder.payType 字段常量值  ===================
	/** 支付宝支付 */
	public static final Integer PAY_TYPE_ALIPAY = 1;
	/** 微信支付 */
	public static final Integer PAY_TYPE_WEPAY = 2;
	/** 银行卡支付 */
	public static final Integer PAY_TYPE_CARDPAY = 3;
	/** 现金支付 */
	public static final Integer PAY_TYPE_MONEY = 4;
	
	
	//================  MallOrder.process 字段常量值  ===================
	/**已下单未付款*/
	public static final Integer PAY_PROCESS_UNPAY = 1;
	/**已付款待确认 */
	public static final Integer PAY_PROCESS_PAY = 2;
	/**已确认待送货*/
	public static final Integer PAY_PROCESS_CONFIRM = 3;
	/**正在送货待收货*/
	public static final Integer PAY_PROCESS_DELIVER = 4;
	/**已收货，完成交易*/
	public static final Integer PAY_PROCESS_FINISH = 5;
	/**取消订单*/
	public static final Integer PAY_PROCESS_CANCEL = -1;
	
	
	
	//================  Role.rootFlag 字段常量值  ===================
	/** 后台系统管理员 */
	public static final Integer ROLE_ROOT = 1;
	/** 商家用户 */
	public static final Integer ROLE_SHOP = 2;
	/** 普通用户 */
	public static final Integer ROLE_NOMAL = 3;
	
	
	//================  系统子平台常量定义，所有实体类中的platform字段通用  ===================
	/** 系统子平台名称，默认公共平台 */
	public static final String PLATFORM_SYSTEM = "system";
	/** 系统子平台，后台管理平台 */
	public static final String PLATFORM_ADMIN = "admin";
	/** 系统子平台，门户站 */
	public static final String PLATFORM_PORTAL = "portal";
	/** 系统子平台，统一认证平台 */
	public static final String PLATFORM_PASSPORT = "passport";
	/** 系统子平台，用户个人中心 */
	public static final String PLATFORM_CENTER = "i";
	/** 系统子平台，项目管理平台 */
	public static final String PLATFORM_PM = "pm";
	/** 系统子平台，购物平台 */
	public static final String PLATFORM_MALL = "mall";
	/** 系统子平台，快递服务 */
	public static final String PLATFORM_EXPRESS = "express";
	/** 系统子平台，物业服务 */
	public static final String PLATFORM_PROPERTY = "property";
	/** 系统子平台，支付系统 */
	public static final String PLATFORM_PAY = "pay";
	/** 系统子平台，金融服务平台 */
	public static final String PLATFORM_FINANCE = "finance";
	/** 系统子平台，社交平台 */
	public static final String PLATFORM_SNS = "sns";
	/** 系统子平台，资源管理平台 */
	public static final String PLATFORM_EXPLORER = "explorer";
	/** 系统子平台，获取资源平台 */
	public static final String PLATFORM_RESOURCE = "resource";
	/** 系统子平台名称，移动端管理平台 */
	public static final String PLATFORM_MOBILE = "mobile";
	/** 系统子平台名称，AOS */
	public static final String PLATFORM_ANDROID = "android";
	/** 系统子平台名称，IOS */
	public static final String PLATFORM_IOS = "ios";
	/** 系统子平台名称，IPAD */
	public static final String PLATFORM_IPAD = "ipad";
	/** 系统子平台名称，Windows Phone */
	public static final String PLATFORM_WP = "winphone";
	
	
	
	//================  Article.atype 字段常量值  ===================
	/** 文章类型-通知公告 */
	public static final Integer ARTICLE_TYPE_NOTICE = 1;
	/** 文章类型-新闻 */
	public static final Integer ARTICLE_TYPE_NEWS = 2;
	
	
	
	//================  所有记录性别的字段常量值  ===================
	/** 性别女 */
	public static final Integer SEX_WOMEN = 0;
	/** 性别男 */
	public static final Integer SEX_MEN = 1;
	/** 性别保密 */
	public static final Integer SEX_HIDE = 2;
	
	
	
	//================  SNSGroup.gtype 字段常量值  ===================
	/** 好友分组类型 - 家庭成员分组 */
	public static final Integer SNS_GROUP_GTYPE_FAMILY = 1;
	/** 好友分组类型 - 普通好友分组 */
	public static final Integer SNS_GROPU_GTYPE_NOMAL = 2;
	
	
	
	
	//================  Member.mtype 字段常量值  ===================
	/** 手机号注册 */
	public static final Integer MEMBER_MTYPE_TEL = 1;
	/** 微信号注册 */
	public static final Integer MEMBER_MTYPE_WECHAT = 2;
	/** QQ号注册 */
	public static final Integer MEMBER_MTYPE_QQ = 3;
	/** 微博账号注册 */
	public static final Integer MEMBER_MTYPE_WEBLOG = 4;
	
	
	//================  Member.state 字段常量值  ===================
	/** 用户已被禁用 */
	public static final Integer MEMBER_STATE_FORBIDDEN = -3;
	/** 用户已被删除*/
	public static final Integer MEMBER_STATE_DELETE = -2;
	/** 密码已被重置 */
	public static final Integer MEMBER_STATE_RESET = -1;
	/** 正常状态*/
	public static final Integer MEMBER_STATE_NOMAL = 1;
	
	
	//================  Member.loginPass 字段常量值  ===================
	/** 密码为空 */
	public static final String MEMBER_LOGINPASS_EMPTY = "EMPTY";
	
	
	//================  Member.certFlag 字段常量值  ===================
	/** 证书状态:未提交资料 */
	public static final Integer MEMBER_CERT_EMPTY = 0;
	/** 证书状态:已提交个人认证 */
	public static final Integer MEMBER_CERT_PERSON = 1;
	/** 证书状态:已提交企业资料 */
	public static final Integer MEMBER_CERT_COMPANY = 2;
	
	
	//================  Member.auditFlag 字段常量值  ===================
	/** 审核状态:已提交未审核 */
	public static final Integer MEMBER_AUDIT_EMPTY = 0;
	/** 审核状态:审核通过 */
	public static final Integer MEMBER_AUDIT_SUCCESS = 1;
	/** 审核状态:审核不通过 */
	public static final Integer MEMBER_AUDIT_ERROR = -1;
	
	
	//================  Kind.alias 字段常量值  ===================
	/** 店铺分类 */
	public static final String KIND_ALIAS_SHOP = "SHOP";
	/** 商品分类 */
	public static final String KIND_ALIAS_PRODUCT = "PRODUCT";
	/** 服务分类 */
	public static final String KIND_ALIAS_SERVICE = "SERVICE";
	/** 快递公司分类表*/
	public static final String KIND_ALIAS_EXPRESS = "EXPRESS";
	
	
	//================  ExOrder.fessFlag 字段常量值  ===================
	/** 按重量计费 */
	public static final Integer EX_ORDER_FEES_FLAG_WEIGHT = 1;
	/** 按体积计费 */
	public static final Integer EX_ORDER_FEES_FLAG_VOLUME = 2;
	
	//================  ExOrder.postState 字段常量值  ===================
	/** 取件中 */
	public static final Integer EX_ORDER_POST_STATE_ACCESS = 1;
	/** 已打包 */
	public static final Integer EX_ORDER_POST_STATE_PACKAGE = 2;
	/** 运送中 */
	public static final Integer EX_ORDER_POST_STATE_ONWAY = 3;
	/** 达到目的地 */
	public static final Integer EX_ORDER_POST_STATE_ARRIVE = 4;
	/** 派件中 */
	public static final Integer EX_ORDER_POST_STATE_DISPATCH = 5;
	/** 已完成 */
	public static final Integer EX_ORDER_POST_STATE_FINISH = 6;
	
	//================  ExOrder.accessFlag 字段常量值  ===================
	/** 上门取件 */
	public static final Integer EX_ORDER_ACCESS_FLAG_TAKE = 1;
	/** 自助送件 */
	public static final Integer EX_ORDER_ACCESS_FLAG_SELF = 2;
	
	
	//================  Product.ptype 字段常量值  ===================
	/** 产品类型-商品 */
	public static final Integer PRODUCT_PTYPE_COMMOD = 1;
	/** 产品类型-服务 */
	public static final Integer PRODUCT_PTYPE_SERVICE = 2;
	
	
	
	//================  Search.type 字段常量值  ===================
	/** 搜索类型-商品 */
	public static final Integer SEARCH_TYPE_COMMOD = 1;
	/** 搜索类型-服务 */
	public static final Integer SEARCH_TYPE_SERVICE = 2;
	/** 搜索类型-小区 */
	public static final Integer SEARCH_TYPE_RESIDENTIAL = 3;
	/** 搜索类型-店铺 */
	public static final Integer SEARCH_TYPE_SHOP = 4;
	/** 搜索类型-用户姓名 */
	public static final Integer SEARCH_TYPE_MEMBER = 5;
	
	
	
	//================  City.levelType字段常量值  ===================
	/** 省、直辖市 */
	public static final Integer CITY_LEVELTYPE_PROV = 1;
	/** 市、地级市 */
	public static final Integer CITY_LEVELTYPE_CITY = 2;
	/** 区、县 */
	public static final Integer CITY_LEVELTYPE_DISTRICT = 3;
	/** 乡镇、街道 */
	public static final Integer CITY_LEVELTYPE_TOWN = 4;
	/** 村、社区 */
	public static final Integer CITY_LEVELTYPE_COMMUNITY = 5;
	/** 组、小区*/
	public static final Integer CITY_LEVELTYPE_RESIDENTIAL = 6;
	
	
	
	
	//================  BBSForum.alias字段常量值  ===================
	/** 小区公共版块 */
	public static final String BBSFORUM_ALIAS_RESIDENTIAL = "PUB_RESIDENTIAL";
	/** 小区物业公共版块 */
	public static final String BBSFORUM_ALIAS_PROPERTY = "PUB_PROPERTY";
	
}
