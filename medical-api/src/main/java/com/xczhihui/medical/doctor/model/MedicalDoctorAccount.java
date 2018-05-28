package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_doctor_account")
public class MedicalDoctorAccount extends Model<MedicalDoctorAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师帐号关系表
     */
	private String id;
    /**
     * 医师id
     */
	@TableField("doctor_id")
	private String doctorId;
    /**
     * 帐号id
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
		return "MedicalDoctorAccount{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", accountId=" + accountId +
			", createTime=" + createTime +
			"}";
	}
}
