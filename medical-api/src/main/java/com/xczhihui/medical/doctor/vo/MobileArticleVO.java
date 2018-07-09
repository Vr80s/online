package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author hejiwei
 */
public class MobileArticleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章类型,外键引用
     */
    private String typeId;
    /**
     * 图片
     */
    private String imgPath;
    private String author;

    private Integer status;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    private String url;

    private String buyLink;

    private String type;

    private String contentUrl;

    @Override
    public String toString() {
        return "MobileArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", typeId='" + typeId + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", url='" + url + '\'' +
                ", buyLink='" + buyLink + '\'' +
                ", type='" + type + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
