package com.xczhihui.bxg.common.support.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 记录异常信息
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "exceptionLog")
public class ExceptionLog extends BasicEntity{

	/**
	 * 异常信息
	 */
	@Lob
	@Basic
	@Column(name = "exception_info")
	private String exceptionInfo;

	/**
	 * 异常堆栈信息
	 */
	@Lob
	@Basic
	@Column(name = "exception_stack_info")
	private String exceptionStackInfo;

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public String getExceptionStackInfo() {
		return exceptionStackInfo;
	}

	public void setExceptionStackInfo(String exceptionStackInfo) {
		this.exceptionStackInfo = exceptionStackInfo;
	}
	
}
