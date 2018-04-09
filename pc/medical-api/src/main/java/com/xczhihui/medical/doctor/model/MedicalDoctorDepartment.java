package com.xczhihui.medical.model.entity;

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
 * @since 2018-04-08
 */
@TableName("medical_doctor_department")
public class MedicalDoctorDepartment extends Model<MedicalDoctorDepartment> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师科室关系表
     */
	private String id;
    /**
     * 医师id
     */
	@TableField("doctor_id")
	private String doctorId;
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
	private Boolean deleted;
	@TableField("update_time")
	private Date updateTime;


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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalDoctorDepartment{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", departmentId=" + departmentId +
			", createTime=" + createTime +
			", deleted=" + deleted +
			", updateTime=" + updateTime +
			"}";
	}
}
