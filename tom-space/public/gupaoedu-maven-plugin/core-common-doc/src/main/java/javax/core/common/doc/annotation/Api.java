package javax.core.common.doc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
/**
 * 定义api方法说明文档
 * @author Tanyongde
 *
 */
public @interface Api {
	
	/**
	 * 创建时间
	 * @return
	 */
	String createtime() default "";
	
	/**
	 * 作者
	 * @return
	 */
	String author() default "";
	
	/**
	 * 其他说明
	 * @return
	 */
	String other() default "";
	
	/**
	 * 接口说明
	 * @return
	 */
	String desc() default "";
	
	/**
	 * 接口名称
	 * @return
	 */
	String name() default "";
	
	/**
	 * 参数列表
	 * @return
	 */
	Rule [] params() default {};
	
	/**
	 * 返回值说明
	 * @return
	 */
	Rule [] returns() default {};
	/**
	 * 结果示例
	 * @return
	 */
	Demo demo() default @Demo;
	
	/**
	 * 权限设置
	 * @return
	 */
	Auth [] auth() default {};
	
	/**
	 * 其他备注信息
	 * @return
	 */
	String remark() default "";
	
}
