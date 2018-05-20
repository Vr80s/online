package com.xczhihui.course.vo;

import java.io.Serializable;

public class QueryConditionVo  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String city;

	private Integer isFree;

	private Integer lineState;
	
	private Integer courseType;
   
	private String menuType;
	
	private String queryKey;
	
	private Integer pageNumber =1;
	   
	private Integer pageSize = 20;
	
	private Integer sortOrder;
	

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
		if (pageNumber > 1) {
            this.pageNumber = pageNumber;
        }
		if (pageNumber > 0) {
			setPageSize((pageNumber - 1) * pageSize);
        }
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
}
