package com.gupaoedu.common.constants;

import javax.core.common.annotation.Comment;

/**
 * 定时任务表达式定义
 * @author Tom
 */
public class CronExpression {
	
	/**
	 * 每天0点触发
	 */
	@Comment("每天0点触发")
	public static final String TIME_0_HOUR = "0 0 0 * * ?";
	
	/**
	 * 每天0点15分触发
	 */
	@Comment("每天0点15分触发")
	public static final String TIME_0_HOUR_15_MINUTE = "0 15 0 * * ?";
	
	/**
	 * 每天0点10分触发
	 */
	@Comment("每天0点10分触发")
	public static final String TIME_0_HOUR_10_MINUTE = "0 10 0 * * ?";
	
	/**
	 * 每天凌晨1点触发
	 */
	@Comment("每天1点触发")
	public static final String TIME_1_HOUR = "0 0 1 * * ?";
	
	/**
	 * 每天凌晨2点触发
	 */
	@Comment("每天凌晨2点触发")
	public static final String TIME_2_HOUR = "0 0 2 * * ?";
	
	/**
	 * 每天凌晨3点触发
	 */
	@Comment("每天凌晨3点触发")
	public static final String TIME_3_HOUR = "0 0 3 * * ?";
	
	/**
	 * 每天早上8点触发
	 */
	@Comment("每天早上8点触发")
	public static final String TIME_8_HOUR = "0 0 8 * * ?";
	
	/**
	 * 每天中午12点触发
	 */
	@Comment("每天中午12点触发")
	public static final String TIME_12_HOUR = "0 0 12 * * ?";
	
	/**
	 * 每隔5秒钟触发
	 */
	@Comment("每隔5秒钟触发")
	public static final String EVERY_5_SECOND = "0/5 * * * * ?";
	
	/**
	 * 每隔5分钟触发
	 */
	@Comment("每隔5分钟触发")
	public static final String EVERY_5_MINUTE = "0 0/5 * * * ?";
	
	/**
	 * 每隔1小时触发
	 */
	@Comment("每隔1小时触发")
	public static final String EVERY_1_HOUR = "0 0 0/1 * * ?";
	
	/**
	 * 每隔2小时触发
	 */
	@Comment("每隔2小时触发")
	public static final String EVERY_2_HOUR = "0 0 0/2 * * ?";
	
	/**
	 * 每隔3小时触发
	 */
	@Comment("每隔3小时触发")
	public static final String EVERY_3_HOUR = "0 0 0/3 * * ?";
	
	
	@Comment("每个小时的第5分钟触发")
	public static final String EVERY_5_MINUTE_OF_HOUR = "0 5 * * * *";
	
	@Comment("每天9点整，13点整，19点整触发 ")
	public static final String TIME_9_HOUR_AND_13_HOUR_19_HOUR = "0 0 9,13,19 * * ?";
	
	@Comment("每天7点整，10点整，14点整触发 ")
	public static final String TIME_7_HOUR_AND_10_HOUR_14_HOUR = "0 0 7,10,14 * * ?";
	
	@Comment("每月第一天凌晨2点触发")
	public static final String TIME_2_HOUR_WHERE_FIRST_DAY_OF_MONTH = "0 0 2 1 * ?";
	
	@Comment("每年第一天早上6点触发")
	public static final String TIME_6_HOUR_WHERE_FIRST_DAY_OF_YEAR = "0 0 6 1 1 ?";
	
	
	//=========================  常用表达式举例  ================================
	
	
	/**
	 * 每天上午10点15分触发(写法1)
	 */
	@Comment("每天上午10点15分触发")
	private static final String TIME_10_HOUR_15_MINUTE_1 = "0 15 10 ? *";
	
	/**
	 * 每天上午10点15分触发(写法2)
	 */
	@Comment("每天上午10点15分触发")
	private static final String TIME_10_HOUR_15_MINUTE_2 = "0 15 10 * * ?";
	
	/**
	 * 每天上午10点15分触发(写法3)
	 */
	@Comment("每天上午10点15分触发")
	private static final String TIME_10_HOUR_15_MINUTE_3 = "0 15 10 * * ? *";
	
	/**
	 * 2005年的每天上午10:15触发
	 */
	@Comment("2005年的每天上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_2005_YEAR = "0 15 10 * * ? 2005";
	
	/**
	 * 每年三月的星期三的下午2:10和2:44触发
	 */
	@Comment("每年三月的星期三的下午2:10和2:44触发")
	private static final String TIME_14_HOUR_10_MINUTE_AND_14_HOUR_44_MINUTE_WHERE_WEDNESDAY_AND_MARCH = "0 10,44 14 ? 3 WED";

	/**
	 * 周一至周五的上午10:15触发
	 */
	@Comment("周一至周五的上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_MONDAY_TO_FRIDAY = "0 15 10 ? * MON-FRI";
	
	/**
	 * 每月15日上午10:15触发
	 */
	@Comment("每月15日上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_ERERY_MONTH = "0 15 10 15 * ?";
	
	/** 
	 *每月最后一日的上午10:15触发
	 */
	@Comment("每月最后一日的上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_LAST_DAY_OF_MONTH = "0 15 10 L * ?";
	
	/**
	 * 每月的最后一个星期五上午10:15触发
	 */
	@Comment("每月的最后一个星期五上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_LAST_FRIDAY_OF_MONTH = "0 15 10 ? * 6L";
	
	/**
	 * 2002年至2005年的每月的最后一个星期五上午10:15触发
	 */
	@Comment("2002年至2005年的每月的最后一个星期五上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_LAST_FRIDAY_OF_MONTH_AND_2002_YEAR_TO_2005_YEAR = "0 15 10 ? * 6L 2002-2005";
	
	/**
	 * 每月的第三个星期五上午10:15触发
	 */
	@Comment("每月的第三个星期五上午10:15触发")
	private static final String TIME_10_HOUR_15_MINUTE_WHERE_THIRD_FRIDAY_OF_MONTH = "0 15 10 ? * 6#3";
	
	/**
	 * 每天早上6点触发
	 */
	@Comment("每天早上6点触发")
	private static final String TIME_6_HOUR = "0 6 * * *";
	
	/**
	 * 每个月的4号和每个周一和周三的早上11点触发
	 */
	@Comment("每个月的4号和每个周一和周三的早上11点触发")
	private static final String TIME_11_HOUR_WHERE_FOURTH_DAY_OF_MONTH_AND_MONDAY_AND_WEDNESDAY = "0 11 4 * 1-3";
	
	/**
	 * 每年1月1日早上4点触发
	 */
	@Comment("每年1月1日早上4点触发")
	private static final String TIME_4_HOUR_WHERE_FIRST_DAY_OF_YEAR = "0 4 1 1 *";
	
	/**
	 * 在每周三和周五的10：30，11：30，12：30触发
	 */
	@Comment("在每周三和周五的10：30，11：30，12：30触发")
	private static final String TIME_30_MINUTE_WHERE_10_HOUR_TO_13_HOUR_AND_WEDNESDAY_AND_FRIDAY = "0 30 10-13 ? *WED,FRI";
	
	/**
	 * 在每天下午2点到下午2:59期间的每1分钟触发
	 */
	@Comment("在每天下午2点到下午2:59期间的每1分钟触发")
	private static final String EVERY_1_MINUTE_WHERE_14_HOUR_TO_14_HOUR_1_MINUTE = "0 * 14 * * ?";
	
	/**
	 * 在每天下午2点到下午2:55期间的每5分钟触发 
	 */
	@Comment("在每天下午2点到下午2:55期间的每5分钟触发")
	private static final String EVERY_5_MINUTE_WHERE_14_HOUR_TO_14_HOUR_55_MINUTE = "0 0/5 14 * * ?";
	
	/**
	 * 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
	 */
	@Comment("在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 ")
	private static final String EVERY_5_MINUTE_WHERE_14_HOUR_55_MINUTE_TO_18_HOUR_55_MINUTE = "0 0/5 14,18 * * ?";

	/**
	 * 在每天下午2点到下午2:05期间的每1分钟触发 
	 */
	@Comment("在每天下午2点到下午2:05期间的每1分钟触发")
	private static final String EVERY_1_MINUTE_WHERE_14_HOUR_TO_14_HOUR_55_MINUTE = "0 0-5 14 * * ?";
	
	/**
	 * 每天晚上11点到早上8点之间每两个小时触发一次
	 */
	@Comment("每天晚上11点到早上8点之间每两个小时触发")
	private static final String EVERY_2_HOUR_WHERE_23_HOUR_TO_8_HOUR = "0 23-7/2，8 * * *";
	
	/**
	 * 在每分钟的10秒后每隔5分钟触发(例如. 10:00:10 am, 10:05:10等)
	 */
	@Comment("在每分钟的10秒后每隔5分钟触发")
	private static final String EVERY_5_MINUTE_WHERE_AFTER_10_SECOND_OF_MINUTE = "10 0/5 * * *?";
	
}
