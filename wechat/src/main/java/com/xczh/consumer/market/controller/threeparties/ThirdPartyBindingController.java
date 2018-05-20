package com.xczh.consumer.market.controller.threeparties;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.ThirdPartyType;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.course.model.QQClientUserMapping;
import com.xczhihui.course.model.WeiboClientUserMapping;
import com.xczhihui.course.service.IThreePartiesLoginService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weibo4j.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

/**
 * 
 * ClassName: ThirdPartyCertificationController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
@Controller
@RequestMapping(value = "/xczh/bind")
public class ThirdPartyBindingController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ThirdPartyBindingController.class);
	
	
	public HttpClient client = new HttpClient();
	
	@Autowired
	private IThreePartiesLoginService threePartiesLoginService;
	
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	//手机端登录使用
	@Value("${mobile.authorizeURL}")
	public  String weiboMobileAuthorizeURL;

	/**
	 * Description：获取当前用户的绑定信息
	 * @param req
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="userBindingInfo")
	@ResponseBody
	public ResponseObject userBindingInfo(HttpServletRequest req,
			HttpServletResponse res ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			Map<String,Object> map = threePartiesLoginService.selectUserBindingInfo(ou.getId());
			return ResponseObject.newSuccessResponseObject(map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("获取用户绑定信息有误");
		}
	}
	/**
	 * Description：解除绑定
	 * @param req
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="removeBinding")
	@ResponseBody
	public ResponseObject removeBinding(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("unionId")String unionId,
			@RequestParam("type")Integer type ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			if(type==ThirdPartyType.WECHAT.getCode()){  //微信
				WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserIdAndUnionId(ou.getId(), unionId);
				m.setClient_id("");
				wxcpClientUserWxMappingService.update(m);
			}else if(type==ThirdPartyType.QQ.getCode()){
				QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByUserIdAndOpenId(ou.getId(), unionId);
		    	qq.setUserId("");
		    	threePartiesLoginService.updateQQInfoAddUserId(qq);
			}else if(type==ThirdPartyType.WEIBO.getCode()){
				WeiboClientUserMapping weibo = threePartiesLoginService.selectWeiboClientUserMappingByUserIdAndUid(ou.getId(), unionId);
		    	weibo.setUserId("");
		    	threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			}
			return ResponseObject.newSuccessResponseObject("解除绑定成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("解除绑定失败");
		}
	}
	
	/**
	 * Description：用现有的手机号绑定微信、微博或者qq
	 * @param req
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="mobileBindingThirdParty")
	public ResponseObject addBinding(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("unionId")String unionId,
			@RequestParam("type")Integer type ){
		/**
		 * 获取用户信息
		 */
		OnlineUser ou = appBrowserService.getOnlineUserByReq(req);
		if(ou ==null){
			return ResponseObject.newErrorResponseObject("登录失效");
		}
		try {
			if(type==ThirdPartyType.WECHAT.getCode()){  //微信
				WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByUserIdAndUnionId(ou.getId(), unionId);
				m.setClient_id("");
				wxcpClientUserWxMappingService.update(m);
			}else if(type==ThirdPartyType.QQ.getCode()){
				QQClientUserMapping qq = threePartiesLoginService.selectQQClientUserMappingByUserIdAndOpenId(ou.getId(), unionId);
		    	qq.setUserId("");
		    	threePartiesLoginService.updateQQInfoAddUserId(qq);
			}else if(type==ThirdPartyType.WEIBO.getCode()){
				WeiboClientUserMapping weibo = threePartiesLoginService.selectWeiboClientUserMappingByUserIdAndUid(ou.getId(), unionId);
		    	weibo.setUserId("");
		    	threePartiesLoginService.updateWeiboInfoAddUserId(weibo);
			}
			return ResponseObject.newSuccessResponseObject("解除绑定成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseObject.newSuccessResponseObject("解除绑定失败");
		}
	}

}
