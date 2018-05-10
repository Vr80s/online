package com.xczhihui.medical.hospital.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 医馆公告
 *
 * @author hejiwei
 */
@TableName("medical_hospital_announcement")
public class MedicalHospitalAnnouncement implements Serializable {

    @TableId
    private String id;

    @TableField("hospital_id")
    private String hospitalId;

    private String content;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_person")
    private String createPerson;

    private Boolean deleted;

    public MedicalHospitalAnnouncement() {
    }

    public MedicalHospitalAnnouncement(String hospitalId, String content) {
        this.hospitalId = hospitalId;
        this.content = content;
        this.deleted = false;
        this.setCreateTime(new Date());
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
