package com.xczhihui.user.center.bean;

import java.io.Serializable;

/**
 * 用户登录后的标识
 * 
 * @author liyong
 */
public class Token implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户在用户中心的ID
	 */
	private int userId;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 用户来源(从什么地方注册)bxg，dual，ask
	 * 
	 * @see UserConst.ORIGIN_*
	 */
	private String origin;
	/**
	 * 登录后产生的票
	 */
	private String ticket;
	/**
	 * 票的有效期,1970开始的毫秒数
	 */
	private long expires;
	
	private String mobile;
	
	/**
	 * 目前定义了普通用户、学生、老师，也可以由业务系统自己定义。
	 * 
	 * @see UserConst.TYPE_*
	 */
	private int type;
	
	private String email;
	
	private String nickName;

	@Override
	public String toString() {
		return "userId:" + userId + " loginName:" + loginName + " origin:" + origin + " ticket:" + ticket + " expires:" + expires + " mobile:"
				+ mobile + " type:" + type + " email:" + email +" nickName:" +nickName;
	}
	
	
	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
