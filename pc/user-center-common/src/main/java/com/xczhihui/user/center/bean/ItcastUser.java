package com.xczhihui.user.center.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户中心的用户实体。
 * 
 * @author liyong
 */
public class ItcastUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 在用户中心的ID，跟各个业务系统用户库中的ID不一样。
	 */
	private int id;

	/**
	 * 登录名，各个业务系统可以通过这个属性获取用户，该属性具有唯一性。
	 */
	private String loginName;

	private String password;

	/**
	 * 盐值用来增加密码加密复杂性。
	 */
	private String salt;

	/**
	 * 昵称给其他用户看的名。
	 */
	private String nikeName;

	/**
	 * 性别
	 * 
	 * @see UserSex
	 */
	private int sex = UserSex.UNKNOWN.getValue();

	/**
	 * email
	 */
	private String email;

	/**
	 * 电话号码
	 */
	private String mobile;

	/**
	 * 目前定义了普通用户、学生、老师，也可以由业务系统自己定义。
	 * 
	 * @see UserConst.TYPE_*
	 */
	private int type;

	/**
	 * 用户来源(从什么地方注册)bxg，dual，ask
	 * 
	 * @see UserConst.ORIGIN_*
	 */
	private String origin;

	/**
	 * 状态
	 * 
	 * @see UserConst.STATUS_*
	 */
	private int status;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginDate;

	/**
	 * 注册时间
	 */
	private Date registDate;

	private String headPhoto;

	private String uuid;

	@Override
	public String toString() {
		return String
		        .format("id=%d,loginName=%s,password=%s,salt=%s,"
		                + "nikeName=%s,sex=%d,email=%s,mobile=%s,type=%d,"
		                + "origin=%s,status=%d,lastLoginDate=%s,registDate=%s",
		                id, loginName, password, salt, nikeName, sex, email,
		                mobile, type, origin,
		                status, lastLoginDate, registDate);
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

}
