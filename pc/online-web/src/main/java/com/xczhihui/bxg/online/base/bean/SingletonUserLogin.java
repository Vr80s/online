package com.xczhihui.bxg.online.base.bean;

import javax.servlet.http.HttpSession;

public class SingletonUserLogin {
	private HttpSession session;
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	private String ticket;
}
