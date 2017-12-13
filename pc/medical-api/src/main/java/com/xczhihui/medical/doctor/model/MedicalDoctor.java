package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.hospital.model.MedicalHospital;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_doctor")
public class MedicalDoctor extends Model<MedicalDoctor> {

    private static final long serialVersionUID = 1L;

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
	@TableField("user_id")
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
	@TableField("detailed_address")
	private String detailedAddress;


	/**
     * 坐诊时间
     */
	@TableField("work_time")
	private String workTime;
    /**
     * 认证信息id
     */
	@TableField("authentication_information_id")
	private String authenticationInformationId;
    /**
     * 1已删除0未删除
     */
//	private Boolean deleted;
    /**
     * 启用状态
     */
//	private Boolean status;
    /**
     * 创建时间
     */
//	@TableField("create_time")
//	private Date createTime;
    /**
     * 创建人id
     */
//	@TableField("create_person")
//	private String createPerson;
    /**
     * 更新时间
     */
//	@TableField("update_time")
//	private Date updateTime;
    /**
     * 更新人id
     */
//	@TableField("update_person")
//	private String updatePerson;
    /**
     * 版本
     */
//	private String version;
    /**
     * 备注
     */
//	private String remark;

	//头像
	private String headPortrait;
	//医馆名
	private String hospitalId;

	private List<MedicalField> fields;

	private MedicalHospital medicalHospital;

	public MedicalHospital getMedicalHospital() {
		return medicalHospital;
	}

	public void setMedicalHospital(MedicalHospital medicalHospital) {
		this.medicalHospital = medicalHospital;
	}

	public List<MedicalField> getFields() {
		return fields;
	}

	public void setFields(List<MedicalField> fields) {
		this.fields = fields;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
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

	public String getDescription() {
		return description;
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

	public String getAuthenticationInformationId() {
		return authenticationInformationId;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public void setAuthenticationInformationId(String authenticationInformationId) {
		this.authenticationInformationId = authenticationInformationId;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalDoctor{" +
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
//			", deleted=" + deleted +
//			", status=" + status +
//			", createTime=" + createTime +
//			", createPerson=" + createPerson +
//			", updateTime=" + updateTime +
//			", updatePerson=" + updatePerson +
//			", version=" + version +
//			", remark=" + remark +
			"}";
	}
}
