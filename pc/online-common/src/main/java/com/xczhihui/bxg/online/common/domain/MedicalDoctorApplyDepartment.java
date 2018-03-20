package com.xczhihui.bxg.online.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医师认证申请科室关系表
 * @author zhuwenbao
 */
@Entity(name = "medical_doctor_apply_department")
@Table(name = "medical_doctor_apply_department")
public class MedicalDoctorApplyDepartment implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 医师认证申请科室关系表
     */
    @Id
	private String id;

	/**
     * 医师认证申请id
     */
	@Column(name = "doctor_apply_id")
	private String doctorApplyId;

	/**
     * 科室id
     */
	@Column(name = "department_id")
	private String departmentId;

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
