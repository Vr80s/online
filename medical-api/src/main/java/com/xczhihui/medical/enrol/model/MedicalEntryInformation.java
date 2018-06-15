package com.xczhihui.medical.enrol.model;

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
 * @since 2018-05-22
 */
@TableName("medical_entry_information")
public class MedicalEntryInformation extends Model<MedicalEntryInformation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 师承模块开发
     */
    @TableField("mer_id")
    private Integer merId;
    private String name;
    private Integer age;
    /**
     * 男1 女0 未知2
     */
    private Integer sex;
    /**
     * 省-市
     */
    @TableField("native_place")
    private String nativePlace;
    /**
     * 1小学、2初中、3高中、4大专、5本科、6研究生、7博士生、8博士后
     */
    private Integer education;
    /**
     * 学习经历
     */
    @TableField("education_experience")
    private String educationExperience;
    /**
     * 行医经历
     */
    @TableField("medical_experience")
    private String medicalExperience;
    /**
     * 学中医的目标
     */
    private String goal;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    @TableField("create_time")
    private Date createTime;
    private Boolean deleted;


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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MedicalEntryInformation{" +
                ", id=" + id +
                ", merId=" + merId +
                ", name=" + name +
                ", age=" + age +
                ", sex=" + sex +
                ", nativePlace=" + nativePlace +
                ", education=" + education +
                ", educationExperience=" + educationExperience +
                ", medicalExperience=" + medicalExperience +
                ", goal=" + goal +
                ", tel=" + tel +
                ", wechat=" + wechat +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                "}";
    }

}
