package com.xczhihui.medical.hospital.vo;

import com.xczhihui.medical.field.vo.MedicalFieldVo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalHospitalVo implements Serializable{

    /**
     * 医馆表
     */
	private String id;
    /**
     * 经纬度
     */
	private String lal;
    /**
     * 医馆名称
     */
	private String name;
    /**
     * 医馆简介
     */
	private String description;
    /**
     * 联系电话
     */
	private String tel;

	private String email;
    /**
     * 邮编
     */
	private Integer postCode;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 详细地址
     */
	private String detailedAddress;

	/**
	 * 是否已认证
	 */
	private Boolean authentication;
	/**
	 * 分值
	 */
	private Double score;

	private List<MedicalHospitalPictureVo> medicalHospitalPictures;

	private List<MedicalFieldVo> fields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLal() {
		return lal;
	}

	public void setLal(String lal) {
		this.lal = lal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPostCode() {
		return postCode;
	}

	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public Boolean getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Boolean authentication) {
		this.authentication = authentication;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public List<MedicalHospitalPictureVo> getMedicalHospitalPictures() {
		return medicalHospitalPictures;
	}

	public void setMedicalHospitalPictures(List<MedicalHospitalPictureVo> medicalHospitalPictures) {
		this.medicalHospitalPictures = medicalHospitalPictures;
	}

	public List<MedicalFieldVo> getFields() {
		return fields;
	}

	public void setFields(List<MedicalFieldVo> fields) {
		this.fields = fields;
	}

	public String getDescription() {
		if(description == null) return null;
		description = description.replace("\n\n","<br/>");
		description = description.replace("\n","<br/>");
		return description;
	}


	@Override
	public String toString() {
		return "MedicalHospitalVo{" +
			", id=" + id +
			", lal=" + lal +
			", name=" + name +
			", description=" + description +
			", tel=" + tel +
			", email=" + email +
			", postCode=" + postCode +
			", province=" + province +
			", city=" + city +
			", detailedAddress=" + detailedAddress +
			"}";
	}
}
