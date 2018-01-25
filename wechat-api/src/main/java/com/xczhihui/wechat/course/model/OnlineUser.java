package com.xczhihui.wechat.course.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 熊猫中医在线网站用户。
 * @author Haicheng Jiang
 */
@TableName("oe_user")
public class OnlineUser extends  Model<Course> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * 主键id
	 */
	@TableField("id")
	private String id;

	/**
	 * 实体是否删除
	 */
	@TableField("isDelete")
	private boolean isDelete;

	/**
	 * 创建人ID
	 */
	@TableField("createPerson")
	private String createPerson;

	/**
	 * 创建时间
	 */
	@TableField("createTime")
	private Date createTime;
	
	/**
	 * 昵称给其他用户看的名。
	 */
	@TableField("name")
	private String name;

	/**
	 * 登录名
	 */
	@TableField("login_name")
	private String loginName;

	@TableField( "password")
	private String password;

	/**
	 * 性别
	 */
	@TableField( "sex")
	private int sex;

	/**
	 * email
	 */
	@TableField( "email")
	private String email;

	/**
	 * 电话号码
	 */
	@TableField( "mobile")
	private String mobile;

	/**
	 * 大头像
	 */
	@TableField( "big_head_photo")
	private String bigHeadPhoto;// 180x180
	/**
	 * 小头像
	 */
	@TableField( "small_head_photo")
	private String smallHeadPhoto;// 45x45
	/**
	 * 用户状态，-1禁用，0正常
	 */
	@TableField( "status")
	private Integer status;
	
	/**
	 * 最后登录ip
	 */
	@TableField( "last_login_ip")
	private String lastLoginIp;
	/**
	 * 最后登录时间
	 */
	@TableField( "last_login_date")
	private Date lastLoginDate;

	/**
	 * 访问次数，登录一次累加一次
	 */
	@TableField( "visit_sum")
	private Integer visitSum;
	/**
	 * 停留时长，最后登录时间 - 最后离开（退出、关闭浏览器）时间
	 */
	@TableField( "stay_time")
	private Integer stayTime;

	/**
	 * 职业
	 */
	@TableField( "occupation")
	private Integer occupation;
	/**
	 * 职业,其他
	 */
	@TableField( "occupation_other")
	private String occupationOther;

	/**
	 * 工作年限
	 */
	@TableField( "jobyears")
	private Integer jobyears;

	/**
	 * 用户信息
	 */
	@TableField( "info")
	private String info;


	/**
	 * 省
	 */
	@TableField( "region_area_id")
	private String province;

	/**
	 * 市
	 */
	@TableField( "region_city_id")
	private String city;

	/**
	 * 区域
	 */
	@TableField( "region_id")
	private String district;

	/**
	 * 学习目标
	 */
	@TableField( "target")
	private String target;


	/**
	 * 报名状态:true:已报名 false:未报名
	 */
	@TableField( "is_apply")
	private boolean isApply;


	/**
	 * 详细地址
	 */
	@TableField( "full_address")
	private String fullAddress;

	/**
	 * 学科ID
	 */
	@TableField( "menu_id")
	private Integer menuId;

	/**
	 * 三方用户唯一标识
	 */
	@TableField( "union_id")
	private String unionId;

	/**
	 * 用户类型
	 */
	@TableField( "user_type")
	private Integer userType = 0;

    /**
     * 三方账户关联的手机或邮箱账户的ID
     */
    @TableField( "ref_id")
    private String refId;
	/**
	 * 分享者id
	 */
	@TableField( "parent_id")
	private String parentId;
	/**
	 * 分享码
	 */
	@TableField( "share_code")
	private String shareCode;

	/**
	 * 变更时间
	 */
	@TableField( "change_time")
	private Date  changeTime;


	@TableField( "origin")
	private String  origin;
	
	@TableField( "vhall_id")
	private String  vhallId;
	
	@TableField( "vhall_pass")
	private String  vhallPass;
	
	
	@TableField( "vhall_name")
	private String  vhallName;
	
	
	@TableField( "type")
	private Integer  type;
	
	@TableField( "description")
	private String description;
	
	/**
	 * 杨宣新增
	 */
	@TableField( "is_lecturer")
	private Integer  isLecturer;    //该用户是否为讲师：0 不是 1是
	
	@TableField( "room_number")
	private Integer  roomNumber;    //如果用户是讲师，这个就是讲师的房间号
	
	@TableField( "province_name")
	private String  provinceName;  //省的名字
	
	@TableField( "city_name")
	private String  cityName;    //市的名字
	
	@TableField( "county_name")
	private String  countyName;    //区的名字
	
	
	@TableField( "individuality_signature")
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
	

	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}


	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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


	public String getIndividualitySignature() {
		return individualitySignature;
	}

	public void setIndividualitySignature(String individualitySignature) {
		this.individualitySignature = individualitySignature;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
