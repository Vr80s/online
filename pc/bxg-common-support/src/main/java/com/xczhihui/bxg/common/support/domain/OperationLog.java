package com.xczhihui.bxg.common.support.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 记录用户操作日志
 * @author Haicheng.J
 */
@Entity
@Table(name = "operation_log")
public class OperationLog extends BasicEntity{
	
	//业务类别：
	//未知
	public static final int SYSTEM_DUAL_UNKNOWN = 0;
	//双元后台
	public static final int SYSTEM_DUAL_SYSTEM =  1;
	//双元学生端
	public static final int SYSTEM_DUAL_STUDENT = 2;
	//双元教师端
	public static final int SYSTEM_DUAL_TEACHER = 3;
	//院校后台
	public static final int SYSTEM_UNIV_SYSTEM =  4;
	//院校学生端
	public static final int SYSTEM_UNIV_STUDENT = 5;
	//院校教师端
	public static final int SYSTEM_UNIV_TEACHER = 6;
	//熊猫中医在线后台
	public static final int SYSTEM_ONLINE_SYSTEM =7;
	//知识中心
	public static final int SYSTEM_KNOWLEDGE_CENTER = 8;
	
	//操作类别：
	//未知
	public static final int OP_UNKNOWN =0;
	//增
	public static final int OP_ADD =    1;
	//删
	public static final int OP_DELETE = 2;
	//改
	public static final int OP_EDIT =   3;
	
	/**
	 * 操作的类名
	 */
	@Column(name = "class_name")
	private String className;
	/**
	 * 业务类别
	 */
	@Column(name = "system_type")
	private int systemType = OperationLog.SYSTEM_DUAL_UNKNOWN;
	/**
	 * 操作类别
	 */
	@Column(name = "operation_type")
	private int operationType = OperationLog.OP_UNKNOWN;
	/**
	 * 操作的方法名
	 */
	@Column(name = "method_name")
	private String methodName;
	/**
	 * 操作的描述
	 */
	@Column(name = "operation_desc")
	private String description;
	/**
	 * 操作用时，毫秒
	 */
	@Column(name = "operation_millisecond")
	private long millisecond;
	/**
	 * 被操作实体类的ID
	 */
	@Column(name = "operation_id",columnDefinition="longtext")
	private String operationId;
	/**
	 * 表名
	 */
	@Column(name = "table_name")
	private String tableName;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getMillisecond() {
		return millisecond;
	}
	public void setMillisecond(long millisecond) {
		this.millisecond = millisecond;
	}
	public int getSystemType() {
		return systemType;
	}
	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
