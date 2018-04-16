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
 * @since 2017-12-09
 */
public class MedicalWritingVO implements Serializable {

    /**
     * 著作表
     */
    private String id;
    private String articleId;
    private String author;
    private String title;
    private String buyLink;
    private String imgPath;
    private String remark;
    private boolean status;
    private Date updateTime;
    private List<MedicalDoctorVO> medicalDoctors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<MedicalDoctorVO> getMedicalDoctors() {
        return medicalDoctors;
    }

    public void setMedicalDoctors(List<MedicalDoctorVO> medicalDoctors) {
        this.medicalDoctors = medicalDoctors;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MedicalWritingVO{" +
                "id='" + id + '\'' +
                ", articleId='" + articleId + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", buyLink='" + buyLink + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", medicalDoctors=" + medicalDoctors +
                '}';
    }
}
