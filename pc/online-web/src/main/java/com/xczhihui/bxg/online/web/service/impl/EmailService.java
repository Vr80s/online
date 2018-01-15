package com.xczhihui.bxg.online.web.service.impl;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EmailService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String auth=null,host=null, user=null;

	private static String password=null;
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
		sendMailBySSL(host, user, password, toemail, subject,content);
	}
	
	public static boolean sendMailBySSL(String smtp,String username,String password,String tousername,String subject,String content) throws AddressException, MessagingException{
		  Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		  // Get a Properties object
		  Properties props = new Properties();
		  props.setProperty("mail.smtp.host", smtp);
		  props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		  props.setProperty("mail.smtp.socketFactory.fallback", "false");
		  props.setProperty("mail.smtp.port", "465");
		  props.setProperty("mail.smtp.socketFactory.port", "465");
		  props.put("mail.smtp.auth", "true");
		  Session session = Session.getDefaultInstance(props, new Authenticator(){
		      @Override
              protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(username, password);
		      }});
		 
		       // -- Create a new message --
		  Message msg = new MimeMessage(session);
		 
		  // -- Set the FROM and TO fields --
		  msg.setFrom(new InternetAddress(username));
		  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tousername,false));
		  msg.setSubject(subject);
//		  msg.setText(content);
		  msg.setContent(content, "text/html;charset = gbk"); 
		  msg.setSentDate(new Date());
		  Transport.send(msg);
		  
		  return true;
		 }
	public static void main(String[] args) throws AddressException, MessagingException {
		String subject = "11111131111111";
		String content = "11111131111111";
		
/*		email.auth=true
		email.host=smtp.ixincheng.com
		email.user=system@ixincheng.com
		email.password=Ixincheng1234*/
		
		String username = "system@ixincheng.com";
		password = "Ixincheng1234";
		String tousername = "yangxuan@ixincheng.com";
		String smtp = "smtp.ixincheng.com";
		sendMailBySSL(smtp, username, password, tousername, subject, content);
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
