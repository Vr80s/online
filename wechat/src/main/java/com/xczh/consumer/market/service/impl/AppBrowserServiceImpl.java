package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Map;

@Service
public class AppBrowserServiceImpl implements AppBrowserService {

	@Autowired
	private CacheService cacheService;
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Override
	public OnlineUser getOnlineUserByReq(HttpServletRequest request){
		String token = request.getParameter("token");
		OnlineUser ou = null;
		/**
		 * 判断来自浏览器呢还是来自app呢
		 *   app端传递参数会有token
		 *   浏览器没有
		 */
		if(null != token && !"".equals(token) && !"null".equals(token)){
			ou = cacheService.get(token);
		}else{
			ou = (OnlineUser) request.getSession().getAttribute("_user_");
			request.getSession().setAttribute("_user_", ou);
			Token t = UCCookieUtil.readTokenCookie(request);
			if(ou == null && t!=null){
				try {
					ou = onlineUserService.findUserByLoginName(t.getLoginName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getSession().setAttribute("_user_", ou);
			}
		}
		return ou;
	}
}
