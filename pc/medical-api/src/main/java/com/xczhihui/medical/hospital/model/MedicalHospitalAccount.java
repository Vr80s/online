package com.xczhihui.medical.hospital.model;

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
@TableName("medical_hospital_account")
public class MedicalHospitalAccount extends Model<MedicalHospitalAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆账号关系表
     */
	private String id;
    /**
     * 医馆id
     */
	@TableField("doctor_id")
	private String doctorId;
    /**
     * 账号id
     */
	@TableField("account_id")
	private String accountId;
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
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalHospitalAccount{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", accountId=" + accountId +
			", createTime=" + createTime +
			"}";
	}
}
