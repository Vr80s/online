package com.xczhihui.medical.doctor.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_doctor_question")
public class MedicalDoctorQuestion extends Model<MedicalDoctorQuestion> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师答疑表
     */
    @TableId(type = IdType.AUTO)
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
    @TableField("user_id")
    private String userId;

    /**
     * 医师id
     */
    @TableField("doctor_id")
    private String doctorId;

    /**
     * 1已删除0未删除
     */
    private Boolean deleted;
    
    /**
     * 1启用0禁用
     */
    private Boolean status;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

 
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    
    
    /**
     * 用户头像
     */
    @TableField(exist = false)
    private String userImg;
    
    
    /**
     * 医师头像
     */
    @TableField(exist = false)
    private String doctorImg;
    
    
    /**
     * 提问者名字
     */
    @TableField(exist = false)
    private String quizzerName;
    


    @Override
    protected Serializable pkVal() {
        return this.id;
    }





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


    public String getUserImg() {
        return userImg;
    }


    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


    public String getDoctorImg() {
        return doctorImg;
    }


    public void setDoctorImg(String doctorImg) {
        this.doctorImg = doctorImg;
    }


    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }

    
    

    public String getQuizzerName() {
        return quizzerName;
    }


    public void setQuizzerName(String quizzerName) {
        this.quizzerName = quizzerName;
    }





    @Override
    public String toString() {
        return "MedicalDoctorQuestion [id=" + id + ", question=" + question + ", answer=" + answer + ", userId="
                + userId + ", doctorId=" + doctorId + ", deleted=" + deleted + ", status=" + status + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", userImg=" + userImg + ", doctorImg=" + doctorImg
                + ", quizzerName=" + quizzerName + "]";
    }


    

    
    
}
