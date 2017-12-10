package com.xczhihui.bxg.online.manager.medical.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medical_doctor_authentication_information database table.
 * 
 */
@Entity
@Table(name="medical_doctor_authentication_information")
@NamedQuery(name="MedicalDoctorAuthenticationInformation.findAll", query="SELECT m FROM MedicalDoctorAuthenticationInformation m")
public class MedicalDoctorAuthenticationInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="card_negative")   //身份证反面
	private String cardNegative;

	@Column(name="card_positive")   //身份证正面
	private String cardPositive;

	@Column(name="create_person")
	private String createPerson;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private boolean deleted;

	@Column(name="head_portrait")
	private String headPortrait;     //头像

	@Column(name="professional_certificate")    //职业医师证
	private String professionalCertificate;

	@Column(name="qualification_certificate")   //医师资格证
	private String qualificationCertificate;

	private String remark;

	private boolean status;

	@Column(name="title_prove")      // 职称证明
	private String titleProve;

	@Column(name="update_person")
	private String updatePerson;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String version;

	public MedicalDoctorAuthenticationInformation() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNegative() {
		return this.cardNegative;
	}

	public void setCardNegative(String cardNegative) {
		this.cardNegative = cardNegative;
	}

	public String getCardPositive() {
		return this.cardPositive;
	}

	public void setCardPositive(String cardPositive) {
		this.cardPositive = cardPositive;
	}

	public String getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getHeadPortrait() {
		return this.headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getProfessionalCertificate() {
		return this.professionalCertificate;
	}

	public void setProfessionalCertificate(String professionalCertificate) {
		this.professionalCertificate = professionalCertificate;
	}

	public String getQualificationCertificate() {
		return this.qualificationCertificate;
	}

	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTitleProve() {
		return this.titleProve;
	}

	public void setTitleProve(String titleProve) {
		this.titleProve = titleProve;
	}

	public String getUpdatePerson() {
		return this.updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}