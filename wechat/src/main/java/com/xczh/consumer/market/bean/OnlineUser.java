package com.xczh.consumer.market.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.xczhihui.user.center.bean.UserSex;

/**
 * 在线用户表
 * @author zhangshixiong
 * @date 2017-02-22
 */
public class OnlineUser extends BasicEntity implements Serializable,HttpSessionBindingListener{
	private static final long serialVersionUID = 1L;
	/**
	 * 女
	 */
	public static final int SEX_FEMALE = UserSex.FEMALE.getValue();

	/**
	 * 男
	 */
	public static final int SEX_MALE = UserSex.MALE.getValue();

	/**
	 * 未知
	 */
	public static final int SEX_UNKNOWN = UserSex.UNKNOWN.getValue();
	
	
	private int userCenterId;

	/**
	 * 昵称给其他用户看的名。
	 */
	private String name;
	/**
	 * 登录名
	 */
	private String loginName;

	private String password;

	/**
	 * 性别
	 */
	private int sex = SEX_UNKNOWN;

	/**
	 * email
	 */
	private String email;

	/**
	 * 电话号码
	 */
	private String mobile;
	
	private String applyId;
	private String gradeName;
	private String qq;
	private String menuName;
	private String shareCourseId;
	private Integer isOldUser;

	private Integer isLecturer;
	/**
	 * 大头像
	 */
	private String bigHeadPhoto;// 180x180
	/**
	 * 小头像
	 */
	private String smallHeadPhoto;// 45x45
	/**
	 * 用户状态，-1禁用，0正常
	 */
	private int status;
	
	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginDate;

	/**
	 * 访问次数，登录一次累加一次
	 */
	private Integer visitSum;
	/**
	 * 停留时长，最后登录时间 - 最后离开（退出、关闭浏览器）时间
	 */
	private Integer stayTime;

	/**
	 * 职业id
	 */
	private Integer occupation;
	/**
	 * 职业value
	 */
	private String occupationText;
	
	/**
	 * 职业,其他
	 */
	private String occupationOther;

	/**
	 * 工作年限
	 */
	private Integer jobyears;

	/**
	 * 用户信息   个人描述
	 */
	private String info;



	/**
	 * 区域 -->目前改成了国家
	 */
	private String district;
	
	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;


	/**
	 * 学习目标
	 */
	private String target;


	/**
	 * 报名状态:true:已报名 false:未报名
	 */
	private boolean isApply;


	/**
	 * 详细地址
	 */
	private String fullAddress;

	/**
	 * 学科ID
	 */
	private Integer menuId;

	/**
	 * 三方用户唯一标识
	 */
	private String unionId;

	/**
	 * 用户类型
	 */
	private Integer userType = 0;

    /**
     * 三方账户关联的手机或邮箱账户的ID
     */
    private String refId;
	/**
	 * 分享者id
	 */
	private String parentId;
	/**
	 * 分享码
	 */
	private String shareCode;

	/**
	 * 变更时间
	 */
	private Date  changeTime;

	/**
	 * 是否完善报名信息
	 */
	private Boolean  isPerfectInformation;

	private String  origin;
	
	private Integer  type;
	
	private String ticket;//票

	private Integer roomNumber; //房间号
	
	private String  vhallId; //微吼账号
	
	private String  vhallPass; //微吼密码
	
	private String vhallName; //微吼名字
	
	
	private String  provinceName;  //省的名字
	
	private String  cityName;    //市的名字
	
	private String  countyName;    //区的名字
	
	private String individualitySignature;//个性签名
	
	private Integer code; //错误代码描述
	
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsPerfectInformation() {
		return isPerfectInformation;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getUserCenterId() {
		return userCenterId;
	}

	public void setUserCenterId(int userCenterId) {
		this.userCenterId = userCenterId;
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

	public Integer getIsLecturer() {
		return isLecturer;
	}

	public void setIsLecturer(Integer isLecturer) {
		this.isLecturer = isLecturer;
	}

	public String getIndividualitySignature() {
		return individualitySignature;
	}

	public void setIndividualitySignature(String individualitySignature) {
		this.individualitySignature = individualitySignature;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	
	
	
	public String getOccupationText() {
		return occupationText;
	}

	public void setOccupationText(String occupationText) {
		this.occupationText = occupationText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		OnlineUser other = (OnlineUser) obj;
		if (getId() == null) {
			if (other.getId() != null) {
                return false;
            }
		} else if (!getId().equals(other.getId())) {
            return false;
        }
		return true;
	}

	
	
	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/****************************  单用户登录使用了   **************************************************/
	
	@SuppressWarnings("unchecked")
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("进入了....");
		HttpSession session = event.getSession();

		Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) session
				.getServletContext().getAttribute("userMap");

		userMap.put(this, session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("退出了....");
		HttpSession session = event.getSession();
		// 获得人员列表
		Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) session
				.getServletContext().getAttribute("userMap");
		// 将用户移除了
		userMap.remove(this);
	}
	
	
	
	
}
