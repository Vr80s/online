package com.xczh.consumer.market.auth;

import java.lang.annotation.*;

/**
 * 该注解可加在接口参数上，用于获取用户的信息
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Account {

    boolean optional() default false;
}


