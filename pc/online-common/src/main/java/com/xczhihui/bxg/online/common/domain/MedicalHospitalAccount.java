package com.xczhihui.bxg.online.common.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医馆帐号关系对应表
 * @author zhuwenbao
 */
@Entity(name = "medical_hospital_account")
@Table(name = "medical_hospital_account")
public class MedicalHospitalAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医师帐号关系表
     */
    @Id
	private String id;

    /**
     * 医馆id
     */
	@Column(name = "doctor_id")
	private String doctorId;

    /**
     * 帐号id
     */
	@Column(name = "account_id")
	private String accountId;

	/**
     * 创建时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MedicalDoctorAccount{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", accountId=" + accountId +
			", createTime=" + createTime +
			"}";
	}
}
