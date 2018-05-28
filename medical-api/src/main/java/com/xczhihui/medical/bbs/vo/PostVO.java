package com.xczhihui.medical.bbs.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * bbs 帖子VO类
 *
 * @author hejiwei
 */
public class PostVO implements Serializable {

    private Integer id;

    private Integer labelId;

    private String title;

    private String content;

    private Date initTime;

    private Boolean top;

    private Boolean good;

    private Boolean hot;

    private String userId;

    private Integer replyCount;

    private Integer browseCount;

    private Integer reportOrder;

    private Boolean deleted;

    private String name;

    private String smallHeadPhoto;

    private String labelName;

    private List<String> imgArray;

    @Override
    public String toString() {
        return "PostVO{" +
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
                ", name='" + name + '\'' +
                '}';
    }

    public String getSmallHeadPhoto() {
        return smallHeadPhoto;
    }

    public void setSmallHeadPhoto(String smallHeadPhoto) {
        this.smallHeadPhoto = smallHeadPhoto;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getReportOrder() {
        return reportOrder;
    }

    public void setReportOrder(Integer reportOrder) {
        this.reportOrder = reportOrder;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<String> getImgArray() {
        return imgArray;
    }

    public void setImgArray(List<String> imgArray) {
        this.imgArray = imgArray;
    }
}
