import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailBySSL {
    private static Logger logger = LoggerFactory.getLogger(MailBySSL.class);

    public static boolean sendMailBySSL(String user, String svnpassword) throws AddressException, MessagingException {
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
        final String password = "Ixincheng123456";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // -- Create a new message --
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("system@ixincheng.com"));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(user + "@ixincheng.com", false));
        msg.setSubject("svn密码变更通知");
        msg.setText("你好,同学：svn密码统一升级，你的svn账号:" + user + "密码变更为：" + svnpassword);
        logger.info(user + ":" + svnpassword);
        msg.setSentDate(new Date());
        Transport.send(msg);

        logger.info("XgMessage sent.");
        return true;
    }
}