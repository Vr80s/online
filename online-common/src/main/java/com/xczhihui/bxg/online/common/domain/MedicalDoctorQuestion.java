package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the medical_field database table.
 */
@Entity
@Table(name = "medical_doctor_question")
public class MedicalDoctorQuestion implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 医师答疑表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 问题描述
     */
    @Column(columnDefinition = "longtext")
    private String question;

    /**
     * 回答内容
     */
    @Column(columnDefinition = "longtext")
    private String answer;

    /**
     * 提问人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 医师id
     */
    @Column(name = "doctor_id")
    private String doctorId;

    /**
     * 1启用0禁用
     */
    @Column(name = "status")
    private boolean status;
    /**
     * 1已删除0未删除
     */
    @Column(name = "deleted")
    private boolean deleted;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;
 
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }


    public String getAnswer() {
        return answer;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getDoctorId() {
        return doctorId;
    }


    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }


    public Boolean getDeleted() {
        return deleted;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


   


}