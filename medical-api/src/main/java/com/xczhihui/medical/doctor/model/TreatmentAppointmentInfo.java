package com.xczhihui.medical.doctor.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author hejiwei
 */
@TableName("medical_treatment_appointment_info")
public class TreatmentAppointmentInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tel;

    private String name;

    @TableField("user_id")
    private String userId;

    @TableField("create_time")
    private Date createTime;

    @TableField("treatment_id")
    private Integer treatmentId;

    private String question;

    @TableField("apprentice_id")
    private Integer apprenticeId;
    
    @TableField("status")
    private Integer status;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @TableField(value = "start_time")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;

    @TableField(value = "end_time")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    @TableField("treatment_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date treatmentStartTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Integer getApprenticeId() {
        return apprenticeId;
    }

    public void setApprenticeId(Integer apprenticeId) {
        this.apprenticeId = apprenticeId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTreatmentStartTime() {
        return treatmentStartTime;
    }

    public void setTreatmentStartTime(Date treatmentStartTime) {
        this.treatmentStartTime = treatmentStartTime;
    }
}
