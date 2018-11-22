package javax.core.common.doc.annotation;

public @interface Demo {
	/**
	 * 请求示例
	 * @return
	 */
	String param() default "";
	/**
	 * 返回成功结果示例
	 * @return
	 */
	String error() default "";
	/**
	 * 请求失败结果示例
	 * @return
	 */
	String success() default "";
	
}
