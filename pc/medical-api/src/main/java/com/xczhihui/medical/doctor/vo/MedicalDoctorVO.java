package com.xczhihui.medical.doctor.vo;

import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

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
public class MedicalDoctorVO implements Serializable{

    /**
     * 医师表
     */
	private String id;

	/**
     * 姓名
     */
	private String name;

	/**
     * 职称
     */
	private String title;

	/**
     * 医师简介
     */
	private String description;

	/**
     * 联系电话
     */
	private String tel;

	/**
     * 用户表id
     */
	private String userId;

	/**
     * 医师类别：1.名青年中医2.名老中医3.少数民族中医4.国医大师5.古中医
     */
	private String type;

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
     * 坐诊时间
     */
	private String workTime;

	/**
     * 认证信息id
     */
	private String authenticationInformationId;

	/**
	 * 头像
	 */
	private String headPortrait;

	/**
	 * 医馆id
	 */
	private String hospitalId;

	/**
	 * 医馆名
	 */
	private String hospitalName;

	/**
	 * 签名
	 */
	private String signature;

	/**
	 * 领域
	 */
	private List<MedicalFieldVO> fields;

	private MedicalHospitalVo medicalHospital;

	/**
	 * 擅长
	 */
	private String fieldText;

	private String departmentText;

	/**
	 * 认证信息
	 */
	private MedicalDoctorAuthenticationInformationVO medicalDoctorAuthenticationInformation;

	/**
	 * 科室
	 */
	private List<MedicalDepartment> departments;

	/**
	 * 身份证号
	 */
	private String cardNum;

	public String getDepartmentText() {
		return departmentText;
	}

	public void setDepartmentText(String departmentText) {
		this.departmentText = departmentText;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public List<MedicalDepartment> getDepartments() {
		return departments;
	}

	public void setDepartments(List<MedicalDepartment> departments) {
		this.departments = departments;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getAuthenticationInformationId() {
		return authenticationInformationId;
	}

	public void setAuthenticationInformationId(String authenticationInformationId) {
		this.authenticationInformationId = authenticationInformationId;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public List<MedicalFieldVO> getFields() {
		return fields;
	}

	public void setFields(List<MedicalFieldVO> fields) {
		this.fields = fields;
	}

	public MedicalHospitalVo getMedicalHospital() {
		return medicalHospital;
	}

	public void setMedicalHospital(MedicalHospitalVo medicalHospitalVo) {
		this.medicalHospital = medicalHospitalVo;
	}

	public MedicalDoctorAuthenticationInformationVO getMedicalDoctorAuthenticationInformation() {
		return medicalDoctorAuthenticationInformation;
	}

	public void setMedicalDoctorAuthenticationInformation(MedicalDoctorAuthenticationInformationVO medicalDoctorAuthenticationInformation) {
		this.medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformation;
	}

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	public String getDescription() {
		if(description == null) {
            return null;
        }
		description = description.replace("\n\n","<br/>");
		description = description.replace("\n","<br/>");
		return description;
	}


	@Override
	public String toString() {
		return "MedicalDoctorVo{" +
			", id=" + id +
			", name=" + name +
			", title=" + title +
			", description=" + description +
			", tel=" + tel +
			", userId=" + userId +
			", type=" + type +
			", province=" + province +
			", city=" + city +
			", detailedAddress=" + detailedAddress +
			", authenticationInformationId=" + authenticationInformationId +
			"}";
	}
}
