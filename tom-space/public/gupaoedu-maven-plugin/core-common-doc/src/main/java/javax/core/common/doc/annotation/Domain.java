package javax.core.common.doc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
/**
 * 定义域名
 * @author Tanyongde
 */

public @interface Domain {
	
	/**
	 * 所在子域名（或模块名）
	 * @return
	 */
	String value() default "";
	
	/**
	 * 别名
	 * @return
	 */
	String alias() default "";
	/**
	 * 说明
	 * @return
	 */
	String desc() default "";
	
}
