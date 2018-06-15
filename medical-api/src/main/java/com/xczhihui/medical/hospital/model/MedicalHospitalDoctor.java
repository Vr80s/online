package com.xczhihui.medical.hospital.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
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
@TableName("medical_hospital_doctor")
public class MedicalHospitalDoctor extends Model<MedicalHospitalDoctor> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆医师关系表
     */
	private String id;

	/**
     * 医师id
     */
	@TableField("doctor_id")
	private String doctorId;

	/**
     * 医馆id
     */
	@TableField("hospital_id")
	private String hospitalId;

	/**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 删除状态
	 */
	@TableLogic
	private String deleted;

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

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

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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
		return "MedicalHospitalDoctor{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", hospitalId=" + hospitalId +
			", createTime=" + createTime +
			"}";
	}
}
