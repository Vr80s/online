package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity;

/**
 * 章节实体类
 * 
 * @author yxd
 */
@Entity
@Table(name = "oe_chapter")
public class Chapter extends BasicEntity implements Serializable {
	@Column(name = "status") // 1已启用 0已禁用
	private Integer status;
	@Column(name = "sort")
	private Integer sort;
	@Column(name = "name")
	private String name;
	@Column(name = "parent_id")
	private String parentId;
	@Column(name = "course_id")
	private Integer courseId;
	@Column(name = "level")
	private Integer level;

	@Column(name = "barrier_id")
	private String barrierId;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getBarrierId() {
		return barrierId;
	}

	public void setBarrierId(String barrierId) {
		this.barrierId = barrierId;
	}
}
