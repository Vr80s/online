package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 医师认证申请科室关系表
 * </p>
 *
 * @author zhuwenbao
 * @since 2018-01-15
 */
@TableName("medical_doctor_apply_department")
public class MedicalDoctorApplyDepartment extends Model<MedicalDoctorApplyDepartment> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师认证申请科室关系表
     */
	private String id;

	/**
     * 医师认证申请id
     */
	@TableField("doctor_apply_id")
	private String doctorApplyId;

	/**
     * 科室id
     */
	@TableField("department_id")
	private String departmentId;

    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoctorApplyId() {
		return doctorApplyId;
	}

	public void setDoctorApplyId(String doctorApplyId) {
		this.doctorApplyId = doctorApplyId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public MedicalDoctorApplyDepartment(String departmentId) {
		this.departmentId = departmentId;
	}

	public MedicalDoctorApplyDepartment() {
	}
}
