package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xczhihui.medical.department.model.MedicalDepartment;
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
	private Boolean deleted;

	/**
     * 启用状态
     */
	private Boolean status;

	/**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;

	/**
     * 创建人id
     */
	@TableField("create_person")
	private String createPerson;

	/**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;

	/**
     * 更新人id
     */
	@TableField("update_person")
	private String updatePerson;
	
	
	/**
     * 更新人id
     */
	@TableField("sort_update_time")
	private String sortUpdateTime;
	

	/**
     * 版本
     */
	private String version;

	/**
     * 备注
     */
	private String remark;

	/**
	 * 头像
	 */
	@TableField(exist = false)
	private String headPortrait;

	/**
	 * 医馆id
	 */
	@TableField(exist = false)
	private String hospitalId;

	/**
	 * 医馆名
	 */
	@TableField(exist = false)
	private String hospitalName;

	/**
	 * 医师擅长领域
	 */
	@TableField(exist = false)
	private List<MedicalField> fields;

	/**
	 * 医师所在医馆
	 */
	@TableField(exist = false)
	private MedicalHospital medicalHospital;

	/**
	 * 医师认证信息
	 */
	@TableField(exist = false)
	private MedicalDoctorAuthenticationInformation medicalDoctorAuthenticationInformation;

	/**
	 * 擅长
	 */
	@TableField("field_text")
	private String fieldText;

	/**
	 * 身份证号
	 */
	@TableField("card_num")
	private String cardNum;

	/**
	 * 医师所在的科室
	 * false：不映射到数据库表字段
	 */
	@TableField(exist = false)
	private List<MedicalDepartment> departments;

	/**
	 * 科室id数组
	 * false：不映射到数据库表字段
	 */
	@TableField(exist = false)
	private List<String> departmentIds;

	/**
	 * 职称证明
	 * false：不映射到数据库表字段
	 */
	@TableField(exist = false)
	private String titleProve;

	public String getTitleProve() {
		return titleProve;
	}

	public void setTitleProve(String titleProve) {
		this.titleProve = titleProve;
	}

	public List<String> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<String> departmentIds) {
		this.departmentIds = departmentIds;
	}

	public List<MedicalDepartment> getDepartments() {
		return departments;
	}

	public void setDepartments(List<MedicalDepartment> departments) {
		this.departments = departments;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public MedicalDoctorAuthenticationInformation getMedicalDoctorAuthenticationInformation() {
		return medicalDoctorAuthenticationInformation;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public void setMedicalDoctorAuthenticationInformation(MedicalDoctorAuthenticationInformation medicalDoctorAuthenticationInformation) {
		this.medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformation;
	}

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
		if(description == null) {
            return null;
        }
		description = description.replace("\n\n","<br/>");
		description = description.replace("\n","<br/>");
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	public String getSortUpdateTime() {
		return sortUpdateTime;
	}

	public void setSortUpdateTime(String sortUpdateTime) {
		this.sortUpdateTime = sortUpdateTime;
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
			", deleted=" + deleted +
			", status=" + status +
			", createTime=" + createTime +
			", createPerson=" + createPerson +
			", updateTime=" + updateTime +
			", updatePerson=" + updatePerson +
			", version=" + version +
			", remark=" + remark +
			"}";
	}
}
