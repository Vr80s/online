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
@TableName("medical_hospital")
public class MedicalHospital extends Model<MedicalHospital> {

    private static final long serialVersionUID = 1L;

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
	@TableField("post_code")
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
	@TableField("detailed_address")
	private String detailedAddress;
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
     * 版本
     */
	private String version;
    /**
     * 备注
     */
	private String remark;
	/**
	 * 是否已认证
	 */
	@TableField("is_authentication")
	private Boolean isAuthentication;
	/**
	 * 分值
	 */
	private Double score;

	public Boolean getAuthentication() {
		return isAuthentication;
	}

	public void setAuthentication(Boolean authentication) {
		isAuthentication = authentication;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalHospital{" +
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
