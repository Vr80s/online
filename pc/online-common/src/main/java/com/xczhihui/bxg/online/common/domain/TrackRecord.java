package com.xczhihui.bxg.online.common.domain;/**
 * Created by admin on 2016/9/7.
 */

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 学员跟踪记录表实体
 *
 * @author 康荣彩
 * @create 2016-09-07 15:17
 */
@Entity
@Table(name = "oe_track_record")
public class TrackRecord extends BasicEntity {

    /**
     * 学员ID号
     */
    @Column(name = "apply_id")
    private String  applyId;

    /**
     * 记录的顺序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 是否启用:true:启用  false：禁用
     */
    @Column(name = "status")
    private boolean status;

    /**
     * 记录时间
     */
    @Column(name="record_time")
    private Date recordTime;

    /**
     * 记录内容
     */
    @Column(name="record_content")
    private String recordContent;

    /**
     * 讲师Id号
     */
    @Column(name = "lecturer_id")
    private Integer  lecturerId;

    @Column(name = "grade_id")
    private Integer gradeId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
}
