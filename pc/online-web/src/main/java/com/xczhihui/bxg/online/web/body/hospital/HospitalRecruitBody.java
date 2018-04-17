package com.xczhihui.bxg.online.web.body.hospital;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.medical.hospital.model.MedicalHospitalRecruit;

/**
 * 医馆招聘参数封装
 *
 * @author hejiwei
 */
public class HospitalRecruitBody {

    @NotBlank
    private String position;

    @NotBlank
    @Length(max = 1)
    private String years;

    @NotBlank
    @Length(max = 1000)
    private String postDuties;

    @NotBlank
    @Length(max = 1000)
    private String jobRequirements;

    private boolean status;

    public MedicalHospitalRecruit build(String hospitalId) {
        MedicalHospitalRecruit medicalHospitalRecruit = new MedicalHospitalRecruit();
        if (Integer.parseInt(years) < 0 || Integer.parseInt(years) > 5) {
            throw new IllegalArgumentException("years 参数不合法");
        }
        Date time = new Date();
        medicalHospitalRecruit.setCreateTime(time);
        medicalHospitalRecruit.setDeleted(false);
        medicalHospitalRecruit.setJobRequirements(jobRequirements);
        medicalHospitalRecruit.setPosition(position);
        medicalHospitalRecruit.setPostDuties(postDuties);
        medicalHospitalRecruit.setRecommend(false);
        medicalHospitalRecruit.setYears(years);
        //默认发布
        medicalHospitalRecruit.setStatus(status);
        medicalHospitalRecruit.setHospitalId(hospitalId);
        medicalHospitalRecruit.setPublicTime(time);
        return medicalHospitalRecruit;
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

    public String getPostDuties() {
        return postDuties;
    }

    public void setPostDuties(String postDuties) {
        this.postDuties = postDuties;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
