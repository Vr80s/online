package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @ClassName: MedicalEnrollmentRegulations
 * @Description: 师承-报名
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/21 15:06
 **/
@Entity
@Table(name = "medical_entry_information")
public class MedicalEntryInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 师承模块开发
     */
    @Column(name = "mer_id")
    private Integer merId;
    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;
    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;
    /**
     * 男1 女0 未知2
     */
    @Column(name = "sex")
    private Integer sex;
    /**
     * 省-市
     */
    @Column(name = "native_place")
    private String nativePlace;
    /**
     * 1小学、2初中、3高中、4大专、5本科、6研究生、7博士生、8博士后
     */
    @Column(name = "education")
    private Integer education;
    /**
     * 学习经历
     */
    @Column(name = "education_experience")
    private String educationExperience;
    /**
     * 行医经历
     */
    @Column(name = "medical_experience")
    private String medicalExperience;
    /**
     * 学中医的目标
     */
    @Column(name = "goal")
    private String goal;
    /**
     * 手机号
     */
    @Column(name = "tel")
    private String tel;
    /**
     * 微信号
     */
    @Column(name = "wechat")
    private String wechat;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 是否删除
     */
    @Column(name = "deleted")
    private boolean deleted;
    /**
     * 是否为徒弟
     */
    @Column(name = "apprentice")
    private Integer apprentice;

    /**
     * 报名截止时间
     */
    @Transient
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date deadline;

    private Boolean applied;
    /**
     * 创建时间
     */
    @Transient
    private String createTimeStr;
    /**
     * 性别
     */
    @Transient
    private String sexStr;
    /**
     * 学历
     */
    @Transient
    private String educationStr;
    /**
     * 是否为徒弟
     */
    @Transient
    private String apprenticeStr;
    /**
     * 报名截止时间str
     */
    @Transient
    private String deadlineStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerId() {
        return merId;
    }

    public void setMerId(Integer merId) {
        this.merId = merId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getEducationExperience() {
        return educationExperience;
    }

    public void setEducationExperience(String educationExperience) {
        this.educationExperience = educationExperience;
    }

    public String getMedicalExperience() {
        return medicalExperience;
    }

    public void setMedicalExperience(String medicalExperience) {
        this.medicalExperience = medicalExperience;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getEducationStr() {
        return educationStr;
    }

    public void setEducationStr(String educationStr) {
        this.educationStr = educationStr;
    }

    public Integer getApprentice() {
        return apprentice;
    }

    public void setApprentice(Integer apprentice) {
        this.apprentice = apprentice;
    }

    public String getApprenticeStr() {
        return apprenticeStr;
    }

    public void setApprenticeStr(String apprenticeStr) {
        this.apprenticeStr = apprenticeStr;
    }

    public String getDeadlineStr() {
        return deadlineStr;
    }

    public void setDeadlineStr(String deadlineStr) {
        this.deadlineStr = deadlineStr;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }
}