package com.xczhihui.bxg.online.web.body;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.medical.hospital.model.MedicalHospitalRecruit;

/**
 * 医馆招聘参数封装
 *
 * @author hejiwei
 */
public class MedicalHospitalRecruitBody {

    private String hospitalId;

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

    public MedicalHospitalRecruit build() {
        MedicalHospitalRecruit medicalHospitalRecruit = new MedicalHospitalRecruit();
        if (Integer.parseInt(years) < 0 || Integer.parseInt(years) >5) {
            throw new IllegalArgumentException("years 参数不合法");
        }
        medicalHospitalRecruit.setCreateTime(new Date());
        medicalHospitalRecruit.setDeleted(false);
        medicalHospitalRecruit.setHospitalId(hospitalId);
        medicalHospitalRecruit.setJobRequirements(jobRequirements);
        medicalHospitalRecruit.setPosition(position);
        medicalHospitalRecruit.setPostDuties(postDuties);
        medicalHospitalRecruit.setRecommend(false);
        medicalHospitalRecruit.setYears(years);
        medicalHospitalRecruit.setStatus(false);
        return medicalHospitalRecruit;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
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
}
