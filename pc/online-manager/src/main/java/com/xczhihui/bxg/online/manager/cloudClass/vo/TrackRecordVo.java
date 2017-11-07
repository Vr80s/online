package com.xczhihui.bxg.online.manager.cloudClass.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by 1 on 2016/9/7.
 */
public class TrackRecordVo {

    private String id;
    /**
     * 学员ID号
     */
    private String  applyId;

    /**
     * 记录的顺序
     */
    private Integer sort;

    /**
     * 是否启用:true:启用  false：禁用
     */
    private boolean status;

    private Integer gradeId;

    /**
     * 记录时间
     */
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "GMT+8")
    private Date recordTime;

    /**
     * 记录内容
     */
    private String recordContent;

    /**
     * 讲师Id号
     */
    private Integer  lecturerId;
    private boolean isDelete;
    private String createPerson;
    private Date createTime;

    private String applyGradeId;
    private String lecturerName;

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getApplyGradeId() {
        return applyGradeId;
    }

    public void setApplyGradeId(String applyGradeId) {
        this.applyGradeId = applyGradeId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}
