package com.xczhihui.bxg.common.web.auth.bean;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 带有用户中心票的登录标示。优先使用username、password登录，然后考虑用ticket验证。
 * 
 * @author liyong
 *
 */
public class UCenterNamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String ticket;

	/**
	 * 
	 * @param ticket
	 *            用户中心产生的票。
	 */
	public UCenterNamePasswordToken(final String ticket) {
		this.setTicket(ticket);
	}

	public UCenterNamePasswordToken(final String loginName, final String password) {
		super(loginName, password);
		this.setTicket(null);
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
