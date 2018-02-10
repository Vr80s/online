package com.xczh.consumer.market.controller.pay;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.common.enums.BankCardType;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.anchor.vo.UserBank;
import org.redisson.api.RLock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
	@Autowired
	private RedissonUtil redissonUtil;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(bankCardController.class);

	/**
	 * 添加银行卡
	 */

	@RequestMapping("addBankCard")
	@ResponseBody
	public ResponseObject addCourseApply(HttpServletRequest req,HttpServletResponse res,
										 @RequestParam("acctName")String acctName,
										 @RequestParam("acctPan")String acctPan,
										 @RequestParam("certId")String certId,
										 @RequestParam("tel")String tel)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}

		// 获得锁对象实例
		RLock redissonLock = redissonUtil.getRedisson().getLock("addUserBank"+user.getId());

		boolean resl = false;
		try {
			//等待3秒 有效期8秒
			resl = redissonLock.tryLock(3, 8, TimeUnit.SECONDS);
			if(resl){
				userBankService.addUserBank(user.getId(),acctName,acctPan,certId,tel);
			}
		}catch (RuntimeException e){
			throw e;
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("网络错误，请重试");
		}finally {
			if(resl){
				redissonLock.unlock();
			}else{
				throw new RuntimeException("网络错误，请重试");
			}
		}
			return  ResponseObject.newSuccessResponseObject("添加成功");

	}
	/**
	 * 获取银行卡列表
	 */
	@RequestMapping(value = "userBankList")
	@ResponseBody
	public ResponseObject selectUserBankbyUserId(HttpServletRequest req) throws Exception{

		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}

		List<UserBank> userBankList = userBankService.selectUserBankByUserId(user.getId());
		return  ResponseObject.newSuccessResponseObject(userBankList);
	}
	/**
	 * 获取银行列表
	 */
	@RequestMapping(value = "getBankCardList")
	@ResponseBody
	public ResponseObject getBankCardList(HttpServletRequest req) throws Exception{

        List<Map>  getBankCardList = BankCardType.getBankCardList();
		return  ResponseObject.newSuccessResponseObject(getBankCardList);
	}
	/**
	 * 删除银行卡
	 */
	@RequestMapping(value = "deleteBankCard")
	@ResponseBody
	public ResponseObject deleteBankCard(HttpServletRequest req,
										 @RequestParam("id")Integer id) throws Exception{
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		userBankService.deleteBankCard(user.getId(),id);
		return  ResponseObject.newSuccessResponseObject("删除成功");
	}
	/**
	 * 设置默认银行卡
	 */
	@RequestMapping(value = "updateDefault")
	@ResponseBody
	public ResponseObject updateDefault(HttpServletRequest req,
										 @RequestParam("id")Integer id) throws Exception{
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		userBankService.updateDefault(user.getId(),id);
		return  ResponseObject.newSuccessResponseObject("设置成功");
	}
	/**
	 * 获取默认银行卡
	 */
	@RequestMapping(value = "getDefault")
	@ResponseBody
	public ResponseObject getDefault(HttpServletRequest req) throws Exception{
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(user==null){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		UserBank ub = userBankService.getDefault(user.getId());
		return  ResponseObject.newSuccessResponseObject(ub);
	}

}
