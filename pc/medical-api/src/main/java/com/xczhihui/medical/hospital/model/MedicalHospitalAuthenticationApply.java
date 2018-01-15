package com.xczhihui.medical.hospital.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-01-15
 */
@TableName("medical_hospital_authentication_apply")
public class MedicalHospitalAuthenticationApply extends Model<MedicalHospitalAuthenticationApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆认证信息申请表
     */
	private String id;
    /**
     * 所属公司
     */
	private String company;
    /**
     * 营业执照号码
     */
	@TableField("business_license_no")
	private String businessLicenseNo;
    /**
     * 营业执照照片
     */
	@TableField("business_license_picture")
	private String businessLicensePicture;
    /**
     * 药品经营许可证号码
     */
	@TableField("license_for_pharmaceutical_trading")
	private String licenseForPharmaceuticalTrading;
    /**
     * 药品经营许可证照片
     */
	@TableField("license_for_pharmaceutical_trading_picture")
	private String licenseForPharmaceuticalTradingPicture;
    /**
     * 0拒绝1通过2未处理
     */
	private Integer status;
    /**
     * 申请用户id
     */
	@TableField("user_id")
	private String userId;
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
     * 版本
     */
	private String version;
    /**
     * 备注
     */
	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public String getBusinessLicensePicture() {
		return businessLicensePicture;
	}

	public void setBusinessLicensePicture(String businessLicensePicture) {
		this.businessLicensePicture = businessLicensePicture;
	}

	public String getLicenseForPharmaceuticalTrading() {
		return licenseForPharmaceuticalTrading;
	}

	public void setLicenseForPharmaceuticalTrading(String licenseForPharmaceuticalTrading) {
		this.licenseForPharmaceuticalTrading = licenseForPharmaceuticalTrading;
	}

	public String getLicenseForPharmaceuticalTradingPicture() {
		return licenseForPharmaceuticalTradingPicture;
	}

	public void setLicenseForPharmaceuticalTradingPicture(String licenseForPharmaceuticalTradingPicture) {
		this.licenseForPharmaceuticalTradingPicture = licenseForPharmaceuticalTradingPicture;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalHospitalAuthenticationApply{" +
			", id=" + id +
			", company=" + company +
			", businessLicenseNo=" + businessLicenseNo +
			", businessLicensePicture=" + businessLicensePicture +
			", licenseForPharmaceuticalTrading=" + licenseForPharmaceuticalTrading +
			", licenseForPharmaceuticalTradingPicture=" + licenseForPharmaceuticalTradingPicture +
			", status=" + status +
			", userId=" + userId +
			", deleted=" + deleted +
			", createTime=" + createTime +
			", createPerson=" + createPerson +
			", updateTime=" + updateTime +
			", updatePerson=" + updatePerson +
			", version=" + version +
			", remark=" + remark +
			"}";
	}
}
