package com.xczh.consumer.market.bean;
import java.io.Serializable;


/**
 * 
 * 店铺\商铺用户表
 * @author yanghui
 **/
@SuppressWarnings("serial")
public class WxcpShopUser implements Serializable {

	/**登录ID   用户帐号**/
	 private String user_id;

	/**昵称**/
	 private String user_name;

	/**用户密码**/
	 private String user_password;

	/**用户电话**/
	 private String user_mobile;

	/**用户状态 1:正常,2:冻结,3:监管,4:禁用,5:删除**/
	 private String user_status;

	/**用户生日**/
	 private java.util.Date user_birthday;

	/**用户性别**/
	 private String user_sex;

	/**用户图像**/
	 private Long face_image_id;

	/**创建时间**/
	 private java.util.Date create_datetime;

	/**用户邮箱**/
	 private String user_email;

	/**用户身份证**/
	 private String user_identifyId;

	/**用户所属单位ID(店铺ＩＤ)**/
	 private Long unit_id;

	/**用户类型： 1,：单位管理员，2：普通会员，3：其他会员**/
	 private String user_level;

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

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public java.util.Date getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(java.util.Date user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public Long getFace_image_id() {
		return face_image_id;
	}

	public void setFace_image_id(Long face_image_id) {
		this.face_image_id = face_image_id;
	}

	public java.util.Date getCreate_datetime() {
		return create_datetime;
	}

	public void setCreate_datetime(java.util.Date create_datetime) {
		this.create_datetime = create_datetime;
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

	public Long getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Long unit_id) {
		this.unit_id = unit_id;
	}

	public String getUser_level() {
		return user_level;
	}

	public void setUser_level(String user_level) {
		this.user_level = user_level;
	}
}
