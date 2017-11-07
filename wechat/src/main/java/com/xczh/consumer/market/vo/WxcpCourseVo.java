package com.xczh.consumer.market.vo;

import java.io.Serializable;

public class WxcpCourseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer course_id;
	private String teacher_id;
	private String teacher_name;
	private String course_name;
	private String course_length;
	private String course_outline;
	private String summary;
	private Double original_cost;
	private Double current_price;
	private String smallimg_path;
	private String teacher_decs;
	private String course_expiry_date;
	private String teacher_head_img;

	private Integer learnd_count;
	private Boolean is_buy;
	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_length() {
		return course_length;
	}

	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}

	public String getCourse_outline() {
		return course_outline;
	}

	public void setCourse_outline(String course_outline) {
		this.course_outline = course_outline;
	}

	

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getOriginal_cost() {
		return original_cost;
	}

	public void setOriginal_cost(Double original_cost) {
		this.original_cost = original_cost;
	}

	public Double getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(Double current_price) {
		this.current_price = current_price;
	}

	public String getSmallimg_path() {
		return smallimg_path;
	}

	public void setSmallimg_path(String smallimg_path) {
		this.smallimg_path = smallimg_path;
	}

	public String getTeacher_decs() {
		return teacher_decs;
	}

	public void setTeacher_decs(String teacher_decs) {
		this.teacher_decs = teacher_decs;
	}

	public String getTeacher_head_img() {
		return teacher_head_img;
	}

	public void setTeacher_head_img(String teacher_head_img) {
		this.teacher_head_img = teacher_head_img;
	}

	public Integer getLearnd_count() {
		return learnd_count;
	}

	public void setLearnd_count(Integer learnd_count) {
		this.learnd_count = learnd_count;
	}

	public String getCourse_expiry_date() {
		return course_expiry_date;
	}

	public void setCourse_expiry_date(String course_expiry_date) {
		this.course_expiry_date = course_expiry_date;
	}

	public Boolean getIs_buy() {
		return is_buy;
	}

	public void setIs_buy(Boolean is_buy) {
		this.is_buy = is_buy;
	}

	
	
	
}
