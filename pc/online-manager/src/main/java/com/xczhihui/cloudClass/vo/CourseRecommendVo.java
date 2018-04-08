package com.xczhihui.cloudClass.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class CourseRecommendVo extends OnlineBaseVo  {
	
	private String id;
	private Integer showCourseId;
    private Integer recCourseId;
    private Integer sort;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
