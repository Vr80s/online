package com.xczh.consumer.market.bean;

import java.io.Serializable;
import java.util.Date;

public class WxcpCourse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String create_person;
	private Date create_time;
	private Boolean is_delete;
	private String bigimg_path;
	private String cloud_classroom;
	private String description;
	private String detailimg_path;
	private String grade_name;
	private Date graduate_time;
	private Date live_time;
	private String smallimg_path;
	private Integer sort;
	private String courseType;
	private Integer status;
	private Integer learnd_count;
	private Double original_cost;
	private Double current_price;
	private String course_length;
	
	private Integer menu_id;
	private String course_type_id;
	private Boolean is_free;
	private String class_template;
	private String course_detail;
	private String course_outline;
	private String common_problem;
	private Integer lecturer_id;
	private Integer is_recommend;
	private Integer recommend_sort;
	private String qqno;
	private String description_show;
	private String rec_img_path;
	private Date start_time;
	private Date end_time;
	private Integer assistant_id;
	private Integer direct_seeding;
	private String direct_id;
	private String external_links;
	private String flowers_number;
	private Integer pv;
	private Integer highest_number_line;
	private Integer end_line_number;
	private Integer type;
	private String teacher_img_path;
	private Integer course_type;
	private Integer grade_student_sum;
	private Integer default_student_count;
	private String grade_qq;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreate_person() {
		return create_person;
	}
	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Boolean getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Boolean is_delete) {
		this.is_delete = is_delete;
	}
	public String getBigimg_path() {
		return bigimg_path;
	}
	public void setBigimg_path(String bigimg_path) {
		this.bigimg_path = bigimg_path;
	}
	public String getCloud_classroom() {
		return cloud_classroom;
	}
	public void setCloud_classroom(String cloud_classroom) {
		this.cloud_classroom = cloud_classroom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetailimg_path() {
		return detailimg_path;
	}
	public void setDetailimg_path(String detailimg_path) {
		this.detailimg_path = detailimg_path;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public Date getGraduate_time() {
		return graduate_time;
	}
	public void setGraduate_time(Date graduate_time) {
		this.graduate_time = graduate_time;
	}
	public Date getLive_time() {
		return live_time;
	}
	public void setLive_time(Date live_time) {
		this.live_time = live_time;
	}
	public String getSmallimg_path() {
		return smallimg_path;
	}
	public void setSmallimg_path(String smallimg_path) {
		this.smallimg_path = smallimg_path;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLearnd_count() {
		return learnd_count;
	}
	public void setLearnd_count(Integer learnd_count) {
		this.learnd_count = learnd_count;
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
	public String getCourse_length() {
		return course_length;
	}
	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public String getCourse_type_id() {
		return course_type_id;
	}
	public void setCourse_type_id(String course_type_id) {
		this.course_type_id = course_type_id;
	}
	public Boolean getIs_free() {
		return is_free;
	}
	public void setIs_free(Boolean is_free) {
		this.is_free = is_free;
	}
	public String getClass_template() {
		return class_template;
	}
	public void setClass_template(String class_template) {
		this.class_template = class_template;
	}
	public String getCourse_detail() {
		return course_detail;
	}
	public void setCourse_detail(String course_detail) {
		this.course_detail = course_detail;
	}
	public String getCourse_outline() {
		return course_outline;
	}
	public void setCourse_outline(String course_outline) {
		this.course_outline = course_outline;
	}
	public String getCommon_problem() {
		return common_problem;
	}
	public void setCommon_problem(String common_problem) {
		this.common_problem = common_problem;
	}
	public Integer getLecturer_id() {
		return lecturer_id;
	}
	public void setLecturer_id(Integer lecturer_id) {
		this.lecturer_id = lecturer_id;
	}
	public Integer getIs_recommend() {
		return is_recommend;
	}
	public void setIs_recommend(Integer is_recommend) {
		this.is_recommend = is_recommend;
	}
	public Integer getRecommend_sort() {
		return recommend_sort;
	}
	public void setRecommend_sort(Integer recommend_sort) {
		this.recommend_sort = recommend_sort;
	}
	public String getQqno() {
		return qqno;
	}
	public void setQqno(String qqno) {
		this.qqno = qqno;
	}
	public String getDescription_show() {
		return description_show;
	}
	public void setDescription_show(String description_show) {
		this.description_show = description_show;
	}
	public String getRec_img_path() {
		return rec_img_path;
	}
	public void setRec_img_path(String rec_img_path) {
		this.rec_img_path = rec_img_path;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Integer getAssistant_id() {
		return assistant_id;
	}
	public void setAssistant_id(Integer assistant_id) {
		this.assistant_id = assistant_id;
	}
	public Integer getDirect_seeding() {
		return direct_seeding;
	}
	public void setDirect_seeding(Integer direct_seeding) {
		this.direct_seeding = direct_seeding;
	}
	public String getDirect_id() {
		return direct_id;
	}
	public void setDirect_id(String direct_id) {
		this.direct_id = direct_id;
	}
	public String getExternal_links() {
		return external_links;
	}
	public void setExternal_links(String external_links) {
		this.external_links = external_links;
	}
	public String getFlowers_number() {
		return flowers_number;
	}
	public void setFlowers_number(String flowers_number) {
		this.flowers_number = flowers_number;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getHighest_number_line() {
		return highest_number_line;
	}
	public void setHighest_number_line(Integer highest_number_line) {
		this.highest_number_line = highest_number_line;
	}
	public Integer getEnd_line_number() {
		return end_line_number;
	}
	public void setEnd_line_number(Integer end_line_number) {
		this.end_line_number = end_line_number;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTeacher_img_path() {
		return teacher_img_path;
	}
	public void setTeacher_img_path(String teacher_img_path) {
		this.teacher_img_path = teacher_img_path;
	}
	public Integer getCourse_type() {
		return course_type;
	}
	public void setCourse_type(Integer course_type) {
		this.course_type = course_type;
	}
	public Integer getGrade_student_sum() {
		return grade_student_sum;
	}
	public void setGrade_student_sum(Integer grade_student_sum) {
		this.grade_student_sum = grade_student_sum;
	}
	public Integer getDefault_student_count() {
		return default_student_count;
	}
	public void setDefault_student_count(Integer default_student_count) {
		this.default_student_count = default_student_count;
	}
	public String getGrade_qq() {
		return grade_qq;
	}
	public void setGrade_qq(String grade_qq) {
		this.grade_qq = grade_qq;
	}
	
	
}
