package com.xczhihui.bxg.online.web.body.doctor;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.bxg.common.util.enums.HeadlineType;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * @author hejiwei
 */
public class DoctorArticleBody {

    @NotBlank
    private String title;

    @NotBlank
    private String imgPath;

    @NotBlank
    private String content;

    private int status;

    private String reportDoctor;

    public OeBxsArticle build(HeadlineType headlineType, String userId) {
        OeBxsArticle oeBxsArticle = new OeBxsArticle();
        //实际是医师名称
        oeBxsArticle.setUserId(userId);
        oeBxsArticle.setDelete(false);
        oeBxsArticle.setCreateTime(new Date());
        oeBxsArticle.setContent(content);
        oeBxsArticle.setBrowseSum(0);
        oeBxsArticle.setCommentSum(0);
        oeBxsArticle.setImgPath(imgPath);
        oeBxsArticle.setPraiseSum(0);
        oeBxsArticle.setUpdateTime(new Date());
        oeBxsArticle.setTypeId(headlineType.getCode());
        oeBxsArticle.setStatus(status);
        oeBxsArticle.setTitle(title);
        return oeBxsArticle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(String reportDoctor) {
        this.reportDoctor = reportDoctor;
    }
}
