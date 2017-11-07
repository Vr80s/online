package com.xczhihui.bxg.common.support.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 统一异常处理的实体类
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "bxg_exception")
public class BxgException extends BasicEntity{
	/**
	 * 异常信息
	 */
	@Column(name = "e_message")
	private String message;
	/**
	 * 异常内容，堆栈信息
	 */
	@Lob
	@Basic
	@Column(name = "content")
	private String e_content;
	/**
	 * 发生异常的业务系统
	 */
	@Column(name = "e_project_name")
	private String projectName;
	/**
	 * 请求头信息
	 */
	@Lob
	@Basic
	@Column(name = "header_msg")
	private String headerMsg;
	
	@Transient
	private String createTimeString;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getE_content() {
		return e_content;
	}
	public void setE_content(String e_content) {
		this.e_content = e_content;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getHeaderMsg() {
		return headerMsg;
	}
	public void setHeaderMsg(String headerMsg) {
		this.headerMsg = headerMsg;
	}
	public String getCreateTimeString() {
		return createTimeString;
	}
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
}
