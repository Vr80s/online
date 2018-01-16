package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 医师申请表医疗领域关系表
 * </p>
 *
 * @author zhuwenbao
 * @since 2018-01-15
 */
@TableName("medical_doctor_apply_field")
public class MedicalDoctorApplyField extends Model<MedicalDoctorApplyField> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师申请表医疗领域关系表
     */
	private String id;

	/**
     * 医师认证申请id
     */
	@TableField("doctor_apply_id")
	private String doctorApplyId;

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

	public MedicalDoctorApplyField(String fieldId) {
		this.fieldId = fieldId;
	}

	public MedicalDoctorApplyField() {
	}
}
