package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @ClassName: MedicalEnrollmentRegulations
 * @Description: 师承-招生简章
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/21 15:06
 **/
@Entity
@Table(name = "medical_enrollment_regulations")
public class MedicalEnrollmentRegulations implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 招生简章
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 医师id
     */
    @Column(name = "doctor_id")
    private String doctorId;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 封面
     */
    @Column(name = "cover_img")
    private String coverImg;
    /**
     * 宣传语
     */
    @Column(name = "propaganda")
    private String propaganda;
    /**
     * 老师超短介绍
     */
    @Column(name = "doctor_introduction")
    private String doctorIntroduction;
    /**
     * 学费
     */
    @Column(name = "tuition")
    private String tuition;
    /**
     * 招生人数
     */
    @Column(name = "count_limit")
    private String countLimit;
    /**
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @Column(name = "deadline")
    private Date deadline;
    /**
     * 学习流程
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "learning_process")
    private String learningProcess;
    /**
     * 学习开始时间
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 学习截至时间
     */
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 学习地址（省-市-区-详细地址）
     */
    @Column(name = "study_address")
    private String studyAddress;
    /**
     * 弟子权益
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "rights_and_interests")
    private String rightsAndInterests;
    /**
     * 拜师资格
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "qualification")
    private String qualification;
    /**
     * 拜师地址
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "ceremony_address")
    private String ceremonyAddress;
    /**
     * 招生简章
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "regulations")
    private String regulations;
    /**
     * 报名表附件
     */
    @Column(name = "entry_form_attachment")
    private String entryFormAttachment;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建者
     */
    @Column(name = "creator")
    private String creator;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 更新人
     */
    @Column(name = "updator")
    private String updator;
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
     * 海报
     */
    @Column(name = "poster_img")
    private String posterImg;
    /**
     * 联系人信息
     */
    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    @Column(name = "contact_way")
    private String contactWay;
    /**
     * 报名人数
     */
    @Transient
    private Integer countPeople;
    /**
     * 医师
     */
    @Transient
    private String doctorName;
    /**
     * 报名截止时间string
     */
    @Transient
    private String deadlineStr;
    /**
     * 开始时间string
     */
    @Transient
    private String startTimeStr;
    /**
     * 结束时间string
     */
    @Transient
    private String endTimeStr;
    /**
     * 结束时间string
     */
    @Transient
    private String createTimeStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getPropaganda() {
        return propaganda;
    }

    public void setPropaganda(String propaganda) {
        this.propaganda = propaganda;
    }

    public String getDoctorIntroduction() {
        return doctorIntroduction;
    }

    public void setDoctorIntroduction(String doctorIntroduction) {
        this.doctorIntroduction = doctorIntroduction;
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

    public String getLearningProcess() {
        return learningProcess;
    }

    public void setLearningProcess(String learningProcess) {
        this.learningProcess = learningProcess;
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

    public String getRightsAndInterests() {
        return rightsAndInterests;
    }

    public void setRightsAndInterests(String rightsAndInterests) {
        this.rightsAndInterests = rightsAndInterests;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(Integer countPeople) {
        this.countPeople = countPeople;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDeadlineStr() {
        return deadlineStr;
    }

    public void setDeadlineStr(String deadlineStr) {
        this.deadlineStr = deadlineStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}