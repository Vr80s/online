package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface AppBrowserService {


	//OnlineUser getOnlineUserByReq(HttpServletRequest request);

	OnlineUser getOnlineUserByReq(HttpServletRequest request);

	
	
	
}
