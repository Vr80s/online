package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 医师的远程诊疗列表的VO
 *
 * @author hejiwei
 */
public class DoctorTreatmentVO implements Serializable {

    private Integer id;

    private String nickname;

    private String avatar;

    @JsonFormat(pattern = "yyyy年-MM月-dd日", timezone = "GMT+8")
    private Date date;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private String dateText;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
