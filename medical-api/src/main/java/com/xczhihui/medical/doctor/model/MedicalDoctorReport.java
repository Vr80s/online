package com.xczhihui.medical.doctor.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
@TableName("medical_doctor_report")
public class MedicalDoctorReport extends Model<MedicalDoctorReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师-报道关系表
     */
    private String id;
    /**
     * 医师id
     */
    @TableField("doctor_id")
    private String doctorId;
    /**
     * 文章报道id
     */
    @TableField("article_id")
    private String articleId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MedicalDoctorReport{" +
                "id='" + id + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
