package com.xczh.consumer.market.bean;/**
 * Created by admin on 2016/8/30.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xczhihui.bxg.online.api.vo.UserAddressManagerVo;

import java.util.Date;

/**
 * 报名表信息实体类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:14
 */
@Entity
@Table(name = "oe_apply")
public class Apply extends BasicEntity {


    /**
     * 用户ID号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 学号
     */
    @Column(name = "student_number")
    private String  studentNumber;

    /**
     * qq号
     */
    @Column(name = "qq")
    private String qq;

    /**
     *email
     */
    @Column(name = "email")
    private String email;

    /**
     *email
     */
    @Column(name = "sex")
    private Integer sex;


    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;


    /**
     * 生日
     */
    @Column(name = "birthday")
    @Transient
    private Date birthday;

    /**
     * 省
     */
    @Column(name = "region_area_id")
    private String province;

    /**
     * 市
     */
    @Column(name = "region_city_id")
    private String city;


    /**
     * 用户真实姓名
     */
    @Column(name = "real_name")
    private String realName;


    /**
     * 学校id号
     */
    @Column(name = "school_id")
    private String schoolId;

    /**
     * 学历
     */
    @Column(name = "education_id")
    private String educationId;

    /**
     * 专业
     */
    @Column(name = "major_id")
    private String majorId;

    /**
     * 身份证号
     */
    @Column(name = "id_card_no")
    private String idCardNo;


    /**
     * 否是为老学员
     */
    @Column(name = "is_old_user")
    private Integer isOldUser;
    
    /**
     * 推荐人
     */
    @Column(name = "referee")
    private String referee;
    
    @Column(name = "wechat_no")
    private String wechatNo;

    @Column(name="occupation")
    private String occupation;
    
    /**
     * 否是第一次参加
     */
    @Column(name = "is_first")
    private Boolean isFirst;
    
    
    /**
     * 如果在选填地址的时候，如果存在默认地址，带回去默认地址，如果没有，就不带回了
     */
    private UserAddressManagerVo userAddressManagerVo;
    
    

    public Integer getIsOldUser() {
        return isOldUser;
    }

    public void setIsOldUser(Integer isOldUser) {
        this.isOldUser = isOldUser;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public Boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public UserAddressManagerVo getUserAddressManagerVo() {
		return userAddressManagerVo;
	}

	public void setUserAddressManagerVo(UserAddressManagerVo userAddressManagerVo) {
		this.userAddressManagerVo = userAddressManagerVo;
	}

	@Override
    public String toString() {
        return "Apply{" +
                "userId='" + userId + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", birthday=" + birthday +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", realName='" + realName + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", educationId='" + educationId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", isOldUser=" + isOldUser +
                ", referee='" + referee + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                ", occupation='" + occupation + '\'' +
                ", isFirst=" + isFirst +
                '}';
    }
}
