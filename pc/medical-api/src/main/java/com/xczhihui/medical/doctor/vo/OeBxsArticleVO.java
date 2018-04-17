package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public class OeBxsArticleVO implements Serializable {

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
    /**
     * banner图片
     */
    private String bannerPath;
    private String author;
    private Date updateTime;

    private Integer status;

    /**
     * 阅读量
     */
    private Integer browseSum;
    /**
     * 点赞数
     */
    private Integer praiseSum;
    /**
     * 评论数
     */
    private Integer commentSum;
    /**
     * 是否推荐，1推荐，0不推荐
     */
    private Boolean isRecommend;

    private Date createTime;

    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private List<MedicalDoctorVO> medicalDoctors;

    public List<MedicalDoctorVO> getMedicalDoctors() {
        return medicalDoctors;
    }

    public void setMedicalDoctors(List<MedicalDoctorVO> medicalDoctors) {
        this.medicalDoctors = medicalDoctors;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public Integer getBrowseSum() {
        return browseSum;
    }

    public void setBrowseSum(Integer browseSum) {
        this.browseSum = browseSum;
    }

    public Integer getPraiseSum() {
        return praiseSum;
    }

    public void setPraiseSum(Integer praiseSum) {
        this.praiseSum = praiseSum;
    }

    public Integer getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Integer commentSum) {
        this.commentSum = commentSum;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OeBxsArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", typeId='" + typeId + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", bannerPath='" + bannerPath + '\'' +
                ", author='" + author + '\'' +
                ", browseSum=" + browseSum +
                ", praiseSum=" + praiseSum +
                ", commentSum=" + commentSum +
                ", isRecommend=" + isRecommend +
                ", createTime=" + createTime +
                ", medicalDoctors=" + medicalDoctors +
                '}';
    }
}
