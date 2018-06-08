package com.xczhihui.course.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author hejiwei
 */
public class LineCourseApplyStudentVO implements Serializable {

    private String id;

    private Integer sex;

    private String mobile;

    private String wechatNo;

    private String courseName;

    private Boolean learned;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private String realName;

    private String anchorName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Boolean getLearned() {
        return learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    @Override
    public String toString() {
        return "LineCourseApplyStudentVO{" +
                "id='" + id + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                ", courseName='" + courseName + '\'' +
                ", learned=" + learned +
                ", createTime=" + createTime +
                ", realName='" + realName + '\'' +
                '}';
    }
}
