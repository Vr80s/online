package com.xczhihui.course.vo;

public class QueryConditionVo {

	private static final long serialVersionUID = 1L;

	private String city;

	private Integer isFree;

	private Integer lineState;
	
	private Integer courseType;
   
	private Integer menuType;
	
	private String queryKey;
	
	private Integer pageNumber;
	   
	private Integer pageSize;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public Integer getLineState() {
		return lineState;
	}

	public void setLineState(Integer lineState) {
		this.lineState = lineState;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
