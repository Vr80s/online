package com.xczh.consumer.market.bean;

/**
 * 短信动态码
 * @author zhangshixiong
 */
public class VerificationCode extends BasicEntity2{
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
	private Integer vtype;
	
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
	public Integer getVtype() {
		return vtype;
	}
	public void setVtype(Integer vtype) {
		this.vtype = vtype;
	}
	
}
