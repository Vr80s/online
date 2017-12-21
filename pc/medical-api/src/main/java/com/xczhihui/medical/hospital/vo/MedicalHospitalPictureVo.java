package com.xczhihui.medical.hospital.vo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalHospitalPictureVo implements Serializable {

    /**
     * 医馆图片表
     */
	private String id;
    /**
     * 医馆id
     */
	private String hospitalId;
    /**
     * 图片地址
     */
	private String picture;


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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "MedicalHospitalPictureVo{" +
			", id=" + id +
			", hospitalId=" + hospitalId +
			", picture=" + picture +
			"}";
	}
}
