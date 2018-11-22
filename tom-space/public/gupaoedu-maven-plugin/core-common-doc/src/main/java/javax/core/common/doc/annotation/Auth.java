package javax.core.common.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
/**
 * 权限信息描述
 * @author Tanyongde
 *
 */
public @interface Auth {
	
	/**
	 * 是否检查IP
	 * @return
	 */
	boolean checkIp() default false;
	
	/**
	 * 是否检查授权码
	 * @return
	 */
	boolean checkEnc() default false;
	
//	/**
//	 * 是否检查角色权限
//	 * @return
//	 */
//	boolean checkRole() default false;
	
	/**
	 * 是否检查调用的产品版本
	 * @return
	 */
	boolean checkVersion() default false;
	
	/**
	 * 允许访问的IP规则
	 * @return
	 */
	Rule [] ipRule() default {};
	
	/**
	 * 允许访问项目规则
	 * @return
	 */
	Rule [] versionRule() default {};
	
//	/**
//	 * 允许访问的角色规则
//	 * @return
//	 */
//	Rule [] roleRule() default {};
	
//	/**
//	 * 允许访问的加密规则
//	 * @return
//	 */
//	Rule [] encRule() default {};
	
}
