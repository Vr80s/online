package com.xczhihui.medical.enrol.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("medical_enrollment_regulations")
public class MedicalEnrollmentRegulations extends Model<MedicalEnrollmentRegulations> {

    private static final long serialVersionUID = 1L;

    /**
     * 师承-招生简章
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 医师id
     */
    @TableField("doctor_id")
    private String doctorId;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    @TableField("cover_img")
    private String coverImg;
    /**
     * 宣传语
     */
    private String propaganda;
    /**
     * 老师超短介绍
     */
    @TableField("doctor_introduction")
    private String doctorIntroduction;
    /**
     * 学费
     */
    private String tuition;
    /**
     * 招生人数
     */
    @TableField("count_limit")
    private String countLimit;
    /**
     * 报名截至时间
     */
    private Date deadline;
    /**
     * 学习流程
     */
    @TableField("learning_process")
    private String learningProcess;
    /**
     * 学习开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 学习截至时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 学习地址（省-市-区-详细地址）
     */
    @TableField("study_address")
    private String studyAddress;
    /**
     * 弟子权益
     */
    @TableField("rights_and_interests")
    private String rightsAndInterests;
    /**
     * 拜师资格
     */
    private String qualification;
    /**
     * 拜师地址
     */
    @TableField("ceremony_address")
    private String ceremonyAddress;
    @TableField("poster_img")
    private String posterImg;
    /**
     * 招生简章
     */
    private String regulations;
    /**
     * 报名表附件
     */
    @TableField("entry_form_attachment")
    private String entryFormAttachment;
    @TableField("contact_way")
    private String contactWay;
    @TableField("create_time")
    private Date createTime;
    private String creator;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    /**
     * 1启用0禁用
     */
    private Boolean status;
    /**
     * 1已删除0未删除
     */
    private Boolean deleted;

    @TableField(exist = false)
    private boolean enrolled;
    @TableField(exist = false)
    private List<String> allEnrolledUser;
    @TableField(exist = false)
    private int allEnrolledUserCount;

    @TableField(exist = false)
    private String doctorPhoto;

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getDoctorPhoto() {
        return doctorPhoto;
    }

    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    public int getAllEnrolledUserCount() {
        return allEnrolledUserCount;
    }

    public void setAllEnrolledUserCount(int allEnrolledUserCount) {
        this.allEnrolledUserCount = allEnrolledUserCount;
    }

    public List<String> getAllEnrolledUser() {
        return allEnrolledUser;
    }

    public void setAllEnrolledUser(List<String> allEnrolledUser) {
        this.allEnrolledUser = allEnrolledUser;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }

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

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
        return "MedicalEnrollmentRegulations{" +
                ", id=" + id +
                ", doctorId=" + doctorId +
                ", title=" + title +
                ", coverImg=" + coverImg +
                ", propaganda=" + propaganda +
                ", doctorIntroduction=" + doctorIntroduction +
                ", tuition=" + tuition +
                ", countLimit=" + countLimit +
                ", deadline=" + deadline +
                ", learningProcess=" + learningProcess +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", studyAddress=" + studyAddress +
                ", rightsAndInterests=" + rightsAndInterests +
                ", qualification=" + qualification +
                ", ceremonyAddress=" + ceremonyAddress +
                ", regulations=" + regulations +
                ", entryFormAttachment=" + entryFormAttachment +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", status=" + status +
                ", deleted=" + deleted +
                "}";
    }
}
