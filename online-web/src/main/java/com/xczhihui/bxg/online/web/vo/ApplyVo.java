package com.xczhihui.bxg.online.web.vo;/**
 * Created by admin on 2016/8/30.
 */

import java.util.Date;

/**
 * 用户报名信息封装类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:25
 */
public class ApplyVo {

    /**
     * 用户id号
     */
    private  String  userId;
    /**
     * qq号
     */
    private String qq;
    /**
     *email
     */
    private String email;
    /**
     *email
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 生日
     */
    private Date birthday;

    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 报名id号
     */
    private  String  applyId;
    /**
     * 学校id号
     */
    private String schoolId;
    /**
     * 学历
     */
    private String educationId;
    /**
     * 专业
     */
    private String majorId;

    /**
     * 身份证号
     */
    private String  idCardNo;

    private String province;

    private String city;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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
}
