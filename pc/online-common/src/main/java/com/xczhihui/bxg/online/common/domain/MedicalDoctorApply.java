package com.xczhihui.bxg.online.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医师入驻申请实体类
 * @author zhuwenbao
 */

@Entity
@Table(name = "medical_doctor_apply")
public class MedicalDoctorApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医师认证申请表
     */
    @Id
	private String id;
    /**
     * 姓名
     */
	private String name;
    /**
     * 职称
     */
	private String title;
    /**
     * 医师简介
     */
	private String description;
    /**
     * 联系电话
     */
	private String tel;
    /**
     * 用户表id
     */
	@Column(name = "user_id")
	private String userId;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 详细地址
     */
	@Column(name = "detailed_address")
	private String detailedAddress;
    /**
     * 真实头像
     */
	@Column(name = "head_portrait")
	private String headPortrait;
    /**
     * 职称证明
     */
	@Column(name = "title_prove")
	private String titleProve;
    /**
     * 身份证正面
     */
	@Column(name = "card_positive")
	private String cardPositive;
    /**
     * 身份证反面
     */
	@Column(name = "card_negative")
	private String cardNegative;
    /**
     * 医师资格证
     */
	@Column(name = "qualification_certificate")
	private String qualificationCertificate;
    /**
     * 医师执业证书
     */
	@Column(name = "professional_certificate")
	private String professionalCertificate;
    /**
     * 0拒绝1通过2未处理
     */
	private Integer status;
    /**
     * 1已删除0未删除
     */
	private Boolean deleted;
    /**
     * 创建时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
    /**
     * 创建人id
     */
	@Column(name = "create_person")
	private String createPerson;
    /**
     * 更新时间
     */
	@Column(name = "update_time")
	private Date updateTime;
    /**
     * 更新人id
     */
	@Column(name = "update_person")
	private String updatePerson;
    /**
     * 版本
     */
	private String version;
    /**
     * 备注
     */
	private String remark;
	/**
	 * 身份证号
	 */
	@Column(name = "card_num")
	private String cardNum;

	/**
	 * 擅长领域
	 */
	private String field;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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


	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	@Override
	public String toString() {
		return "MedicalDoctorApply{" +
			", id=" + id +
			", name=" + name +
			", title=" + title +
			", description=" + description +
			", tel=" + tel +
			", userId=" + userId +
			", province=" + province +
			", city=" + city +
			", detailedAddress=" + detailedAddress +
			", headPortrait=" + headPortrait +
			", titleProve=" + titleProve +
			", cardPositive=" + cardPositive +
			", cardNegative=" + cardNegative +
			", qualificationCertificate=" + qualificationCertificate +
			", professionalCertificate=" + professionalCertificate +
			", status=" + status +
			", deleted=" + deleted +
			", createTime=" + createTime +
			", createPerson=" + createPerson +
			", updateTime=" + updateTime +
			", updatePerson=" + updatePerson +
			", version=" + version +
			", remark=" + remark +
			"}";
	}
}
