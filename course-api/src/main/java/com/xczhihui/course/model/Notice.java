package com.xczhihui.course.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author hejiwei
 */
@TableName("oe_notice")
public class Notice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("notice_content")
    private String noticeContent;

    @TableField("create")
    private String createPerson;

    @TableField("is_delete")
    private Boolean deleted;

    @TableField("create_time")
    private Date createTime;

    @TableField("show_start_time")
    private Date showStartTime;

    @TableField("show_end_time")
    private Date showEndTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(Date showStartTime) {
        this.showStartTime = showStartTime;
    }

    public Date getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(Date showEndTime) {
        this.showEndTime = showEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
