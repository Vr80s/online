package com.xczhihui.bxg.online.base.bean;

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

public class SendEmail {
	 public static boolean sendMailBySSL() throws AddressException, MessagingException{
		  Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		  // Get a Properties object
		  Properties props = new Properties();
		  props.setProperty("mail.smtp.host", "smtp.ixincheng.com");
		  props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		  props.setProperty("mail.smtp.socketFactory.fallback", "false");
		  props.setProperty("mail.smtp.port", "465");
		  props.setProperty("mail.smtp.socketFactory.port", "465");
		  props.put("mail.smtp.auth", "true");
		  final String username = "system@ixincheng.com";
		  final String password = "Ixincheng1234";
		  Session session = Session.getDefaultInstance(props, new Authenticator(){
		      @Override
              protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(username, password);
		      }});
		 
		       // -- Create a new message --
		  Message msg = new MimeMessage(session);
		 
		  // -- Set the FROM and TO fields --
		  msg.setFrom(new InternetAddress("system@ixincheng.com"));
		  msg.setRecipients(Message.RecipientType.TO, 
		    InternetAddress.parse("yuruixin_china@163.com",false));
		  msg.setSubject("你好,这是来自本地11111服务器");
		  msg.setText("来自测试邮件");
		  msg.setSentDate(new Date());
		  Transport.send(msg);
		  
		  System.out.println("XgMessage sent.");
		  return true;
		 }

}
