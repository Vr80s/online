/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: Nxt5SlRPb4fKZKqOr9zBj/pm8XlAzWss
 */
package net.shopxx.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Audit - 最后修改日期注解
 * 
 * @author ixincheng
 * @version 6.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface LastModifiedDate {

}