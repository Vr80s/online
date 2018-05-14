package com.xczhihui.user.model;

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
 * @since 2018-05-14
 */
@TableName("oe_user")
public class OeUser extends Model<OeUser> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	@TableField("login_name")
	private String loginName;
    /**
     * 盐值
     */
	private String salt;
	private String password;
	private Integer sex;
	private String mobile;
	private String email;
	@TableField("is_delete")
	private Boolean isDelete;
	@TableField("small_head_photo")
	private String smallHeadPhoto;
	@TableField("create_time")
	private Date createTime;
	private Integer status;
    /**
     * 最后登录ip
     */
	@TableField("last_login_ip")
	private String lastLoginIp;
    /**
     * 最后登录时间
     */
	@TableField("last_login_date")
	private Date lastLoginDate;
    /**
     * 访问次数，登录一次累加一次
     */
	@TableField("visit_sum")
	private Integer visitSum;
    /**
     * 停留时长，最后登录时间 - 最后离开（退出、关闭浏览器）时间
     */
	@TableField("stay_time")
	private Integer stayTime;
    /**
     * 工作年限
     */
	private Integer jobyears;
    /**
     * 用户职业
     */
	private Integer occupation;
    /**
     * 区域
     */
	@TableField("region_id")
	private String regionId;
    /**
     * 省
     */
	@TableField("region_area_id")
	private String regionAreaId;
    /**
     * 市
     */
	@TableField("region_city_id")
	private String regionCityId;
    /**
     * 职业,其他
     */
	@TableField("occupation_other")
	private String occupationOther;
    /**
     * 详细地址
     */
	@TableField("full_address")
	private String fullAddress;
    /**
     * 三方用户唯一标识
     */
	@TableField("union_id")
	private String unionId;
    /**
     * 三方账号绑定的账号ID
     */
	@TableField("ref_id")
	private String refId;
    /**
     * 分享者id(上级用户)
     */
	@TableField("parent_id")
	private String parentId;
    /**
     * 分享码
     */
	@TableField("share_code")
	private String shareCode;
    /**
     * 变更时间
     */
	@TableField("change_time")
	private Date changeTime;
    /**
     * 用户来源：1.pc 2.h5 3.android 4.ios 5.导入
     */
	private String origin;
    /**
     * 讲师房间号
     */
	@TableField("room_number")
	private Integer roomNumber;
    /**
     * 微吼id
     */
	@TableField("vhall_id")
	private String vhallId;
    /**
     * 微吼密码
     */
	@TableField("vhall_pass")
	private String vhallPass;
    /**
     * 微吼登录名
     */
	@TableField("vhall_name")
	private String vhallName;
	@TableField("province_name")
	private String provinceName;
	@TableField("city_name")
	private String cityName;
    /**
     * 地区的名字
     */
	@TableField("county_name")
	private String countyName;
    /**
     * 废弃---用户类型（0：非三方用户，1：qq用户，2：微信用户,3微博用户）
     */
	@TableField("user_type")
	private Integer userType;
    /**
     * 废弃---是否是讲师：0,用户，1既是用户也是讲师
     */
	@TableField("is_lecturer")
	private Integer isLecturer;
    /**
     * 废弃---用户表名状态 0:未报名 1：已报名
     */
	@TableField("is_apply")
	private Boolean isApply;
    /**
     * 废弃---0普通，1学生，2老师
     */
	private Integer type;
    /**
     * 废弃---学习目标
     */
	private String target;
    /**
     * 废弃---个性签名
     */
	private String info;
    /**
     * 废弃---个性签名 此字段废除
     */
	@TableField("individuality_signature")
	private String individualitySignature;
    /**
     * 废弃---个人简介（讲师用）
     */
	private String description;
    /**
     * 废弃---
     */
	private String blacklist;
    /**
     * 废弃---
     */
	@TableField("menu_id")
	private Integer menuId;
    /**
     * 废弃---
     */
	private String gag;
    /**
     * 废弃---
     */
	@TableField("big_head_photo")
	private String bigHeadPhoto;
    /**
     * 废弃---
     */
	@TableField("create_person")
	private String createPerson;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getSmallHeadPhoto() {
		return smallHeadPhoto;
	}

	public void setSmallHeadPhoto(String smallHeadPhoto) {
		this.smallHeadPhoto = smallHeadPhoto;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getVisitSum() {
		return visitSum;
	}

	public void setVisitSum(Integer visitSum) {
		this.visitSum = visitSum;
	}

	public Integer getStayTime() {
		return stayTime;
	}

	public void setStayTime(Integer stayTime) {
		this.stayTime = stayTime;
	}

	public Integer getJobyears() {
		return jobyears;
	}

	public void setJobyears(Integer jobyears) {
		this.jobyears = jobyears;
	}

	public Integer getOccupation() {
		return occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionAreaId() {
		return regionAreaId;
	}

	public void setRegionAreaId(String regionAreaId) {
		this.regionAreaId = regionAreaId;
	}

	public String getRegionCityId() {
		return regionCityId;
	}

	public void setRegionCityId(String regionCityId) {
		this.regionCityId = regionCityId;
	}

	public String getOccupationOther() {
		return occupationOther;
	}

	public void setOccupationOther(String occupationOther) {
		this.occupationOther = occupationOther;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getVhallId() {
		return vhallId;
	}

	public void setVhallId(String vhallId) {
		this.vhallId = vhallId;
	}

	public String getVhallPass() {
		return vhallPass;
	}

	public void setVhallPass(String vhallPass) {
		this.vhallPass = vhallPass;
	}

	public String getVhallName() {
		return vhallName;
	}

	public void setVhallName(String vhallName) {
		this.vhallName = vhallName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getIsLecturer() {
		return isLecturer;
	}

	public void setIsLecturer(Integer isLecturer) {
		this.isLecturer = isLecturer;
	}

	public Boolean getApply() {
		return isApply;
	}

	public void setApply(Boolean isApply) {
		this.isApply = isApply;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIndividualitySignature() {
		return individualitySignature;
	}

	public void setIndividualitySignature(String individualitySignature) {
		this.individualitySignature = individualitySignature;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getGag() {
		return gag;
	}

	public void setGag(String gag) {
		this.gag = gag;
	}

	public String getBigHeadPhoto() {
		return bigHeadPhoto;
	}

	public void setBigHeadPhoto(String bigHeadPhoto) {
		this.bigHeadPhoto = bigHeadPhoto;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OeUser{" +
			", id=" + id +
			", name=" + name +
			", loginName=" + loginName +
			", salt=" + salt +
			", password=" + password +
			", sex=" + sex +
			", mobile=" + mobile +
			", email=" + email +
			", isDelete=" + isDelete +
			", smallHeadPhoto=" + smallHeadPhoto +
			", createTime=" + createTime +
			", status=" + status +
			", lastLoginIp=" + lastLoginIp +
			", lastLoginDate=" + lastLoginDate +
			", visitSum=" + visitSum +
			", stayTime=" + stayTime +
			", jobyears=" + jobyears +
			", occupation=" + occupation +
			", regionId=" + regionId +
			", regionAreaId=" + regionAreaId +
			", regionCityId=" + regionCityId +
			", occupationOther=" + occupationOther +
			", fullAddress=" + fullAddress +
			", unionId=" + unionId +
			", refId=" + refId +
			", parentId=" + parentId +
			", shareCode=" + shareCode +
			", changeTime=" + changeTime +
			", origin=" + origin +
			", roomNumber=" + roomNumber +
			", vhallId=" + vhallId +
			", vhallPass=" + vhallPass +
			", vhallName=" + vhallName +
			", provinceName=" + provinceName +
			", cityName=" + cityName +
			", countyName=" + countyName +
			", userType=" + userType +
			", isLecturer=" + isLecturer +
			", isApply=" + isApply +
			", type=" + type +
			", target=" + target +
			", info=" + info +
			", individualitySignature=" + individualitySignature +
			", description=" + description +
			", blacklist=" + blacklist +
			", menuId=" + menuId +
			", gag=" + gag +
			", bigHeadPhoto=" + bigHeadPhoto +
			", createPerson=" + createPerson +
			"}";
	}
}
