package com.xczhihui.bxg.common.support.service;

/**
 * 记录用户操作日志
 * @author Haicheng.J
 */
public interface OperationLogService {
	/**
	 * 记录日志
	 * @param loginName 当前用户登录名
	 * @param className 操作的类名
	 * @param methodName 操作的方法名
	 * @param description 描述
	 * @param millisecond 所用时间，毫秒
	 * @param systemType 业务系统类别，详见OperationLog类的静态常量
	 * @param operationType 操作类别，详见OperationLog类的静态常量
	 * @param operationId 被操作实体类的id
	 * @param tableName 表名
	 */
	public void add(String loginName,String className,String methodName,String description, long millisecond,int systemType,int operationType,String operationId,String tableName);
}
