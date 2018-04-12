package com.xczhihui.medical.hospital.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hejiwei
 */
public class MedicalHospitalAnnouncementVO implements Serializable {

    private String id;

    private String content;

    private Date createTime;

    private String hospitalId;

    @Override
    public String toString() {
        return "MedicalHospitalAnnouncementVO{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", hospitalId='" + hospitalId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
