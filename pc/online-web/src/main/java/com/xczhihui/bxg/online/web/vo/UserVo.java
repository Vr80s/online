package com.xczhihui.bxg.online.web.vo;/**
 * Created by admin on 2016/8/30.
 */

/**
 * 用户个人信息封装类
 *
 * @author 康荣彩
 * @create 2016-08-30 13:25
 */
public class UserVo {
    /**
     * 用户id号
     */
    private  String  userId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     *个性签名
     */
    private String autograph;
    /**
     *e登录帐号
     */
    private String loginName;
    /**
     * 手机号
     */
    private Integer occupation;
    /**
     * 生日
     */
    private String occupationOther;
    /**
     * 省
     */
    private Integer jobyearId;
    /**
     * 市
     */
    private String company;
    /**
     * 用户真实姓名
     */
    private String posts;
    /**
     * 报名id号
     */
    private  String  province;
    /**
     * 学校id号
     */
    private String city;
    /**
     * 学历
     */
    private String district;
    /**
     * 专业
     */
    private String target;

    /**
     * 详细地址
     */
    private String fullAddress;
    
    /**
     * 地区
     */
    public String countyName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public String getOccupationOther() {
        return occupationOther;
    }

    public void setOccupationOther(String occupationOther) {
        this.occupationOther = occupationOther;
    }

    public Integer getJobyearId() {
        return jobyearId;
    }

    public void setJobyearId(Integer jobyearId) {
        this.jobyearId = jobyearId;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
    
    
    
}
