package com.xczhihui.medical.bbs.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * @author hejiwei
 */
@TableName(value = "quark_posts")
public class Post implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("label_id")
    private Integer labelId;

    private String title;

    private String content;

    @TableField("init_time")
    private Date initTime;

    private boolean top;

    private boolean good;

    private boolean hot;

    private String userId;

    @TableField("reply_count")
    private int replyCount;

    @TableField("browse_count")
    private int browseCount;

    @TableField("report_order")
    private int reportOrder;

    @TableField("is_delete")
    private boolean deleted;

    @TableField("update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public int getReportOrder() {
        return reportOrder;
    }

    public void setReportOrder(int reportOrder) {
        this.reportOrder = reportOrder;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", initTime=" + initTime +
                ", top=" + top +
                ", good=" + good +
                ", hot=" + hot +
                ", userId='" + userId + '\'' +
                ", replyCount=" + replyCount +
                ", browseCount=" + browseCount +
                ", reportOrder=" + reportOrder +
                ", deleted=" + deleted +
                '}';
    }
}
