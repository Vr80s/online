package com.xczh.consumer.market.vo;

import java.io.Serializable;

public class MyCourseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String course_id;
	private String course_name;
	private String smallimg_path;
	private String teacher_name;
	private String course_expiry_date;
//	private String v_id;
//	private String video_id;
//	private String video_name;
	
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getSmallimg_path() {
		return smallimg_path;
	}
	public void setSmallimg_path(String smallimg_path) {
		this.smallimg_path = smallimg_path;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getCourse_expiry_date() {
		return course_expiry_date;
	}
	public void setCourse_expiry_date(String course_expiry_date) {
		this.course_expiry_date = course_expiry_date;
	}
//	public String getV_id() {
//		return v_id;
//	}
//	public void setV_id(String v_id) {
//		this.v_id = v_id;
//	}
//	public String getVideo_id() {
//		return video_id;
//	}
//	public void setVideo_id(String video_id) {
//		this.video_id = video_id;
//	}
//	public String getVideo_name() {
//		return video_name;
//	}
//	public void setVideo_name(String video_name) {
//		this.video_name = video_name;
//	}
	
}
