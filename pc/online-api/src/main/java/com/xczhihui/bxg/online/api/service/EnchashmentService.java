package com.xczhihui.bxg.online.api.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.common.enums.OrderFrom;

import java.util.Map;


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
     * Description：提现申请表
     * @param pageNumber
     * @param pageSize
     * @return
     * @return Page<EnchashmentApplication>
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public Page<EnchashmentApplication>  enchashmentApplicationList(String userId,Integer pageNumber, Integer pageSize);

	/** 
	 * Description：保存提现申请
	 * @param ea  
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void saveEnchashmentApplication(EnchashmentApplication ea);

	/** 
	 * Description：获取可提现熊猫币余额
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public String enableEnchashmentBalance(String userId);
	
	/** 
	 * Description：获取可提现余额（充值+打赏礼物）
	 * @return
	 * @return Object
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Double enableEnchashmentRmbBalance(String userId);

	/** 
	 * Description：获取提现表单中相应数据：姓名,手机号，可提现金额
	 * @param userId
	 * @return
	 * @return Map<String,String>
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public Map<String,Object> getEnableEnchashmentData(String userId);
}
