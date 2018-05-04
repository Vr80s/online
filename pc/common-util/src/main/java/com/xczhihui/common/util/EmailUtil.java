package com.xczhihui.common.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/1 0001-下午 7:32<br>
 */
public class EmailUtil {

    public static Properties pro = new Properties();
    static {
        try {
            InputStream in = EmailUtil.class.getResource("/config.properties").openStream();
            pro.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String SMTP = getValue("email.host");
    public static final String USERNAME = getValue("email.user");
    public static final String PASSWORD = getValue("email.password");
    public static final String ENV = getValue("env.flag");
    public static final String TOUSER = "system@ixincheng.com";
    public static final String MODIFYLOGINNAMETOUSER = "yuruixin@ixincheng.com";

    public static void sendExceptionMailBySSL(String server,String subject,String content) throws MessagingException {
        if(ENV != null && (ENV.equals("test")||ENV.equals("prod"))){
            sendMailBySSL(SMTP,USERNAME,PASSWORD,TOUSER,server+":"+ENV+"环境异常:"+subject,content);
        }
    }

    public static void modifyLoginNameMailBySSL(String content) throws MessagingException {
            sendMailBySSL(SMTP,USERNAME,PASSWORD,MODIFYLOGINNAMETOUSER,"工作人员操作:用户名更换",content);
    }

    public static boolean sendMailBySSL(String smtp,String username,String password,String tousername,String subject,String content) throws MessagingException {
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

    public static String getValue (String key) {
        return pro.getProperty(key);
    }
}
