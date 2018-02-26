package com.xczh.consumer.market.controller.threeparties;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.util.HttpsRequest;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/wxpublic")
public class H5WeChatSetController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(H5WeChatSetController.class);
	
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private	WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private OnlineUserMapper onlineUserMapper;
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	/**
	 * 
	 * Description：设置微信公众号下的菜单
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("setWxMenu")
	@ResponseBody
	public ResponseObject setWxMenu (HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		String access_token = req.getParameter("access_token");
		access_token ="6mcGpY9ORGOF_Vw7s0VdYnSoNIaOTeYnJWrHAcb1Xaihi7dIDi-SqjV6B_uY4FJ_N6PT2NKtYKQjCWvVB5OTptOea-JBV13UEfYmskk2L1wTBCeABADLM";
		if(access_token == null || access_token.isEmpty()) {
            return null;
        }
		String strUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"
				.replace("access_token=ACCESS_TOKEN", "access_token=" + access_token);
		
		String strLinkHome 	= 	" \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri="+returnOpenidUri+"/xczh/wxpublic/publicToPersonalCenter&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\" "
								.replace("appid=APPID", "appid=wx81c7ce773415e00a");
		String strMyCenter 	= 	" \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri="+returnOpenidUri+"/xczh/wxpublic/publicToRecommended&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\" "
				.replace("appid=APPID", "appid=wx81c7ce773415e00a");
		
//		String strMyShare 	= 	" \"url\":\"+"returnOpenidUri"+/Views/h5/my_share.html\" ";
//		String strMyCenter 	= 	" \"url\":\"+"returnOpenidUri"+/bxg/page/personal\" ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("	{																");
		sb.append("	\"button\":                                                		");
		sb.append("	[                                                         		");
		sb.append(" 	{	                                                  		");
		sb.append("    	\"type\":\"view\",                                     		");
		sb.append("    	\"name\":\"在线课堂\",                                   		");		
		sb.append(strLinkHome);														//sb.append("    	\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx42fe48fd3935151e&redirect_uri=+"returnOpenidUri"+/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\"           ");//sb.append("          \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe8667c589b5bdc0c&redirect_uri=+"returnOpenidUri"+/bxg/wxpay/h5GetOpenid&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect\"           ");
		sb.append("    	},                                                      	");
//		sb.append("     {	                                                    	");
//		sb.append("    	\"type\":\"view\",                                     		");
//		sb.append("    	\"name\":\"我的分享\",                                      	");
//		sb.append(strMyShare);														//sb.append("    	\"url\":\"+"returnOpenidUri"+/Views/h5/my_center.html\"	");
//		sb.append("    	},                                                       	");
		sb.append("     {	                                                    	");
		sb.append("    	\"type\":\"view\",                                     		");
		sb.append("    	\"name\":\"个人中心\",                                      	");
		sb.append(strMyCenter);														//sb.append("    	\"url\":\"+"returnOpenidUri"+/Views/h5/my_center.html\"   ");
		sb.append("    	}                                                       	");
		sb.append("	]                                                               ");
		sb.append("	}                                                               ");		
			
		HttpsRequest request = new HttpsRequest();
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		String buffer = request.sendPost2(strUrl, jsonObject);		
		return ResponseObject.newSuccessResponseObject(buffer);
	}	
	
	
	/**
	 * 个人中心    -- 我的页面
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToPersonalCenter")
	public void h5GetOpenidForPersonal(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			String openid = wxw.getOpenid();
			
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			
			if(StringUtils.isNotBlank(wxw.getClient_id())){    //如果绑定过了，就直接ok了。
				
			    //这里回调的时候不能默认登录
				
				LOGGER.info("wxw.getClient_id()===="+wxw.getClient_id());
				
				OnlineUser ou =  onlineUserMapper.findUserById(wxw.getClient_id());
				
				LOGGER.info("getLoginName===="+ou.getLoginName());
				
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/my_homepage.html?openId="+openid);
				} else{
					res.getWriter().write(openid);
				}	
			}else{       //--否则也去推荐页，需要标记下，
				/**
				 * jump_type=1	跳到首页
				 * jump_type=2	跳到我的页面
				 */
				//否则跳转到这是页面。绑定下手机号啦   -- 如果从个人中心进入的话，也需要绑定手机号啊，绑定过后，就留在这个页面就行。
				res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&jump_type=2");
				//res.sendRedirect(returnOpenidUri + "/xcviews/html/my.html?"+ "openId="+openid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//res.getWriter().write(e.getMessage());
		}
	}
	
	
	/**
	 * 在线课堂   -- 推荐页面
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToRecommended")
	public void h5GetOpenid(HttpServletRequest req, HttpServletResponse res,
			Map<String, String> params) throws Exception {
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			
			LOGGER.info("wxw===="+wxw);
			
			String openid = wxw.getOpenid();
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(StringUtils.isNotBlank(wxw.getClient_id())){
				
				LOGGER.info("wxw.getClient_id()===="+wxw.getClient_id());
				
				OnlineUser ou =  onlineUserMapper.findUserById(wxw.getClient_id());
				LOGGER.info("getLoginName===="+ou.getLoginName());
			    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
				Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+openid);
					//res.sendRedirect(returnOpenidUri + "/bxg/page/index/"+ openid + "/" + code);
				} else{
					res.getWriter().write(openid);
				}	
			}else{
				/**
				 * jump_type=1	跳到首页
				 * jump_type=2	跳到我的页面
				 */
				//否则跳转到这是页面。绑定下手机号啦   -- 如果从个人中心进入的话，也需要绑定手机号啊，绑定过后，就留在这个页面就行。
				res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&jump_type=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//res.getWriter().write(e.getMessage());
		}
	}
	
	
	
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket){
		// 用户登录成功
		// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
		req.getSession().invalidate();
		// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
		// 获得到ServletCOntext中存的Map集合.
		Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req.getServletContext()
				.getAttribute("userMap");
		// 判断用户是否已经在map集合中'
		HttpSession session = userMap.get(user);
		if(session!=null && userMap.containsKey(user)){
			/**
			 * 得到客户端信息
			 */
			Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
			//session.invalidate();
			session.setAttribute("topOff", mapClientInfo);
			session.setAttribute("_user_",null);
		}else if(session!=null){
			session.setAttribute("topOff",null);
		}
		// 使用监听器:HttpSessionBandingListener作用在JavaBean上的监听器.
		req.getSession().setMaxInactiveInterval(1000);//设置session失效时间
		req.getSession().setAttribute("_user_", user);
		/**
		 * 这是cookie 
		 */
		UCCookieUtil.writeTokenCookie(res, token);
	}
	
}
