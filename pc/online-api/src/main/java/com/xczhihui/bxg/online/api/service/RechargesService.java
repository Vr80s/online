package com.xczhihui.bxg.online.api.service;

import java.util.List;

import com.xczhihui.bxg.online.api.po.Recharges;




/** 
 * ClassName: RechargesService.java <br>
 * Description: 充值列表<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
public interface RechargesService {
	

	/**
	 * Description：获取所有的充值面额
	 * @return
	 * @return List<Gift>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public List<Recharges> getRecharges();
	
}
