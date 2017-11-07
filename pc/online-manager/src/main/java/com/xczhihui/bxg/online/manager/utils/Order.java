package com.xczhihui.bxg.online.manager.utils;

/**
 * @category 排序方式
 * @author zoujun
 *
 */
public class Order {
	private String field;
	private SortType sortType;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public SortType getSortType() {
		return sortType;
	}
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}
	
	public enum SortType{
		ASC,DESC
	}
}


