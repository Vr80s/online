package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface AppBrowserService {


	/**
	 * 
	 * Description：根据app中的token 或者浏览器中的 session 中的获取用户信息
	 * @param request
	 * @return
	 * @return OnlineUser
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	OnlineUser getOnlineUserByReq(HttpServletRequest request);

	
	
	
}
