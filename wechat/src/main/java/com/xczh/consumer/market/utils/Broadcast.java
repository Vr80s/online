package com.xczh.consumer.market.utils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 
 * ClassName: Broadcast.java <br>
 * Description: 送礼物IM系统广播工具类<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月24日<br>
 */
@Component
public class Broadcast {
	
//	private    String host = "47.92.39.21";
//	private  int port = 5222;
//	private   String account = "15@47.92.39.21";
//	private   String password = "system-prod-mobile";
//	private   String postfix="xczh@conference.47.92.39.21";

	@Value("${gift.im.host}")
	private  String host;
	@Value("${gift.im.port}")
	private  int port;
	@Value("${gift.im.system.account}")
	private  String account;
	@Value("${gift.im.system.password}")
	private  String password;
	@Value("${gift.im.room.postfix}")
	private  String postfix;

	public   void loginAndSend(String roomId, String body) throws XMPPException, SmackException, IOException{
		synchronized (Broadcast.class){
		XMPPTCPConnection connection = null;
		SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
		SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
		if(connection == null){
			connection = getConnection(connection);
			connection.login();
		}
		// 是否已经通过身份验证
		System.out.println("Authenticated = " + connection.isAuthenticated());
		if(!connection.isAuthenticated()){
			connection.login();
		}
		MultiUserChatManager mMultiUserChatManager = MultiUserChatManager.getInstanceFor(connection);
		joinRoom(mMultiUserChatManager,roomId,body);
		connection.disconnect();
		connection.instantShutdown();
//		connection.

		}
	}

	/**
	 * 获得与服务器的连接 config.setSASLAuthenticationEnabled(false);
	 *
	 * @return
	 */
	public   XMPPTCPConnection getConnection(XMPPTCPConnection connection) {
		try {
			if (connection == null) {
				XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration
						.builder()
						// 服务器IP地址
						.setHost(host)
						// 服务器端口
						.setPort(port)
						// 服务器名称(管理界面的 主机名)
						.setServiceName(host)
						// 是否开启安全模式
						.setSecurityMode(
								XMPPTCPConnectionConfiguration.SecurityMode.disabled)
						// .set
						// 是否开启压缩
						.setCompressionEnabled(false).setResource(UUID.randomUUID().toString().replaceAll("-", ""))
						.setUsernameAndPassword(account,password)
						// 开启调试模式
						.setDebuggerEnabled(false).build();
				connection = new XMPPTCPConnection(config);
				connection.connect();
			}
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public    void joinRoom(MultiUserChatManager mMultiUserChatManager,String roomId,String body){
		try {
			MultiUserChat multiUserChat=mMultiUserChatManager.getMultiUserChat(roomId+postfix);
			multiUserChat.join("system");
			System.out.println(body);
			Message message=multiUserChat.createMessage();
			message.setBody(body);
			multiUserChat.sendMessage(message);
			multiUserChat.leave();
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
}

