package com.xczhihui.medical.doctor.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_doctor_authentication_information")
public class MedicalDoctorAuthenticationInformation extends Model<MedicalDoctorAuthenticationInformation> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师认证信息表
     */
	private String id;
    /**
     * 头像
     */
	@TableField("head_portrait")
	private String headPortrait;
    /**
     * 职称证明
     */
	@TableField("title_prove")
	private String titleProve;
    /**
     * 身份证正面
     */
	@TableField("card_positive")
	private String cardPositive;
    /**
     * 身份证反面
     */
	@TableField("card_negative")
	private String cardNegative;
    /**
     * 医师资格证
     */
	@TableField("qualification_certificate")
	private String qualificationCertificate;
    /**
     * 职业医师证
     */
	@TableField("professional_certificate")
	private String professionalCertificate;
    /**
     * 1已删除0未删除
     */
	private Boolean deleted;
    /**
     * 启用状态
     */
	private Boolean status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 创建人id
     */
	@TableField("create_person")
	private String createPerson;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 更新人id
     */
	@TableField("update_person")
	private String updatePerson;
    /**
     * 版本
     */
	private String version;
    /**
     * 备注
     */
	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getTitleProve() {
		return titleProve;
	}

	public void setTitleProve(String titleProve) {
		this.titleProve = titleProve;
	}

	public String getCardPositive() {
		return cardPositive;
	}

	public void setCardPositive(String cardPositive) {
		this.cardPositive = cardPositive;
	}

	public String getCardNegative() {
		return cardNegative;
	}

	public void setCardNegative(String cardNegative) {
		this.cardNegative = cardNegative;
	}

	public String getQualificationCertificate() {
		return qualificationCertificate;
	}

	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}

	public String getProfessionalCertificate() {
		return professionalCertificate;
	}

	public void setProfessionalCertificate(String professionalCertificate) {
		this.professionalCertificate = professionalCertificate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalDoctorAuthenticationInformation{" +
			", id=" + id +
			", headPortrait=" + headPortrait +
			", titleProve=" + titleProve +
			", cardPositive=" + cardPositive +
			", cardNegative=" + cardNegative +
			", qualificationCertificate=" + qualificationCertificate +
			", professionalCertificate=" + professionalCertificate +
			", deleted=" + deleted +
			", status=" + status +
			", createTime=" + createTime +
			", createPerson=" + createPerson +
			", updateTime=" + updateTime +
			", updatePerson=" + updatePerson +
			", version=" + version +
			", remark=" + remark +
			"}";
	}
}
