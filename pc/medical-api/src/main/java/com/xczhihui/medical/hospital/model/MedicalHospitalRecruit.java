package com.xczhihui.medical.hospital.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
@TableName("medical_hospital_recruit")
public class MedicalHospitalRecruit extends Model<MedicalHospitalRecruit> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆招聘表
     */
    private String id;
    /**
     * 医馆id
     */
    @TableField("hospital_id")
    private String hospitalId;
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
    @TableField("post_duties")
    private String postDuties;
    /**
     * 职位要求
     */
    @TableField("job_requirements")
    private String jobRequirements;
    /**
     * 1已删除0未删除
     */
    private Boolean deleted;
    /**
     * 启用状态
     */
    private Boolean status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人id
     */
    @TableField("create_person")
    private String createPerson;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 更新人id
     */
    @TableField("update_person")
    private String updatePerson;
    /**
     * 版本
     */
    private String version;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否推荐
     */
    private Boolean recommend;
    /**
     * 医馆招聘排序字段
     */
    private Integer sort;


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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
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
                ", deleted=" + deleted +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createPerson=" + createPerson +
                ", updateTime=" + updateTime +
                ", updatePerson=" + updatePerson +
                ", version=" + version +
                ", remark=" + remark +
                ", recommend=" + recommend +
                ", sort=" + sort +
                "}";
    }
}
