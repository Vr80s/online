package com.xczhihui.bxg.online.api.service;

import com.xczhihui.bxg.online.common.enums.OrderFrom;

import java.math.BigDecimal;


/** 
 * ClassName: EnchashmentService.java <br>
 * Description: 提现相关业务层<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月17日<br>
 */
public interface EnchashmentService {

	/**
	 * Description：结算
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 7:04 2018/1/27 0027
	 **/
	public void saveSettlement(String userId, int amount,OrderFrom orderFrom);

	/**
	 * Description：提现
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:17 2018/1/29 0029
	 **/
	void saveEnchashmentApplyInfo(String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom);

}
