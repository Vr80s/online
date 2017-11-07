package com.xczh.consumer.market.controller;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import java.util.UUID;
//import com.xczh.consumer.market.bean.WxcpGoodsInfo;
//import com.xczh.consumer.market.bean.WxcpOrderGoods;
//import com.xczh.consumer.market.bean.WxcpOrderInfo;
//import com.xczh.consumer.market.bean.ExampleBean;
//import com.xczh.consumer.market.service.ExampleLocalService;
//import com.xczh.distributed.common.utils.ControllerUtil;

/**
 * 
 * 客户端用户访问控制器类
 * @author yanghui
 **/
@Controller
@RequestMapping("/bxg/client")
public class ClientUserController {
	
	@Autowired
	private WxcpClientUserService wxcpClientUserService;

	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	
	/**
	 * 拉取微信访问用户信息；
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("pullWxInfo")
	@ResponseBody
	public ResponseObject pullWxInfo (HttpServletRequest req, HttpServletResponse res) throws Exception {
		String auth_code = req.getParameter("code");
		String user_mobile = req.getParameter("user_mobile");
		String user_nick_name = req.getParameter("user_nick_name");
		//String public_id = req.getParameter("public_id");
		//String public_name = req.getParameter("public_name");
		
		String openid =  ClientUserUtil.setWxInfo (
				auth_code,
				user_mobile,
				user_nick_name,
				wxcpClientUserService,
				wxcpClientUserWxMappingService, 
				null, 
				null, 
				req, 
				res
				) ;
					
		if(openid != null && !openid.isEmpty()) {
			JSONObject jsonObject = JSONObject.fromObject(openid);
			System.out.println("pullWxInfo->\r\n\t"+"openid="+jsonObject.get("openid") + "&code=" + auth_code);
			return ResponseObject.newSuccessResponseObject("openid="+jsonObject.get("openid") + "&code=" + auth_code);
		} else {
			System.out.println("pullWxInfo->\r\n\t"+"openid=,code=");
			return ResponseObject.newSuccessResponseObject("openid=,code=");
		}
	}	
	
}
