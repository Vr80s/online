package com.xczhihui.bxg.online.web.vo;

/**
 * 用户F码
 * @author Haicheng Jiang
 */
public class UserFcodeVo {
	private String id;
	private String user_id;
	private String fcode;
	private String course_names;
	private String course_name;
	private Integer status;
	private String expiry_time;
	private String used_course_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	public String getCourse_names() {
		return course_names;
	}
	public void setCourse_names(String course_names) {
		this.course_names = course_names;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getExpiry_time() {
		if (expiry_time != null) {
			return expiry_time.substring(0,10);
		}
		return expiry_time;
	}
	public void setExpiry_time(String expiry_time) {
		this.expiry_time = expiry_time;
	}
	public String getUsed_course_id() {
		return used_course_id;
	}
	public void setUsed_course_id(String used_course_id) {
		this.used_course_id = used_course_id;
	}
}
