package com.xczhihui.bxg.online.manager.fcode.vo;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FcodeVo {
	private String id;
	private String lotNo;//批次号
	private String name;//兑换活动名称
	private Integer fcodeSum;//兑换码数量
	private Integer clockFcodeSum;//已绑定数量
	private Integer usedFcodeSum;//已兑换数量
	private String subjectIds;//学科范围
	private String subjectNames;//学科范围名字
	private String includeCourses;//课程范围
	private String includeCourseIds;//课程范围Id
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date startTime;//开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date endTime;//结束时间
    private String createPerson;
    private String createPersonName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date createTime;//创建时间
    private Integer auto;
    private Boolean startFlag;
    private Boolean endFlag;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private java.util.Date expiryTime;
	
	private String fcode;//F码
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private java.util.Date lockTime;//绑定时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private java.util.Date  useTime;//使用时间
	private Integer status;//F码状态
	private String usedCourseId;//兑换了哪些课程Id
	private String CourseName;//兑换了哪些课程Name
	private String loginName;//使用人(帐号)
	
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFcodeSum() {
		return fcodeSum;
	}
	public void setFcodeSum(Integer fcodeSum) {
		this.fcodeSum = fcodeSum;
	}
	public Integer getClockFcodeSum() {
		return clockFcodeSum;
	}
	public void setClockFcodeSum(Integer clockFcodeSum) {
		this.clockFcodeSum = clockFcodeSum;
	}
	public Integer getUsedFcodeSum() {
		return usedFcodeSum;
	}
	public void setUsedFcodeSum(Integer usedFcodeSum) {
		this.usedFcodeSum = usedFcodeSum;
	}
	public String getIncludeCourses() {
		return includeCourses;
	}
	public void setIncludeCourses(String includeCourses) {
		this.includeCourses = includeCourses;
	}
	
	public String getIncludeCourseIds() {
		return includeCourseIds;
	}
	public void setIncludeCourseIds(String includeCourseIds) {
		this.includeCourseIds = includeCourseIds;
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
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getSubjectIds() {
		return subjectIds;
	}
	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}
	public Integer getAuto() {
		return auto;
	}
	public void setAuto(Integer auto) {
		this.auto = auto;
	}
	public String getSubjectNames() {
		return subjectNames;
	}
	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}
	public Boolean getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(Boolean startFlag) {
		this.startFlag = startFlag;
	}
	public Boolean getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(Boolean endFlag) {
		this.endFlag = endFlag;
	}
	public java.util.Date getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(java.util.Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	public java.util.Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(java.util.Date lockTime) {
		this.lockTime = lockTime;
	}
	public java.util.Date getUseTime() {
		return useTime;
	}
	public void setUseTime(java.util.Date useTime) {
		this.useTime = useTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUsedCourseId() {
		return usedCourseId;
	}
	public void setUsedCourseId(String usedCourseId) {
		this.usedCourseId = usedCourseId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	
	
}
