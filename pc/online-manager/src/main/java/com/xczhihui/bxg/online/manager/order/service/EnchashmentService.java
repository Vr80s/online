package com.xczhihui.bxg.online.manager.order.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;

public interface EnchashmentService {

	public Page<EnchashmentApplication> findEnchashmentPage(EnchashmentApplication orderVo, Integer pageNumber, Integer pageSize);

	/** 
	 * Description：处理提现申请
	 * @param ea
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void updateHandleEnchashment(EnchashmentApplication ea) throws IllegalAccessException, InvocationTargetException;

}
