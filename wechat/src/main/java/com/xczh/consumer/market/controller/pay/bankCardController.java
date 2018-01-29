package com.xczh.consumer.market.controller.pay;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.pay.model.UserBank;
import com.xczhihui.medical.pay.service.IUserBankService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产控制器 ClassName: bankCardController.java <br>
 * Description:
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/medical")
public class bankCardController {

	@Autowired
	private ICourseApplyService courseApplyService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private IUserBankService userBankService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(bankCardController.class);

	/**
	 * 添加银行卡
	 */

	@RequestMapping("addBankCard")
	@ResponseBody
	public ResponseObject addCourseApply(HttpServletRequest req,HttpServletResponse res,UserBank userBank,
										 @RequestParam("acctName")String acctName,@RequestParam("acctPan")String acctPan,
										 @RequestParam("certId")String certId)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		UserBank i = new UserBank();
		/*if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}*/
		String userId="2c9aec35605a5bab01605a632d350000";
		userBank.setUserId(userId);

			userBankService.addUserBank(userBank);
			return  ResponseObject.newSuccessResponseObject("添加成功");

	}

	@RequestMapping(value = "userBankList")
	@ResponseBody
	public ResponseObject selectUserBankbyUserId(HttpServletRequest req) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		/*if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}*/
		String userId="2c9aec35605a5bab01605a632d350000";
		List<UserBank> userBankList = userBankService.selectUserBankByUserId(userId);
		return  ResponseObject.newSuccessResponseObject(userBankList);
	}

}
