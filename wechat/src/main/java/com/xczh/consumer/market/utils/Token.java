package com.xczh.consumer.market.utils;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/**
 * 用户登录后的标识
 * 
 * @author Alex Wang
 */
public class Token implements Serializable,Writable {
	private static final long serialVersionUID = 7175888406581656483L;
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
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 类型，1学生，2老师，3未知
	 */
	private int type;
	private int sex;
	private String email;
	private String nikeName;
	
	@Override
	public String toString() {
		return "userId:" + userId + " loginName:" + loginName 
				+ " origin:" + origin 
				+ " ticket:" + ticket 
				+ " expires:" + expires 
				+ "mobile:" + mobile 
				+ "type:" + type 
				+ "sex:" + sex 
				+ "email:" + email 
				+ "nikeName:" + nikeName;
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

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(userId);
		out.writeInt(type);
		out.writeInt(sex);
		out.writeLong(expires);
		out.writeUTF(loginName == null ? "" : loginName);
		out.writeUTF(nikeName == null ? "" : nikeName);
		out.writeUTF(mobile == null ? "" : mobile);
		out.writeUTF(email == null ? "" : email);
		out.writeUTF(origin == null ? "" : origin);
		out.writeUTF(ticket == null ? "" : ticket);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.userId = in.readInt();
		this.type = in.readInt();
		this.sex = in.readInt();
		this.expires = in.readLong();
		this.loginName = in.readUTF();
		this.nikeName = in.readUTF();
		this.mobile = in.readUTF();
		this.email = in.readUTF();
		this.origin = in.readUTF();
		this.ticket = in.readUTF();
	}
}
