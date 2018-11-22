package com.gupaoedu.vip.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by Tom on 2018/4/18.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowried {
    String value() default  "";
}
