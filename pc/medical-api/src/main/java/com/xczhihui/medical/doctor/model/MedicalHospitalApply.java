package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *  医馆入驻申请实体类
 *  @author zhuwenbao
 *  @date 2018-01-17
 */
@TableName("medical_hospital_apply")
public class MedicalHospitalApply extends Model<MedicalHospitalApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;

	/**
	 * 创建人id
	 */
	@TableField("user_id")
	private String userId;

	/**
     * 医馆所属公司
     */
	private String company;

	/**
     * 营业执照
     */
	@TableField("medical_license")
	private String businessLicense;

	/**
     * 营业执照号
     */
	@TableField("medical_license_num")
	private String businessLicenseNum;

	/**
     * 药品经营许可证
     */
	@TableField("medical_license")
	private String medicalLicense;

    /**
     * 药品经营许可证号
     */
	@TableField("medical_license_num")
	private String medicalLicenseNum;

    /**
     * 0拒绝1通过2未处理
     */
	private Integer status;

    /**
     * 1已删除0未删除
     */
	private Boolean deleted;

    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;

    /**
     * 版本
     */
	private String version;

	/**
     * 备注
     */
	private String remark;


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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getBusinessLicenseNum() {
		return businessLicenseNum;
	}

	public void setBusinessLicenseNum(String businessLicenseNum) {
		this.businessLicenseNum = businessLicenseNum;
	}

	public String getMedicalLicense() {
		return medicalLicense;
	}

	public void setMedicalLicense(String medicalLicense) {
		this.medicalLicense = medicalLicense;
	}

	public String getMedicalLicenseNum() {
		return medicalLicenseNum;
	}

	public void setMedicalLicenseNum(String medicalLicenseNum) {
		this.medicalLicenseNum = medicalLicenseNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
}
