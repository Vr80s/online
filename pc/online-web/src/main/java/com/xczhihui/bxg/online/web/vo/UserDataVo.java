package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.online.api.vo.JobVo;

import java.util.Date;
import java.util.List;

/**
 * 用户资料
 * @author duanqh
 *
 */
public class UserDataVo {

	/** 用户id */
	private String uid;
	/** 报名id号 */
	private String applyId;
	/** 用户头像 */
	private String img;
	/** 昵称 */
	private String nickName;
	/** 用户真实姓名 */
	private String realName;
	/** 用户名 */
	private String loginName;
	/** 个性签名 */
	private String autograph;
	/** 手机号码 */
	private String mobile;
	/** qq号码 */
	private String qq;
	/** 邮箱 */
	private String email;
	/** 性别 */
	private Integer sex;
	/** 生日 */
	private Date birthday;
	/** 生日 */
	private String birthdayStr;
	/** 职业id 对应common的id*///暂改为存储身份
	private Integer occupation;
	/** 职业 *///暂改为存储身份信息集合
	private List<JobVo> job;
	/** 学习目标 */
	private List<JobVo> studyTarget;
	/** 学习目标 */
	private String target;
	/** 工作id */
	private Integer jobyearId;
	/** 工作 */
	private List<JobYearVo> jobyear;
	/** 学历 */
	private List<SystemVariate> education;
	/** 学历id号 */
	private String  educationId;
	/** 专业 */
	private List<SystemVariate> major;
	/** 专业id号 */
	private String majorId;
	/** 公司名称 */
	private String company;
	/** 岗位 */
	private String posts;
	/** 职业其他*/
	private String occupationOther;

	//用户个人信息的省市
	private String province;
	private String city;

	//报名信息的省市
	private String applyProvince;
	private String appCity;

	private String schoolId;
	private String district;

	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 详细地址
	 */
	private String fullAddress;


	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public List<JobVo> getJob() {
		return job;
	}
	public void setJob(List<JobVo> job) {
		this.job = job;
	}
	public Integer getJobyearId() {
		return jobyearId;
	}
	public void setJobyearId(Integer jobyearId) {
		this.jobyearId = jobyearId;
	}
	public List<JobYearVo> getJobyear() {
		return jobyear;
	}
	public void setJobyear(List<JobYearVo> jobyear) {
		this.jobyear = jobyear;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosts() {
		return posts;
	}
	public void setPosts(String posts) {
		this.posts = posts;
	}
	public String getBirthdayStr() {
		return birthdayStr;
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	public String getOccupationOther() {
		return occupationOther;
	}
	public void setOccupationOther(String occupationOther) {
		this.occupationOther = occupationOther;
	}
	public List<JobVo> getStudyTarget() {
		return studyTarget;
	}
	public void setStudyTarget(List<JobVo> studyTarget) {
		this.studyTarget = studyTarget;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public List<SystemVariate> getEducation() {
		return education;
	}

	public void setEducation(List<SystemVariate> education) {
		this.education = education;
	}

	public List<SystemVariate> getMajor() {
		return major;
	}

	public void setMajor(List<SystemVariate> major) {
		this.major = major;
	}

	public String getEducationId() {
		return educationId;
	}

	public void setEducationId(String educationId) {
		this.educationId = educationId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getApplyProvince() {
		return applyProvince;
	}

	public void setApplyProvince(String applyProvince) {
		this.applyProvince = applyProvince;
	}

	public String getAppCity() {
		return appCity;
	}

	public void setAppCity(String appCity) {
		this.appCity = appCity;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
}
