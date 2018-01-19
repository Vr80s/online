package com.xczhihui.wechat.course.vo;

public class FocusVo {

	private String id; 				//主键id
	private String lecturerId;  	//老师
	private String userId;      	//用户id 
	private Integer courseId;   	//课程id
	private String lecturerName;	//老师名字
	private String userName;        //用户名字
	private String userHeadImg;     //用户头像
	private String lecturerHeadImg; //老师头像
	
	private String fansCount;       //粉丝数
	private Integer isFocus;	    //是否关注  0：未关注  1:已关注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getLecturerName() {
		return lecturerName;
	}
	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHeadImg() {
		return userHeadImg;
	}
	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}
	public String getLecturerHeadImg() {
		return lecturerHeadImg;
	}
	public void setLecturerHeadImg(String lecturerHeadImg) {
		this.lecturerHeadImg = lecturerHeadImg;
	}
	public String getFansCount() {
		return fansCount;
	}
	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}
	public Integer getIsFocus() {
		return isFocus;
	}
	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}
	
}
