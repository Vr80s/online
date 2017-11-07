package com.xczhihui.bxg.online.manager.cloudClass.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by admin on 2016/8/2.
 */
public class GradeDetailVo {

    /**
     * 课程ID号
     */
    private Integer id;
    /**
     * 班级名
     */
    private String name;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 学科
     */
    private String menuId;

    /**
     * 课程类别
     */
    private String scoreTypeId;

    /**
     * 授课方式
     */
    private String teachMethodId;

    /**
     *开课时间
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date curriculumTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;
    /**
     *班级状态
     */
    private Integer status;
    /**
     * 剩余席位书
     */
    private Integer seat;
    /**
     * 课程名称
     */
    private String couseName;

    /**
     *班级状态 :0禁用 1启用
     */
    private Integer gradeStatus;

    public String getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(String scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    /**
     *联系QQ
     */
    private String qqno;

    /**
     *联系QQ
     */
    private Integer sort;

    /**
     *课程id号
     */
    private Integer course_id;
    
    private Integer defaultStudentCount;

    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCurriculumTime() {
        return curriculumTime;
    }


    public void setCurriculumTime(Date curriculumTime) {
        this.curriculumTime = curriculumTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getCouseName() {
        return couseName;
    }

    public void setCouseName(String couseName) {
        this.couseName = couseName;
    }

    public Integer getGradeStatus() {
        return gradeStatus;
    }

    public void setGradeStatus(Integer gradeStatus) {
        this.gradeStatus = gradeStatus;
    }

    public Date getStopTime() {
        return stopTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getQqno() {
        return qqno;
    }

    public void setQqno(String qqno) {
        this.qqno = qqno;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getTeachMethodId() {
        return teachMethodId;
    }

    public void setTeachMethodId(String teachMethodId) {
        this.teachMethodId = teachMethodId;
    }

	public Integer getDefaultStudentCount() {
		return defaultStudentCount;
	}

	public void setDefaultStudentCount(Integer defaultStudentCount) {
		this.defaultStudentCount = defaultStudentCount;
	}
    
}
