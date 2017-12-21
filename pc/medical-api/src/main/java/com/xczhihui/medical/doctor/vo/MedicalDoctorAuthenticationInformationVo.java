package com.xczhihui.medical.doctor.vo;


import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalDoctorAuthenticationInformationVo implements Serializable {

    /**
     * 医师认证信息表
     */
	private String id;
    /**
     * 头像
     */
	private String headPortrait;
    /**
     * 职称证明
     */
	private String titleProve;
    /**
     * 身份证正面
     */
	private String cardPositive;
    /**
     * 身份证反面
     */
	private String cardNegative;
    /**
     * 医师资格证
     */
	private String qualificationCertificate;
    /**
     * 职业医师证
     */
	private String professionalCertificate;

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

	@Override
	public String toString() {
		return "MedicalDoctorAuthenticationInformationVo{" +
			", id=" + id +
			", headPortrait=" + headPortrait +
			", titleProve=" + titleProve +
			", cardPositive=" + cardPositive +
			", cardNegative=" + cardNegative +
			", qualificationCertificate=" + qualificationCertificate +
			", professionalCertificate=" + professionalCertificate +
			"}";
	}
}
