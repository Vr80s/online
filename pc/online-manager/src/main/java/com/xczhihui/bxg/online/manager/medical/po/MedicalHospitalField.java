package com.xczhihui.bxg.online.manager.medical.po;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medical_hospital_field database table.
 * 
 */
@Entity
@Table(name="medical_hospital_field")
@NamedQuery(name="MedicalHospitalField.findAll", query="SELECT m FROM MedicalHospitalField m")
public class MedicalHospitalField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="field_id")
	private String fieldId;

	@Column(name="hospital_id")
	private String hospitalId;

	public MedicalHospitalField() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}