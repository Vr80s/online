package com.xczhihui.bxg.online.common.domain;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * 熊猫中医在线网站用户。
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "oe_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "login_name" }) })
public class OnlineUser extends BxgUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String applyId;
	@Transient
	private String gradeName;
	@Transient
	private String qq;
	@Transient
	private String menuName;
	@Transient //大使课id
	private String shareCourseId;

	@Transient //否是为老学员
	private Integer isOldUser;
	@Transient
	private String OldUserClassName;
	@Transient
	private String OldUserSubjectName;
	@Transient
	private String OldUserSubjectId;
	@Transient
	private Double balance;
	@Transient
	private Double balanceGive;//是否具有代币账户
	/**
	 * 大头像
	 */
	@Column(name = "big_head_photo")
	private String bigHeadPhoto;// 180x180
	/**
	 * 小头像
	 */
	@Column(name = "small_head_photo")
	private String smallHeadPhoto;// 45x45
	/**
	 * 用户状态，-1禁用，0正常
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 最后登录ip
	 */
	@Column(name = "last_login_ip")
	private String lastLoginIp;
	/**
	 * 最后登录时间
	 */
	@Column(name = "last_login_date")
	private Date lastLoginDate;

	/**
	 * 访问次数，登录一次累加一次
	 */
	@Column(name = "visit_sum")
	private Integer visitSum;
	/**
	 * 停留时长，最后登录时间 - 最后离开（退出、关闭浏览器）时间
	 */
	@Column(name = "stay_time")
	private Integer stayTime;

	/**
	 * 职业
	 */
	@Column(name = "occupation")
	private Integer occupation;
	/**
	 * 职业,其他
	 */
	@Column(name = "occupation_other")
	private String occupationOther;

	/**
	 * 工作年限
	 */
	@Column(name = "jobyears")
	private Integer jobyears;

	/**
	 * 用户信息
	 */
	@Column(name = "info")
	private String info;


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
	 * 区域
	 */
	@Column(name = "region_id")
	private String district;

	/**
	 * 学习目标
	 */
	@Column(name = "target")
	private String target;


	/**
	 * 报名状态:true:已报名 false:未报名
	 */
	@Column(name = "is_apply")
	private boolean isApply;


	/**
	 * 详细地址
	 */
	@Column(name = "full_address")
	private String fullAddress;

	/**
	 * 学科ID
	 */
	@Column(name = "menu_id")
	private Integer menuId;

	/**
	 * 三方用户唯一标识
	 */
	@Column(name = "union_id")
	private String unionId;

	/**
	 * 用户类型
	 */
	@Column(name = "user_type")
	private Integer userType = 0;

    /**
     * 三方账户关联的手机或邮箱账户的ID
     */
    @Column(name = "ref_id")
    private String refId;
	/**
	 * 分享者id
	 */
	@Column(name = "parent_id")
	private String parentId;
	/**
	 * 分享码
	 */
	@Column(name = "share_code")
	private String shareCode;

	/**
	 * 变更时间
	 */
	@Column(name = "change_time")
	private Date  changeTime;

	/**
	 * 是否完善报名信息
	 */
	@Transient
	private Boolean  isPerfectInformation;

	@Column(name = "origin")
	private String  origin;
	
	@Column(name = "vhall_id")
	private String  vhallId;
	
	@Column(name = "vhall_pass")
	private String  vhallPass;
	
	
	@Column(name = "vhall_name")
	private String  vhallName;
	
	
	@Column(name = "type")
	private Integer  type;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type="text")
	@Column(name = "description")
	private String description;
	
	/**
	 * 杨宣新增
	 */
	@Column(name = "is_lecturer")
	private Integer  isLecturer;    //该用户是否为讲师：0 不是 1是
	
	@Column(name = "room_number")
	private Integer  roomNumber;    //如果用户是讲师，这个就是讲师的房间号
	
	@Column(name = "province_name")
	private String  provinceName;  //省的名字
	
	@Column(name = "city_name")
	private String  cityName;    //市的名字
	
	@Column(name = "county_name")
	private String  countyName;    //区的名字
	
	
	@Column(name = "individuality_signature")
	private String  individualitySignature;    //个性签名
	
	
	

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

	public String getBigHeadPhoto() {
		return bigHeadPhoto;
	}

	public void setBigHeadPhoto(String bigHeadPhoto) {
		this.bigHeadPhoto = bigHeadPhoto;
	}

	public String getSmallHeadPhoto() {
		return smallHeadPhoto;
	}

	public void setSmallHeadPhoto(String smallHeadPhoto) {
		this.smallHeadPhoto = smallHeadPhoto;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}



	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreateTime() {
		return super.getCreateTime();
	}

	public void setCreateTime(Date createTime) {
		super.setCreateTime(createTime);
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public Integer getJobyears() {
		return jobyears;
	}

	public void setJobyears(Integer jobyears) {
		this.jobyears = jobyears;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
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



	public boolean isApply() {
		return isApply;
	}

	public void setIsApply(boolean isApply) {
		this.isApply = isApply;
	}


	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

    public String getRefId() {
        return refId;
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

	public void setRefId(String refId) {
        this.refId = refId;
    }

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Boolean isPerfectInformation() {
		return isPerfectInformation;
	}

	public void setIsPerfectInformation(Boolean isPerfectInformation) {
		this.isPerfectInformation = isPerfectInformation;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShareCourseId() {
		return shareCourseId;
	}

	public void setShareCourseId(String shareCourseId) {
		this.shareCourseId = shareCourseId;
	}

	public Integer getIsOldUser() {

		return isOldUser;
	}

	public void setIsOldUser(Integer isOldUser) {
		this.isOldUser = isOldUser;
	}

	public String getOldUserClassName() {
		return OldUserClassName;
	}

	public void setOldUserClassName(String oldUserClassName) {
		OldUserClassName = oldUserClassName;
	}

	public String getOldUserSubjectName() {
		return OldUserSubjectName;
	}

	public void setOldUserSubjectName(String oldUserSubjectName) {
		OldUserSubjectName = oldUserSubjectName;
	}

	public String getOldUserSubjectId() {
		return OldUserSubjectId;
	}

	public void setOldUserSubjectId(String oldUserSubjectId) {
		OldUserSubjectId = oldUserSubjectId;
	}

	public Integer getIsLecturer() {
		return isLecturer;
	}

	public void setIsLecturer(Integer isLecturer) {
		this.isLecturer = isLecturer;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalanceGive() {
		return balanceGive;
	}

	public void setBalanceGive(Double balanceGive) {
		this.balanceGive = balanceGive;
	}

	public String getIndividualitySignature() {
		return individualitySignature;
	}

	public void setIndividualitySignature(String individualitySignature) {
		this.individualitySignature = individualitySignature;
	}
}
