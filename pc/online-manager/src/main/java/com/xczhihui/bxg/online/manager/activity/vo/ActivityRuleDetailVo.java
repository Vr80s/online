package com.xczhihui.bxg.online.manager.activity.vo;

public class ActivityRuleDetailVo {
	
	private String id;
	private String ruleId;
    private String courseId;
    private java.util.Date startTime;
    private java.util.Date endTime;
    private Double reachMoney;
    private Double minusMoney;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	public Double getReachMoney() {
		return reachMoney;
	}
	public void setReachMoney(Double reachMoney) {
		this.reachMoney = reachMoney;
	}
	public Double getMinusMoney() {
		return minusMoney;
	}
	public void setMinusMoney(Double minusMoney) {
		this.minusMoney = minusMoney;
	}
}
