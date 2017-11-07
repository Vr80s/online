package com.xczhihui.bxg.online.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.bxg.online.common.domain.OnlineUser;

/**
 * 回答相关
 * @author Haicheng Jiang
 */
public interface ThirdSystemService {

	public String sendMobileMessage(String systemName,String mobile, String content,long timestamp, String sign);
	
	public void addSendVerificationCode(HttpServletRequest req,HttpServletResponse res) throws Exception;

	public OnlineUser getUserInfo(String systemName, String loginName, long timestamp, String sign);
	
}
