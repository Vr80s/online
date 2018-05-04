package com.xczh.consumer.market.controller;

import com.xczh.consumer.market.utils.ResponseObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * 客户端用户访问控制器类
 * 
 * @author yanghui
 **/
@Controller
@RequestMapping("/bxg/pay")
public class H5AppPayController {

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(H5AppPayController.class);
	
	/**
	 * 拉取微信访问用户信息；
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("wxPay")
	@ResponseBody
	@Transactional
	public ResponseObject appOrderPay(HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		
		LOGGER.info("老版本方法----》》》》wxPay");
		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
	}

	/**
	 * 微信统一打赏入口
	 * @param req
	 * @param res
	 * @param params
	 * 1pc,2app,3h5 4微信
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rewardPay")
	@ResponseBody
	public ResponseObject rewardPay(HttpServletRequest req,
                                    HttpServletResponse res, Map<String, String> params)
			throws Exception {

		LOGGER.info("老版本方法----》》》》rewardPay");
		
		return ResponseObject.newErrorResponseObject("请使用最新版本");
	}


	/**
	 * 微信统一充值入口
	 * @param req
	 * @param res
	 * @param params
	 * 1pc,2app,3h5 4微信
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rechargePay")
	@ResponseBody
	public ResponseObject rechargePay(
			HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		
		LOGGER.info("老版本方法----》》》》rechargePay");

		return ResponseObject.newErrorResponseObject("请使用最新版本");
	}
}
