package com.xczhihui.bxg.online.web.service;
/**
 * 动态码
 * @author Haicheng Jiang
 */
public interface VerificationCodeService {
	/**
	 * 发送验证信息
	 *
	 * 业务逻辑：
	 * 1、动态码有效期XX分钟，XX分钟之内发送的动态码都一样
	 * 2、同一帐号两次发送间隔至少XX秒
	 * 
	 * @param username 用户名
	 * @param vtype 动态码类型，1新注册，2找回密码
	 */
	public String addMessage(String username,String vtype);
	/**
	 * 校验动态码
	 * @param phone
	 * @param code
	 */
	public String checkCode(String phone,String code);
}
