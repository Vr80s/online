package com.xczhihui.medical.hospital.model;

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
@TableName("medical_hospital_field")
public class MedicalHospitalField extends Model<MedicalHospitalField> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆医疗领域关系表
     */
	private String id;

    /**
     * 医馆id
     */
	@TableField("hospital_id")
	private String hospitalId;

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

	/**
	 * 是否删除（1：是 0：否）
	 */
	@TableField("deleted")
	private Boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalHospitalField{" +
			", id=" + id +
			", hospitalId=" + hospitalId +
			", fieldId=" + fieldId +
			", createTime=" + createTime +
			"}";
	}
}
