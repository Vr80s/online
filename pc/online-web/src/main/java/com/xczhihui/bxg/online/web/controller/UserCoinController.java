package com.xczhihui.bxg.online.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.base.utils.WebUtil;


/** 
 * ClassName: UserCoinController.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@RestController
@RequestMapping(value = "/userCoin")
public class UserCoinController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserCoinService userCoinService;

    @Value("${rate}")
    private int rate;
    @Value("${ENV_FLAG}")
    private String env;
    @Value("${minimum_amount}")
    private Double minimumAmount;
    
	/** 
	 * Description：获取用户余额
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/balance")
	@ResponseBody
	public ResponseObject balance(HttpServletRequest request) throws Exception {
		//获取登录用户
        BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		Map<String,String> balance = userCoinService.getBalanceByUserId(loginUser.getId());
		balance.put("account", loginUser.getLoginName());
		return ResponseObject.newSuccessResponseObject(balance);
	}
	
	@RequestMapping(value = "/cztest")
	@ResponseBody
	public ResponseObject cztest(HttpServletRequest request) throws Exception {
		//获取登录用户
//		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
//		UserCoinIncrease uci =new UserCoinIncrease();
//		uci.setChangeType(1);
//		uci.setUserId(loginUser.getId());
////		uci.setValue(100d);
//		userCoinService.updateBalanceForIncrease(uci);
		return ResponseObject.newSuccessResponseObject(true);
	}
	
	
	@RequestMapping(value = "/zstest")
	@ResponseBody
	public ResponseObject zstest(HttpServletRequest request) throws Exception {
		//获取登录用户
//		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
//		UserCoinIncrease uci =new UserCoinIncrease();
//		uci.setChangeType(2);
//		uci.setUserId(loginUser.getId());
////		uci.setValue(100d);
//		userCoinService.updateBalanceForIncrease(uci);
		return ResponseObject.newSuccessResponseObject(true);
	}
	
	@RequestMapping(value = "/userCoinIncreaseRecord")
	@ResponseBody
	public ResponseObject userCoinIncreaseRecord(HttpServletRequest request,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		return ResponseObject.newSuccessResponseObject(userCoinService.getUserCoinIncreaseRecord(loginUser.getId(), pageNumber, pageSize));
	}

	@RequestMapping(value = "/userCoinConsumptionRecord")
	@ResponseBody
	public ResponseObject userCoinConsumptionRecord(HttpServletRequest request,Integer pageNumber,Integer pageSize) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		return ResponseObject.newSuccessResponseObject(userCoinService.getUserCoinConsumptionRecord(loginUser.getId(), pageNumber, pageSize));
	}

	@RequestMapping(value = "/getRchargeOrderNo")
	@ResponseBody
	public ResponseObject getRchargeOrderNo(HttpServletRequest request) throws Exception {
		//获取登录用户
		BxgUser loginUser = UserLoginUtil.getLoginUser(request);
		if(loginUser!=null)
			return ResponseObject.newSuccessResponseObject(TimeUtil.getSystemTime() + RandomUtil.getCharAndNumr(12));
		return ResponseObject.newErrorResponseObject(null);
	}

	@RequestMapping(value = "/recharge/pay",method= RequestMethod.GET)
    public  ModelAndView saveOrder(HttpServletRequest request,String price) throws IOException {
        ModelAndView mav=new ModelAndView();
        Double count = Double.valueOf(price)*rate; 
		if(!WebUtil.isIntegerForDouble(count)){
			throw new RuntimeException("充值金额"+price+"兑换的熊猫币"+count+"不为整数");
		}
		if(minimumAmount > Double.valueOf(price)){
			throw new RuntimeException("充值金额低于最低充值金额："+minimumAmount);
		}
        
        OnlineUser u =  (OnlineUser)request.getSession().getAttribute("_user_");
        if( u != null){
            mav.setViewName("rechargePay");
            mav.addObject("actualPay", price);
            mav.addObject("courseName", "充值熊猫币:"+count+"个");
        }else{
        	//cichu跳转
        }
            return mav;
    }
	
	@RequestMapping(value = "/checkRechargeOrder")
	@ResponseBody
	public ResponseObject checkRechargeOrder(HttpServletRequest request,String orderNo) throws Exception {
		return ResponseObject.newSuccessResponseObject(userCoinService.checkRechargeOrder(orderNo));
	}

	@RequestMapping(value = "/userDataForRecharge")
	public ResponseObject getUserDataForRecharge(HttpServletRequest request,EnchashmentApplication ea) {
		OnlineUser u =  (OnlineUser)request.getSession().getAttribute("_user_");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("balanceTotal", userCoinService.getBalanceByUserId(u.getId()).get("balanceTotal"));
		m.put("account", u.getLoginName());
		m.put("rate", rate);
		m.put("env", env);
		return ResponseObject.newSuccessResponseObject(m);
	}
}