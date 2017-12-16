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
@TableName("medical_doctor_field")
public class MedicalDoctorField extends Model<MedicalDoctorField> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师医疗领域关系表
     */
	private String id;
    /**
     * 医师id
     */
	@TableField("doctor_id")
	private String doctorId;
    /**
     * 医疗领域id
     */
	@TableField("field_id")
	private String fieldId;
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

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
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
		return "MedicalDoctorField{" +
			", id=" + id +
			", doctorId=" + doctorId +
			", fieldId=" + fieldId +
			", createTime=" + createTime +
			"}";
	}
}
