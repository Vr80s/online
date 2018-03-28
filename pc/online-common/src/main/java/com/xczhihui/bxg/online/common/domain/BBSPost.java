package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 帖子
 *
 * @author hejiwei
 */
@Entity
@Table(name = "quark_posts")
public class BBSPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //与标签的关系
    @Column(nullable = false, name = "label_id")
    private Integer labelId;

    //标题
    @Column(unique = true, nullable = false)
    private String title;

    //内容
    @Column(columnDefinition = "text")
    private String content;

    //发布时间
    @Column(nullable = false, name = "init_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initTime;

    //是否置顶
    @Column(name = "top")
    private boolean top;

    //是否精华
    @Column(name = "good")
    private boolean good;

    //是否热门
    @Column(name = "hot")
    private boolean hot;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reply_count")
    private int replyCount = 0;

    //浏览次数
    @Column(name = "browse_count")
    private int browseCount;

    //回复最大值
    @Column(name = "report_order")
    private int reportOrder;

    //是否删除这个帖子
    @Column(name = "is_delete")
    private boolean isDelete;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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
}
