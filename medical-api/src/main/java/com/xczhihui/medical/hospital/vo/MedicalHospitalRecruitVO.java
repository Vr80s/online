package com.xczhihui.medical.hospital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public class MedicalHospitalRecruitVO implements Serializable {

    /**
     * 医馆招聘表
     */
    private String id;
    /**
     * 医馆id
     */
    private String hospitalId;
    private String city;
    /**
     * 医馆名称
     */
    private String hospitalName;
    /**
     * 职位
     */
    private String position;
    /**
     * 工作经验 0.不限 1.0-1年 2.1-3年 3.3-5年 4.5-10年 5.10年以上
     */
    private String years;
    /**
     * 岗位职责
     */
    private String postDuties;
    /**
     * 职位要求
     */
    private String jobRequirements;

    private Boolean status;

    private Date updateTime;

    private Date publicTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public String getPostDuties() {
        if (postDuties == null) {
            return null;
        }
        postDuties = postDuties.replace("\n\n", "<br/>");
        postDuties = postDuties.replace("\n", "<br/>");
        return postDuties;
    }

    public void setPostDuties(String postDuties) {
        this.postDuties = postDuties;
    }

    public String getJobRequirements() {
        if (jobRequirements == null) {
            return null;
        }
        jobRequirements = jobRequirements.replace("\n\n", "<br/>");
        jobRequirements = jobRequirements.replace("\n", "<br/>");
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MedicalHospitalRecruitVo{" +
                ", id=" + id +
                ", hospitalId=" + hospitalId +
                ", position=" + position +
                ", years=" + years +
                ", postDuties=" + postDuties +
                ", jobRequirements=" + jobRequirements +
                "}";
    }
}
