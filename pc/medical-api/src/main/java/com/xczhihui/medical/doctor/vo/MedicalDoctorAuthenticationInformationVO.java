package com.xczhihui.medical.doctor.vo;


import com.xczhihui.medical.department.model.MedicalDepartment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuxin
 * @since 2017-12-09
 */
@Data
public class MedicalDoctorAuthenticationInformationVO implements Serializable {

    /**
     * 医师认证信息表
     */
	private String id;

	/**
     * 头像
     */
	private String headPortrait;

	/**
     * 职称证明
     */
	private String titleProve;

	/**
     * 身份证正面
     */
	private String cardPositive;

	/**
     * 身份证反面
     */
	private String cardNegative;

	/**
     * 医师资格证
     */
	private String qualificationCertificate;

	/**
     * 职业医师证
     */
	private String professionalCertificate;

	/**
	 * 医师姓名
	 */
	private String name;

	/**
	 * 身份证号
	 */
	private String cardNum;

	/**
	 * 职称
	 */
	private String title;

	/**
	 * 擅长
	 */
	private String fieldText;

	/**
	 * 个人介绍
	 */
	private String description;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 详细地址
	 */
	private String detailedAddress;

	/**
	 * 科室
	 */
	private List<MedicalDepartment> medicalDepartments;

	/**
	 * 状态
	 */
	private Boolean status;
}
