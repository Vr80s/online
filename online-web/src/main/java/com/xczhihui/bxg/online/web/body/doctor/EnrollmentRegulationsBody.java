package com.xczhihui.bxg.online.web.body.doctor;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;

/**
 * @author hejiwei
 */
public class EnrollmentRegulationsBody implements Serializable {

    /**
     * 标题
     */
    @NotBlank
    private String title;
    /**
     * 封面
     */
    @NotBlank
    private String coverImg;
    /**
     * 学费
     */
    @NotBlank
    private String tuition;
    /**
     * 招生人数
     */
    @NotBlank
    private String countLimit;
    /**
     * 报名截至时间
     */
    @NotNull
    private Date deadline;
    /**
     * 学习开始时间
     */
    @NotNull
    private Date startTime;
    /**
     * 学习截至时间
     */
    @NotNull
    private Date endTime;
    /**
     * 学习地址（省-市-区-详细地址）
     */
    @NotBlank
    private String studyAddress;
    /**
     * 相关介绍/拜师地址
     */
    @NotBlank
    private String ceremonyAddress;
    /**
     * 招生简章
     */
    @NotBlank
    private String regulations;
    /**
     * 报名表附件
     */
    @NotBlank
    private String entryFormAttachment;

    private boolean status;

    public MedicalEnrollmentRegulations build(String userId, String doctorId) {
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = new MedicalEnrollmentRegulations();
        medicalEnrollmentRegulations.setDeleted(false);
        medicalEnrollmentRegulations.setTitle(title);
        medicalEnrollmentRegulations.setStatus(this.status);
        medicalEnrollmentRegulations.setCeremonyAddress(ceremonyAddress);
        medicalEnrollmentRegulations.setCountLimit(countLimit);
        medicalEnrollmentRegulations.setDeadline(deadline);
        medicalEnrollmentRegulations.setCoverImg(coverImg);
        medicalEnrollmentRegulations.setTuition(tuition);
        medicalEnrollmentRegulations.setStudyAddress(this.studyAddress);
        medicalEnrollmentRegulations.setRegulations(regulations);
        medicalEnrollmentRegulations.setEntryFormAttachment(entryFormAttachment);
        medicalEnrollmentRegulations.setDeleted(false);
        medicalEnrollmentRegulations.setUpdateTime(new Date());
        medicalEnrollmentRegulations.setDoctorId(doctorId);
        medicalEnrollmentRegulations.setCreator(userId);
        medicalEnrollmentRegulations.setStartTime(startTime);
        medicalEnrollmentRegulations.setEndTime(endTime);
        medicalEnrollmentRegulations.setCreateTime(new Date());
        return medicalEnrollmentRegulations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTuition() {
        return tuition;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public String getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(String countLimit) {
        this.countLimit = countLimit;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public String getStudyAddress() {
        return studyAddress;
    }

    public void setStudyAddress(String studyAddress) {
        this.studyAddress = studyAddress;
    }

    public String getCeremonyAddress() {
        return ceremonyAddress;
    }

    public void setCeremonyAddress(String ceremonyAddress) {
        this.ceremonyAddress = ceremonyAddress;
    }

    public String getRegulations() {
        return regulations;
    }

    public void setRegulations(String regulations) {
        this.regulations = regulations;
    }

    public String getEntryFormAttachment() {
        return entryFormAttachment;
    }

    public void setEntryFormAttachment(String entryFormAttachment) {
        this.entryFormAttachment = entryFormAttachment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
