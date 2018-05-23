package com.xczh.consumer.market.controller.threeparties;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.ThridFalg;
import com.xczhihui.user.center.vo.Token;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
	private	WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

	@Autowired
	private UserCenterService userCenterService;

	@Autowired
	private OnlineUserMapper onlineUserMapper;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	@Value("${wechatpay.h5.appid}")
	private String gzh_appid;
	
	
	@Value("${webdomain}")
	private String webdomain;

	/**
	 * 国医学堂  -- 个人中心
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToPersonalCenter")
	public void publicToPersonalCenter(HttpServletRequest req,
									   HttpServletResponse res, Map<String, String> params)
			throws Exception {

		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");

			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			String openid = wxw.getOpenid();
			/**
			 * 因为没有退出登录了，所以残留的是上一个用户的coke
			 */
/*			OnlineUser currentOnlineUser = appBrowserService.getOnlineUserByReq(req);
			if(currentOnlineUser !=null){
				res.sendRedirect(returnOpenidUri + "/xcview/html/my_homepage.html?openId="+openid);
				return;
			}*/

			LOGGER.info(" ==========================  " + req.getParameter("code"));
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
				OeUserVO oeUserVO = userCenterService.getUserVO(ou.getLoginName());
				Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);
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
				//res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&jump_type=2");
				/**
				 * 写入这个cookie
				 */
				ThridFalg tf = new ThridFalg();
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName( StringUtils.isNotBlank(wxw.getNickname()) ? wxw.getNickname() : "熊猫中医" );
				
				String defaultHeadImg = webdomain+"/web/images/defaultHead/18.png";
				tf.setHeadImg(StringUtils.isNotBlank(wxw.getHeadimgurl()) ? wxw.getHeadimgurl() : defaultHeadImg );
				UCCookieUtil.writeThirdPartyCookie(res,tf);
				LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"+UCCookieUtil.readThirdPartyCookie(req));
				res.sendRedirect(returnOpenidUri + "/xcview/html/my_homepage.html?openId="+openid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 国医学堂   -- 推荐页面
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToRecommended")
	public void publicToRecommended(HttpServletRequest req, HttpServletResponse res,
									Map<String, String> params) throws Exception {
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			LOGGER.info("wxw===="+wxw);
			String openid = wxw.getOpenid();

//			OnlineUser currentOnlineUser = appBrowserService.getOnlineUserByReq(req);
//			if(currentOnlineUser !=null){
//				res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+openid);
//				return;
//			}
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(StringUtils.isNotBlank(wxw.getClient_id())){
				LOGGER.info("wxw.getClient_id()===="+wxw.getClient_id());
				OnlineUser ou =  onlineUserMapper.findUserById(wxw.getClient_id());
				LOGGER.info("getLoginName===="+ou.getLoginName());
				Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);

				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+openid);
				} else{
					res.getWriter().write(openid);
				}
			}else{
				ThridFalg tf = new ThridFalg();
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName(wxw.getNickname());
				tf.setHeadImg(wxw.getHeadimgurl());
				UCCookieUtil.writeThirdPartyCookie(res,tf);

				LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"+UCCookieUtil.readThirdPartyCookie(req));
				res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&jump_type=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 国医学堂  -- 学习中心
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToLearningCenter")
	public void publicToLearningCenter(HttpServletRequest req, HttpServletResponse res,
									   Map<String, String> params) throws Exception {
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			LOGGER.info("wxw===="+wxw);
			String openid = wxw.getOpenid();
//			OnlineUser currentOnlineUser = appBrowserService.getOnlineUserByReq(req);
//			if(currentOnlineUser !=null){
//				res.sendRedirect(returnOpenidUri + "/xcview/html/my_study.html?openId="+openid);
//				return;
//			}
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(StringUtils.isNotBlank(wxw.getClient_id())){
				LOGGER.info("wxw.getClient_id()===="+wxw.getClient_id());
				OnlineUser ou =  onlineUserMapper.findUserById(wxw.getClient_id());
				LOGGER.info("getLoginName===="+ou.getLoginName());
				Token t = userCenterService.loginThirdPart(ou.getLoginName(),TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);

				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/my_study.html?openId="+openid);
				} else{
					res.getWriter().write(openid);
				}
			}else{
				/**
				 * jump_type=1	跳到首页
				 * jump_type=2	跳到我的页面
				 */
				/**
				 * 写入这个cookie
				 */
				ThridFalg tf = new ThridFalg();
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName(wxw.getNickname());
				tf.setHeadImg(wxw.getHeadimgurl());
				UCCookieUtil.writeThirdPartyCookie(res,tf);
				LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"+UCCookieUtil.readThirdPartyCookie(req));
				res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&jump_type=1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 国医学堂  -- 学习中心
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("publicToMenuTypeList")
	public void publicToMenuTypeList(HttpServletRequest req, HttpServletResponse res,
									 Map<String, String> params) throws Exception {
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {

			String code = req.getParameter("code");
			String menuType = req.getParameter("menuType");

			WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
			LOGGER.info("wxw===="+wxw);
			String openid = wxw.getOpenid();

//			OnlineUser currentOnlineUser = appBrowserService.getOnlineUserByReq(req);
//			if(currentOnlineUser !=null){
//				res.sendRedirect(returnOpenidUri + "/xcview/html/curriculum_table.html?openId="+openid+"&menuType="+menuType);
//				return;
//			}
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if(StringUtils.isNotBlank(wxw.getClient_id())){
				LOGGER.info("wxw.getClient_id()===="+wxw.getClient_id());
				OnlineUser ou =  onlineUserMapper.findUserById(wxw.getClient_id());
				LOGGER.info("getLoginName===="+ou.getLoginName());
				Token t = userCenterService.loginThirdPart(ou.getLoginName(), TokenExpires.TenDay);
				ou.setTicket(t.getTicket());
				onlogin(req,res,t,ou,t.getTicket());
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);

				if (openid != null && !openid.isEmpty()) {
					res.sendRedirect(returnOpenidUri + "/xcview/html/curriculum_table.html?openId="+openid+"&menuType="+menuType);
				} else{
					res.getWriter().write(openid);
				}
			}else{
				ThridFalg tf = new ThridFalg();
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName(wxw.getNickname());
				tf.setHeadImg(wxw.getHeadimgurl());
				UCCookieUtil.writeThirdPartyCookie(res,tf);
				LOGGER.info("readThirdPartyCookie{}{}{}{}{}{}"+UCCookieUtil.readThirdPartyCookie(req));
				res.sendRedirect(returnOpenidUri + "/xcview/html/curriculum_table.html?openId="+openid+"&unionId="+wxw.getUnionid()+"&menuType="+menuType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录成功处理
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
