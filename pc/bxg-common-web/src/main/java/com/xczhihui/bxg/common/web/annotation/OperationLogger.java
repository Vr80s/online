package com.xczhihui.bxg.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来记录操作日志
 * @author Haijing.J
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface OperationLogger {
	/**
	 * 要操作的实体类id参数的名称，如要删除一个用户“http://xxx/user/del?userid=123”，名称就是“userid”，
	 * 保证后台能用request.getParameter(xxx)获得参数值;
	 * @return
	 */
	public String idparameterName() default "";
	/**
	 * 操作描述
	 * @return
	 */
	public String description() default "";
	/**
	 * 业务系统类别，详见OperationLog的静态常量
	 * @return
	 */
	public int systemType() default 0;
	/**
	 * 操作类别，详见OperationLog的静态常量
	 * @return
	 */
	public int operationType() default 0;
	/**
	 * 被操作的表名
	 * @return
	 */
	public String tableName() default "";
}
