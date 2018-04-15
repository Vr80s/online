package com.xczhihui.bxg.online.web.body.doctor;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.medical.doctor.model.MedicalWriting;

/**
 * 医师著作
 *
 * @author hejiwei
 */
public class MedicalDoctorWritingBody {

    @NotBlank(message = "作者不能为空")
    private String author;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "购买链接不能为空")
    private String buyLink;

    @NotBlank(message = "封面图片不能为空")
    private String imgPath;

    @NotBlank(message = "内容不能为空")
    private String remark;

    public MedicalWriting build(String userId) {
        MedicalWriting medicalWriting = new MedicalWriting();
        medicalWriting.setAuthor(author);
        medicalWriting.setBuyLink(buyLink);
        medicalWriting.setCreatePerson(userId);
        medicalWriting.setCreateTime(new Date());
        medicalWriting.setDeleted(false);
        medicalWriting.setImgPath(imgPath);
        medicalWriting.setStatus(true);
        medicalWriting.setTitle(title);
        medicalWriting.setRemark(remark);
        return medicalWriting;
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
}
