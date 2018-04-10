package com.xczhihui.course.vo;

import java.util.Date;

import javax.persistence.Column;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by admin on 2016/7/27.
 */
public class CourseLecturVo extends OnlineBaseVo {

	/**
	 * 课程ID
	 */
	private int id;
	/**
	 * 课程名
	 */
	private String gradeName;
	/**
	 * 直播时间
	 */
	@JsonFormat(pattern = "MM/dd HH:mm", timezone = "GMT+8")
	private Date liveTime;
	/**
	 * 课程小图
	 */
	private String smallImgPath;
	/**
	 * 课程详情图
	 */
	private String detailImgPath;
	/**
	 * 课程大图
	 */
	private String bigImgPath;
	/**
	 * 课程描述
	 */
	private String description;

	/**
	 * 课程结业时间
	 */
	@JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
	private Date graduateTime;
	/**
	 * 云课堂链接
	 */
	private String cloudClassroom;

	/**
	 * 课程类型:1录播 2:直播
	 */
	private Integer type;

	/**
	 * 课程类型 1：直播课 2:公开课 3:基础课
	 */
	@Column(name = "courseType")
	private Integer courseType;

	/**
	 * 讲师名
	 */
	private String name;

	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	private Date createTime;

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}

	public String getSmallImgPath() {
		return smallImgPath;
	}

	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}

	public String getDetailImgPath() {
		return detailImgPath;
	}

	public void setDetailImgPath(String detailImgPath) {
		this.detailImgPath = detailImgPath;
	}

	public String getBigImgPath() {
		return bigImgPath;
	}

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCloudClassroom() {
		return cloudClassroom;
	}

	public void setCloudClassroom(String cloudClassroom) {
		this.cloudClassroom = cloudClassroom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	public Date getGraduateTime() {
		return graduateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
