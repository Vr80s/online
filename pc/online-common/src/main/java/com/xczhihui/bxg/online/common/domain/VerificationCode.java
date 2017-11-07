package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.bxg.common.support.domain.BasicEntity2;

/**
 * 短信动态码
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "oe_verification_code")
public class VerificationCode extends BasicEntity2 {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 动态码
	 */
	private String vcode;
	/**
	 * 动态码类型，1新注册，2找回密码
	 */
	private String vtype;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getVtype() {
		return vtype;
	}
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	
}
