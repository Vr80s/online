package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/** 
 * ClassName: EnchashmentController.java <br>
 * Description: 提现相关<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月17日<br>
 */
@RestController("EnchashmentController1")
@RequestMapping(value = "/enchashment")
public class EnchashmentController {

	@Autowired
	private EnchashmentService enchashmentService;

	@Autowired
	private UserCoinService userCoinService;

	/**
	 * Description：可提现余额
	 * @param request
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/enableEnchashmentBalance")
	public ResponseObject enableEnchashmentBalance(HttpServletRequest request) {
		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
		return ResponseObject.newSuccessResponseObject(userCoinService.getEnchashmentBalanceByUserId(user.getId()));
	}
	
	/** 
	 * Description：发起提现申请
	 * @param request
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/enchashment")
	public ResponseObject saveEnchashment(HttpServletRequest request,BigDecimal enchashmentSum,int bankCardId) {
		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
		enchashmentService.saveEnchashmentApplyInfo(user.getId(),enchashmentSum,bankCardId, OrderFrom.PC);
		return ResponseObject.newSuccessResponseObject("提现申请发起成功！");
	}
//
//	@RequestMapping(value = "/test")
//	public ResponseObject test(HttpServletRequest request) {
//		EnchashmentApplication ea = new EnchashmentApplication();
//		ea.setEnchashmentAccount("18611762456");
//		ea.setPhone("18611762456");
//		ea.setEnchashmentAccountType(0);
//		ea.setEnchashmentSum(new BigDecimal("0.01"));
//		ea.setRealName("testa[o");
//
//		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
//		ea.setClientType(1);
//		ea.setEnchashmentStatus(0);
//		ea.setUserId(user.getId());
//		enchashmentService.saveEnchashmentApplication(ea);
//		return ResponseObject.newSuccessResponseObject("提现申请发起成功！");
//	}
//
//	@RequestMapping(value = "/enchashmentData")
//	public ResponseObject getEnableEnchashmentData(HttpServletRequest request,EnchashmentApplication ea) {
//		OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
//		return ResponseObject.newSuccessResponseObject(enchashmentService.getEnableEnchashmentData(user.getId()));
//	}

}

