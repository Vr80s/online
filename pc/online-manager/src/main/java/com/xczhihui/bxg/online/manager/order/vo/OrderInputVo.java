package com.xczhihui.bxg.online.manager.order.vo;
import org.springframework.web.multipart.MultipartFile;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
/**
 * 线下订单录入
 * @author Haicheng Jiang
 */
public class OrderInputVo extends OnlineBaseVo  {
	private String id;
	private String user_id;
	private String login_name;
	private String course_id;
	private String course_name;
	private String create_person; 
	private String create_time;
	private String create_time_start;
	private String create_time_end;
	private Integer order_from;
	private Double actual_pay;
	private String class_id;
	private MultipartFile excelFile;
	
	private String key_type;
	private String key_value;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
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
	public String getCreate_person() {
		return create_person;
	}
	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getOrder_from() {
		return order_from;
	}
	public void setOrder_from(Integer order_from) {
		this.order_from = order_from;
	}
	public Double getActual_pay() {
		return actual_pay;
	}
	public void setActual_pay(Double actual_pay) {
		this.actual_pay = actual_pay;
	}
	public String getKey_type() {
		return key_type;
	}
	public void setKey_type(String key_type) {
		this.key_type = key_type;
	}
	public String getKey_value() {
		return key_value;
	}
	public void setKey_value(String key_value) {
		this.key_value = key_value;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public MultipartFile getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}
}
