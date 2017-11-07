package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity;

/**
 * 学员故事表	
 * @author yxd
 */
@Entity
@Table(name = "student_story")
public class StudentStory extends BasicEntity implements Serializable {

	// Fields
	@Column(name = "head_img")
	private String headImg;
	@Column(name = "name")
	private String name;
	@Column(name = "course")
	private String course;
	@Column(name = "company")
	private String company;
	@Column(name = "salary")
	private Integer salary;
	@Column(name = "introduce")
	private String introduce;
	@Column(name = "title")
	private String title;
	@Column(name = "text")
	private String text;
	@Column(name = "menu_id")
	private Integer menuId;
	@Column(name = "course_type_id")
	private String courseTypeId;
	@Column(name = "other_name")
	private String otherName;
	@Column(name = "use_other_name")
	private Boolean useOtherName;
	
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getCourseTypeId() {
		return courseTypeId;
	}
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public Boolean getUseOtherName() {
		return useOtherName;
	}
	public void setUseOtherName(Boolean useOtherName) {
		this.useOtherName = useOtherName;
	}



}