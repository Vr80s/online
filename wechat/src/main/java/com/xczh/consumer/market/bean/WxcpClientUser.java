package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * Client端用户表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpClientUser implements Serializable {

	/**用户ID（关键字ID）**/
	 private String user_id;

	/**用户姓名（用户帐号，唯一的）**/
	 private String user_name;

	/**用户昵称**/
	 private String user_nick_name;

	/**用户密码**/
	 private String user_password;

	/**用户电话**/
	 private String user_mobile;
	 
	 /**微信号名称**/
	 private String openname;

	/**用户状态 禁用(0)  正常(1)  删除(2)**/
	 private String user_status;
	 /**用户所在城市 **/
	 private String city;
	 /**用户所在国家 **/
	 private String country;
	 /**用户所在省份 **/
	 private String province;

	/**用户生日**/
	 private String user_birthday;

	/**用户性别  0表示女性  1表示男性**/
	 private String user_sex;

	/**用户图像**/
	 private String face_image_id;

	/**创建时间**/
	 private java.util.Date create_time;

	/**用户邮箱**/
	 private String user_email;

	/**用户身份证**/
	 private String user_identifyId;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_nick_name() {
		return user_nick_name;
	}

	public void setUser_nick_name(String user_nick_name) {
		this.user_nick_name = user_nick_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getOpenname() {
		return openname;
	}

	public void setOpenname(String openname) {
		this.openname = openname;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getFace_image_id() {
		return face_image_id;
	}

	public void setFace_image_id(String face_image_id) {
		this.face_image_id = face_image_id;
	}

	public java.util.Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_identifyId() {
		return user_identifyId;
	}

	public void setUser_identifyId(String user_identifyId) {
		this.user_identifyId = user_identifyId;
	}
	
	private String openid = "";

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	private String wx_public_id = "";
	private String wx_public_name = "";

	public String getWx_public_id() {
		return wx_public_id;
	}

	public void setWx_public_id(String wx_public_id) {
		this.wx_public_id = wx_public_id;
	}

	public String getWx_public_name() {
		return wx_public_name;
	}

	public void setWx_public_name(String wx_public_name) {
		this.wx_public_name = wx_public_name;
	}
			
}
