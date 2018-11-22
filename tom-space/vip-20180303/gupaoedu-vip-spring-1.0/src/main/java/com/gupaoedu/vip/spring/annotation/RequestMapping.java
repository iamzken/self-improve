package com.gupaoedu.vip.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by Tom on 2018/4/18.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default  "";

}
