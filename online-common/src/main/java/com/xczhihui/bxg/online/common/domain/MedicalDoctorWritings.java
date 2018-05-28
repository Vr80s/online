package com.xczhihui.bxg.online.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the medical_doctor_field database table.
 * 
 */
@Entity
@Table(name="medical_doctor_writings")
@NamedQuery(name="MedicalDoctorWritings.findAll", query="SELECT m FROM MedicalDoctorWritings m")
public class MedicalDoctorWritings implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="doctor_id")
	private String doctorId;

	@Column(name="writings_id")
	private String writingsId;

	public MedicalDoctorWritings() {
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

	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getWritingsId() {
		return writingsId;
	}

	public void setWritingsId(String writingsId) {
		this.writingsId = writingsId;
	}



}