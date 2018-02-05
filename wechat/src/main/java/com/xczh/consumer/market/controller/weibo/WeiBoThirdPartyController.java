package com.xczh.consumer.market.controller.weibo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.http.HttpClient;
import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SLEmojiFilter;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczhihui.bxg.online.common.enums.ThirdPartyType;
import com.xczhihui.bxg.online.common.enums.UserUnitedStateType;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.wechat.course.model.WeiboClientUserMapping;
import com.xczhihui.wechat.course.service.IThreePartiesLoginService;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/weibo")
public class WeiBoThirdPartyController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WeiBoThirdPartyController.class);
	
	
	public HttpClient client = new HttpClient();
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private IThreePartiesLoginService threePartiesLoginService;
	
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	//手机端登录使用
	@Value("${mobile.authorizeURL}")
	public  String weiboMobileAuthorizeURL;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	
	/**
	 * Description：h5 --》微博回调接口  -- 回调了接口后需要请求  
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="evokeWeiBoRedirect")
	public void evokeWeiBoRedirect(HttpServletRequest req,
			HttpServletResponse res){
		
		String code = req.getParameter("code");
		LOGGER.info("微博用户授权登录成功code"+code);
		String state = (String)((HttpServletRequest)req).getSession().getAttribute("weibo_connect_state");
		Oauth oauth = new Oauth();
		try {
			/**
			 * 通过code获取认证 微博唯一票据
			 */
			AccessToken at = oauth.getAccessTokenByCode(code);
			String uId = at.getUid();
			LOGGER.info("微博唯一票据--------》认证AccessToken成功:"+at.getAccessToken());
			LOGGER.info("微博用户唯一uid--------:"+at.getUid());
			if ((state == null) || (state.equals(""))) {
				LOGGER.info("获取accessToken信息有误：");
			    res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
			}
			/**
			 * 将用户信息保存到数据库中。
			 */
			//封装实体bean
			WeiboClientUserMapping wbuser = new WeiboClientUserMapping();
			try {
				/**
				 * 根据票据和用户id得到用户信息
				 */
				Users um = new Users();
				um.client.setToken(at.getAccessToken());
				
				/**
				 * 其实如果存在的话可以做更新操作了啊
				 */
				WeiboClientUserMapping wcum = threePartiesLoginService.selectWeiboClientUserMappingByUid(at.getUid());
				LOGGER.info("是否存在此微博号--------:"+wcum);
				if(wcum==null){
					JSONObject job = um.client.get(WeiboConfig.getValue("baseURL") + "users/show.json",
					        new PostParameter[] {new PostParameter("uid", at.getUid())}).asJSONObject();
					
					wbuser = new WeiboClientUserMapping(job);
					wbuser.setId(UUID.randomUUID().toString().replace("-", ""));
					//用户昵称
					String screenName =wbuser.getScreenName();
					screenName= SLEmojiFilter.filterEmoji(screenName);
					wbuser.setScreenName(screenName);
					//友好显示名称
					String name =wbuser.getName();
					name= SLEmojiFilter.filterEmoji(name);
					wbuser.setName(name);
					threePartiesLoginService.saveWeiboClientUserMapping(wbuser);
					
				
					 //直接重定向到完善信息
		             res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?uId="+uId+"&type="+ThirdPartyType.WEIBO.getCode());
				
				}else if(wcum.getUserId()!=null){ //绑定了用户信息了
					
					LOGGER.info("绑定了用户信息了-wcum.getUserId()-------:"+wcum.getUserId());
					
					OnlineUser ou =  onlineUserService.findUserById(wcum.getUserId());
					ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
					Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
					//把用户中心的数据给他  --这些数据是IM的
					ou.setUserCenterId(iu.getId());
					ou.setPassword(iu.getPassword());
					ou.setTicket(t.getTicket());
					/**
					 *	 直接让登录了  返回状态值：200
					 */
					this.onlogin(req,res,t,ou,t.getTicket());
					
					//重定向到推荐首页
					 res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
					
				}else if(wcum.getUserId()==null){
					
					LOGGER.info("没有绑定了用户信息了"+wcum.getUserId());
					
					 //直接重定向到完善信息
		            res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?uId="+uId+"&type="+ThirdPartyType.WEIBO.getCode());
				}
			} catch (Exception  e) {
				e.printStackTrace();
				LOGGER.info("==============");
				//return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.DATA_IS_WRONG.getCode());
			}  
		} catch (WeiboException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}	
	}

	
	
	/**
	 * Description：APP ---> 微博回调接口  -- 回调了接口后需要请求  
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="appEvokeWeiBoRedirect")
	@ResponseBody
	public ResponseObject appEvokeWeiBoRedirect(HttpServletRequest req,
			HttpServletResponse res){
		
		String code = req.getParameter("code");
		LOGGER.info("微博用户授权登录成功code"+code);
		
		Map<String,String> mapRequest = new HashMap<String,String>();
		
		Oauth oauth = new Oauth();
		try {
			/**
			 * 通过code获取认证 微博唯一票据
			 */
			AccessToken at = oauth.getAccessTokenByCode(code);
			LOGGER.info("微博唯一票据--------》认证AccessToken成功:"+at.getAccessToken());
			LOGGER.info("微博用户唯一uid--------:"+at.getUid());
			/**
			 * 将用户信息保存到数据库中。
			 */
			//封装实体bean
			WeiboClientUserMapping wbuser = new WeiboClientUserMapping();
			try {
				/**
				 * 根据票据和用户id得到用户信息
				 */
				Users um = new Users();
				um.client.setToken(at.getAccessToken());
				//User user = um.showUserById(at.getUid());
				//User user = new User(job);
				
				/**
				 * 其实如果存在的话可以做更新操作了啊
				 */
				WeiboClientUserMapping wcum = threePartiesLoginService.selectWeiboClientUserMappingByUid(at.getUid());
				LOGGER.info("是否存在此微博号--------:"+wcum);
				if(wcum==null){
					JSONObject job = um.client.get(WeiboConfig.getValue("baseURL") + "users/show.json",
					        new PostParameter[] {new PostParameter("uid", at.getUid())}).asJSONObject();
					
					wbuser = new WeiboClientUserMapping(job);
					wbuser.setId(UUID.randomUUID().toString().replace("-", ""));
					//用户昵称
					String screenName =wbuser.getScreenName();
					screenName= SLEmojiFilter.filterEmoji(screenName);
					wbuser.setScreenName(screenName);
					//友好显示名称
					String name =wbuser.getName();
					name= SLEmojiFilter.filterEmoji(name);
					wbuser.setName(name);
					threePartiesLoginService.saveWeiboClientUserMapping(wbuser);
					
					mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
					mapRequest.put("openId",at.getUid()+"");
					
					return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.UNBOUNDED.getCode());
				}else if(wcum.getUserId()!=null){ //绑定了用户信息了
					
					LOGGER.info("绑定了用户信息了-wcum.getUserId()-------:"+wcum.getUserId());
					
					OnlineUser ou =  onlineUserService.findUserById(wcum.getUserId());
					ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
					Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
					//把用户中心的数据给他  --这些数据是IM的
					ou.setUserCenterId(iu.getId());
					ou.setPassword(iu.getPassword());
					ou.setTicket(t.getTicket());
					/**
					 *	 直接让登录了  返回状态值：200
					 */
					this.onlogin(req,res,t,ou,t.getTicket());
					return ResponseObject.newSuccessResponseObject(ou,UserUnitedStateType.BINDING.getCode());
				}else if(wcum.getUserId()==null){
					
					LOGGER.info("没有绑定了用户信息了"+wcum.getUserId());
					
					mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
					mapRequest.put("openId",at.getUid()+"");
					
					return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.UNBOUNDED.getCode());
				}
			} catch (Exception  e) {
				e.printStackTrace();
				LOGGER.info("==============");
				mapRequest.put("code",UserUnitedStateType.DATA_IS_WRONG.getCode()+"");
				mapRequest.put("openId",at.getUid()+"");
				return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.DATA_IS_WRONG.getCode());
			}  
			return ResponseObject.newSuccessResponseObject("");
		} catch (WeiboException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}	
		//response.sendRedirect(authorize("code",state,"mobile"));
	}
	
	
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket) throws SQLException{
		
		LOGGER.info("用户普通登录----》ticket"+ticket);
		/**
		 * 存在两个票，两个票都可以得到用户信息。
		 * 然后根据用户信息得到新的票和这个旧的票进行比较就ok了
		 */
		String appUniqueId = req.getParameter("appUniqueId");
		
		/*
		 * 这个地方是可以了。目前都支持吧
		 */
		
		if(StringUtils.isNotBlank(appUniqueId)){   //表示是app登录
			//设置登录标识
			onlineUserService.updateAppleTourisrecord(appUniqueId,1);
			cacheService.set(ticket, user,TokenExpires.TenDay.getExpires());
			cacheService.set(user.getId(),ticket,TokenExpires.TenDay.getExpires());
			//Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
			String model = req.getParameter("model");
			if(StringUtils.isNotBlank(model) && user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),model,TokenExpires.TenDay.getExpires());
			}else if(user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),"其他设备",TokenExpires.TenDay.getExpires());
			}
		}else{
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
				 *  * 如果存在那么就注销原来的。或者把原来的session搞成一个表示，不是取用户信息的。
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
			req.getSession().setMaxInactiveInterval(86400);//设置session失效时间
			req.getSession().setAttribute("_user_", user);
			/**
			 * 这是cookie 
			 */
			UCCookieUtil.writeTokenCookie(res, token);
		}
	}
	
	

	/**
	 * Description：唤起微博第三方登录授权页面   --获取code
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws WeiboException 
	 * @throws IOException 
	 */
	@RequestMapping(value="evokeWeiBoOuth")
	public void evokeWeiBoOuth(HttpServletRequest req,
			HttpServletResponse response) throws IOException, WeiboException{
		/**
		 * state 用户防止其他人乱调用
		 * mobile 手机浏览器调用
		 */
		String state = UUID.randomUUID().toString().replace("-", "");
	    ((HttpServletRequest)req).getSession().setAttribute("weibo_connect_state", state);
	    
		response.sendRedirect(authorize("code",state,"mobile"));
	}
	
	/**
	 * Description：微博接口
	 * @param response_type 响应类型 默认code
	 * @param state
	 * @param display  default 默认的授权页面，适用于web浏览器。
	 *                 mobile  移动终端的授权页面，适用于支持html5的手机,使用此版授权页请用 https://open.weibo.cn/oauth2/authorize 授权接口
	 *				   client 客户端版本授权页面，适用于PC桌面应用
	 *                 wap 	wap版授权页面，适用于非智能手机。
	 * 				   apponweibo 默认的站内应用授权页，授权后不返回access_token，只刷新站内应用父框架。	
	 * 
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	  public String authorize(String response_type, String state,String display)  throws WeiboException {
		  return weiboMobileAuthorizeURL.trim() + 
				  "?client_id=" + WeiboConfig.getValue("client_ID").trim() + 
				  "&redirect_uri=" + WeiboConfig.getValue("redirect_URI").trim() +
				  "&response_type=" + response_type + 
				  "&state=" + state+
				  "&display="+display;
	  }
}
