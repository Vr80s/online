package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EmailService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String auth=null,host=null, user=null;

	private String password=null;
	public EmailService(String auth,String host,String user,String password){
		this.auth = auth;
		this.host = host;
		this.user = user;
		this.password = password;
	}
	public EmailService(){
	}
	
	public void sendEmail(String toemail,String subject,String content,String type) throws Exception{
		if (!StringUtils.hasText(auth)||!StringUtils.hasText(host)||!StringUtils.hasText(user)||!StringUtils.hasText(password)) {
			throw new RuntimeException("参数错误！new对象的时候请传入auth、host、user、password参数，或通过set方法设置。");
		}
		EmailUtil.sendMailBySSL(host, user, password, toemail, subject,content);
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
