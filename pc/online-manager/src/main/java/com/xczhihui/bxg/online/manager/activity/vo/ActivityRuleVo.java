package com.xczhihui.bxg.online.manager.activity.vo;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivityRuleVo  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
    private String url;
    private String subjectIds;
    private String subjectNames;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date endTime;
    private String createPerson;
    private String createPersonName;
    private String courseIds;
    private String reachMoneys;
    private String minusMoneys;
    private String ruleContent;
    private String courseNames;
    private String ruleMoneys;
    private Integer actOrderSum;
    private Integer actUserSum;
    private Double actOrderMoneyTotal;
    private Boolean isEdit;
    private Boolean isEnd;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSubjectIds() {
		return subjectIds;
	}
	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getSubjectNames() {
		return subjectNames;
	}
	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public String getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}
	public String getReachMoneys() {
		return reachMoneys;
	}
	public void setReachMoneys(String reachMoneys) {
		this.reachMoneys = reachMoneys;
	}
	public String getMinusMoneys() {
		return minusMoneys;
	}
	public void setMinusMoneys(String minusMoneys) {
		this.minusMoneys = minusMoneys;
	}
	public String getRuleContent() {
		return ruleContent;
	}
	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	public String getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(String courseNames) {
		this.courseNames = courseNames;
	}
	public String getRuleMoneys() {
		return ruleMoneys;
	}
	public void setRuleMoneys(String ruleMoneys) {
		this.ruleMoneys = ruleMoneys;
	}
	public Integer getActOrderSum() {
		return actOrderSum;
	}
	public void setActOrderSum(Integer actOrderSum) {
		this.actOrderSum = actOrderSum;
	}
	public Integer getActUserSum() {
		return actUserSum;
	}
	public void setActUserSum(Integer actUserSum) {
		this.actUserSum = actUserSum;
	}
	public Double getActOrderMoneyTotal() {
		return actOrderMoneyTotal;
	}
	public void setActOrderMoneyTotal(Double actOrderMoneyTotal) {
		this.actOrderMoneyTotal = actOrderMoneyTotal;
	}
	public Boolean getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
}
