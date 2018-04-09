package com.xczhihui.order.service;

import java.lang.reflect.InvocationTargetException;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.po.EnchashmentApplyInfo;

public interface EnchashmentService {

	public Page<EnchashmentApplyInfo> findEnchashmentPage(
			EnchashmentApplyInfo orderVo, Integer pageNumber, Integer pageSize);

	/**
	 * Description：处理提现申请
	 * 
	 * @param ea
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @return void
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 **/
	public void updateHandleEnchashment(EnchashmentApplyInfo ea);

}
