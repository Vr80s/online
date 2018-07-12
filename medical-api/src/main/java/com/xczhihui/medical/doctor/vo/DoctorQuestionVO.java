package com.xczhihui.medical.doctor.vo;

import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class DoctorQuestionVO{


    /**
     * 医师答疑表
     */
    private Integer id;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 回答内容
     */
    private String answer;

    /**
     * 提问人id
     */
    private String userId;

    /**
     * 医师id
     */
    private String doctorId;

    /**
     * 1已删除0未删除
     */
    private Boolean deleted;
    
    /**
     * 1已删除0未删除
     */
    private Boolean status;


    /**
     * 创建时间
     */
    private Date createTime;

 
    /**
     * 更新时间
     */
    private Date updateTime;
    
    
    /**
     * 更新时间
     */
    private String doctorName;
    
    
    
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

    
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DoctorQuestionVO [id=" + id + ", question=" + question + ", answer=" + answer + ", userId=" + userId
                + ", doctorId=" + doctorId + ", deleted=" + deleted + ", status=" + status + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", doctorName=" + doctorName + "]";
    }

   
    
}
