package com.xczhihui.bxg.online.common.domain;
import com.xczhihui.bxg.common.support.domain.BasicEntity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "oe_course_recommend")
public class CourseRecommend extends BasicEntity2  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7259352700253158186L;

	@Column(name = "show_course_id")
	private Integer showCourseId;
	
	@Column(name = "rec_course_id")
    private Integer recCourseId;
    
	@Column(name = "sort")
    private Integer sort;

	public Integer getShowCourseId() {
		return showCourseId;
	}

	public void setShowCourseId(Integer showCourseId) {
		this.showCourseId = showCourseId;
	}

	public Integer getRecCourseId() {
		return recCourseId;
	}

	public void setRecCourseId(Integer recCourseId) {
		this.recCourseId = recCourseId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
