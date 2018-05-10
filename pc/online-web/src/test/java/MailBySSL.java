import java.security.Security;
import java.util.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailBySSL{
	public static boolean sendMailBySSL(String user,String svnpassword) throws AddressException, MessagingException{
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
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(username, password);
		      }});
		 
		       // -- Create a new message --
		  Message msg = new MimeMessage(session);
		 
		  // -- Set the FROM and TO fields --
		  msg.setFrom(new InternetAddress("system@ixincheng.com"));
		  msg.setRecipients(Message.RecipientType.TO, 
		    InternetAddress.parse(user+"@ixincheng.com",false));
		  msg.setSubject("svn密码变更通知");
		  msg.setText("你好,同学：svn密码统一升级，你的svn账号:"+user+"密码变更为："+svnpassword);
			System.out.println(user+":"+svnpassword);
		  msg.setSentDate(new Date());
		  Transport.send(msg);
		  
		  System.out.println("Message sent.");
		  return true;
		 }

	public static void main(String[] args) {
		List<String> list = Arrays.asList("yuruixin");
		list.forEach(name -> {
			try {
				sendMailBySSL(name, UUID.randomUUID().toString());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
	}
}