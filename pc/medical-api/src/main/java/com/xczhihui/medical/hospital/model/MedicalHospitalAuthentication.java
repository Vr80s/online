package com.xczhihui.medical.hospital.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @author yuxin
 * @since 2018-01-15
 */
@Data
@TableName("medical_hospital_authentication")
public class MedicalHospitalAuthentication extends Model<MedicalHospitalAuthentication> {

    private static final long serialVersionUID = 1L;

    /**
     * 医馆认证信息表
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
     * 用户id
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

	/**
	 * 启用状态
	 */
	private Boolean status;

	/**
	 * 认证的医馆名字
	 */
	@TableField(exist = false)
	private String name;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
