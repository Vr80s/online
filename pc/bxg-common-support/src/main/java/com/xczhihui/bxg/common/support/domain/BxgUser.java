package com.xczhihui.bxg.common.support.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.xczhihui.user.center.bean.UserSex;

/**
 * 熊猫中医用户抽象。使用UserHolder和统一的shiro登录的系统，用户必须从这个类继承。
 * 
 * @author liyong
 *
 */
@MappedSuperclass
public abstract class BxgUser extends BasicEntity implements Serializable {

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

	/**
	 * 昵称给其他用户看的名。
	 */
	private String name;

	/**
	 * 登录名
	 */
	@Column(name = "login_name")
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
