package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author hejiwei
 */
public class TreatmentVO implements Serializable {

    private Integer id;

    @JsonFormat(pattern = "yyyy年-MM月-dd日", timezone = "GMT+8")
    private Date date;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private String dateText;

    private String tel;

    private String name;

    private Integer status;

    private Boolean appointed;

    private String indexDateText;

    private String question;

    private String week;

    private String userId;

    private Integer apprenticeId;

    @Override
    public String toString() {
        return "TreatmentVO{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dateText='" + dateText + '\'' +
                ", tel='" + tel + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAppointed() {
        return appointed;
    }

    public void setAppointed(Boolean appointed) {
        this.appointed = appointed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIndexDateText() {
        return indexDateText;
    }

    public void setIndexDateText(String indexDateText) {
        this.indexDateText = indexDateText;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getApprenticeId() {
        return apprenticeId;
    }

    public void setApprenticeId(Integer apprenticeId) {
        this.apprenticeId = apprenticeId;
    }
}
