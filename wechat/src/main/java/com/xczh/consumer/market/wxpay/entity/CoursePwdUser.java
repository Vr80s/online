package com.xczh.consumer.market.wxpay.entity;

public class CoursePwdUser {
    private Long id;

    private Integer courseId;

    private String userId;

    private Integer pwdFalg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPwdFalg() {
        return pwdFalg;
    }

    public void setPwdFalg(Integer pwdFalg) {
        this.pwdFalg = pwdFalg;
    }
}