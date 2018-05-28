package com.xczhihui.medical.doctor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuxin
 * @since 2017-12-09
 */
@Data
@TableName("medical_doctor_authentication_information")
public class MedicalDoctorAuthenticationInformation extends Model<MedicalDoctorAuthenticationInformation> {

    private static final long serialVersionUID = 1L;

    /**
     * 医师认证信息表
     */
	private String id;

	/**
     * 头像
     */
	@TableField("head_portrait")
	private String headPortrait;

	/**
     * 职称证明
     */
	@TableField("title_prove")
	private String titleProve;

	/**
     * 身份证正面
     */
	@TableField("card_positive")
	private String cardPositive;

	/**
     * 身份证反面
     */
	@TableField("card_negative")
	private String cardNegative;

	/**
     * 医师资格证
     */
	@TableField("qualification_certificate")
	private String qualificationCertificate;

	/**
     * 职业医师证
     */
	@TableField("professional_certificate")
	private String professionalCertificate;

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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
