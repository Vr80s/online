package com.xczh.consumer.market.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信核心的消息业务
 * ClassName: CoreMessageService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年3月19日<br>
 */
public interface CoreMessageService {

	
	 public String processRequest(HttpServletRequest request);
}
