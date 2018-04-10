package com.xczhihui.bbs.vo;

public class BBSLabelVo {

	private int id;

	private String name;

	private boolean isDisable;

	private Integer sort;

	private String details;

	private String labelImgUrl;

	private Integer postsCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean disable) {
		isDisable = disable;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLabelImgUrl() {
		return labelImgUrl;
	}

	public void setLabelImgUrl(String labelImgUrl) {
		this.labelImgUrl = labelImgUrl;
	}

	public Integer getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(Integer postsCount) {
		this.postsCount = postsCount;
	}
}
