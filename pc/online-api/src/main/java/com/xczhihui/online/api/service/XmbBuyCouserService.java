package com.xczhihui.online.api.service;


/**
 * 订单业务层接口类
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
public interface XmbBuyCouserService {
   
	
	public void xmbBuyCourseLock(String orderNo);
	


	public void focusHostLock(String lecturerId, String userid, Integer type);
	
}
