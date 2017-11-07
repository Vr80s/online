package com.xczh.consumer.market.vo;

import com.xczhihui.user.center.bean.UserSex;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户中心的用户实体。
 * 
 * @author Alex Wang
 *
 */
public class ItcastUser implements Serializable, Writable {

	private static final long serialVersionUID = 6985869770954350322L;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 在用户中心的ID，跟各个业务系统用户库中的ID不一样。
	 */
	private int id;

	/**
	 * 登录名，各个业务系统可以通过这个属性获取用户，该属性具有唯一性。
	 */
	private String login_name;

	private String password;

	/**
	 * 盐值用来增加密码加密复杂性。
	 */
	private String salt;

	/**
	 * 昵称给其他用户看的名。
	 */
	private String nike_name;

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
	private Date last_login_date;

	/**
	 * 注册时间
	 */
	private Date registDate;

	@Override
	public String toString() {
		return String.format(
				"id=%d,loginName=%s,password=%s,salt=%s," + "nikeName=%s,sex=%d,email=%s,mobile=%s,type=%d,"
						+ "origin=%s,status=%d,lastLoginDate=%s,registDate=%s",
				id, login_name, password, salt, nike_name, sex, email, mobile, type, origin, status, last_login_date,
				registDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
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

	public String getNike_name() {
		return nike_name;
	}

	public void setNike_name(String nike_name) {
		this.nike_name = nike_name;
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

	public Date getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(id);
		out.writeInt(sex);
		out.writeInt(type);
		out.writeInt(status);
		out.writeUTF(login_name == null ? "" : login_name);
		out.writeUTF(password == null ? "" : password);
		out.writeUTF(salt == null ? "" : salt);
		out.writeUTF(nike_name == null ? "" : nike_name);
		out.writeUTF(email == null ? "" : email);
		out.writeUTF(mobile == null ? "" : mobile);
		out.writeUTF(origin == null ? "" : origin);
		out.writeUTF(last_login_date != null ? sdf.format(last_login_date) : sdf.format(new Date()));
		out.writeUTF(registDate != null ? sdf.format(registDate) : sdf.format(new Date()));
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		try {
			this.id = in.readInt();
			this.sex = in.readInt();
			this.type = in.readInt();
			this.status = in.readInt();
			this.login_name = in.readUTF();
			this.password = in.readUTF();
			this.salt = in.readUTF();
			this.nike_name = in.readUTF();
			this.email = in.readUTF();
			this.mobile = in.readUTF();
			this.origin = in.readUTF();
			this.last_login_date = sdf.parse(in.readUTF());
			this.registDate = sdf.parse(in.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
