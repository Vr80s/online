package com.xczh.consumer.market.controller.weibo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczhihui.bxg.online.common.enums.ThirdPartyType;
import com.xczhihui.bxg.online.common.enums.UserUnitedStateType;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.wechat.course.model.QQClientUserMapping;
import com.xczhihui.wechat.course.service.IThreePartiesLoginService;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/qq")
public class QQThirdPartyController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(QQThirdPartyController.class);
	
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private IThreePartiesLoginService threePartiesLoginService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	
	 protected HttpClient client = new HttpClient();
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	/**
	 * Description：h5 -- > 回调接口  -- 回调了接口后需要请求  。本期先不做
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="evokeQQRedirect")
	public void evokeQQRedirect(HttpServletRequest request,
			HttpServletResponse res){
		try {
			LOGGER.info("进入	qq回调函数   ============：qq_connect_state");

			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
		    String accessToken   = null,openID        = null;
	        long tokenExpireIn = 0L;
	        Map<String,String> mapRequest = new HashMap<String,String>();
	        LOGGER.info("accessTokenObj"+accessTokenObj.toString());
			if (accessTokenObj.getAccessToken().equals("")) {
				
				LOGGER.info("获取accessToken信息有误：");
				
				res.sendRedirect(returnOpenidUri + "/xcview/html/enter.html");
			}else{
				 LOGGER.info("获取到票据  accessToken   ============："+accessToken);
				 accessToken = accessTokenObj.getAccessToken();
	             tokenExpireIn = accessTokenObj.getExpireIn();
	             // 利用获取到的accessToken 去获取当前用的openid -------- start
	             OpenID openIDObj =  new OpenID(accessToken);
	             openID = openIDObj.getUserOpenID();
	             LOGGER.info("qq用户openid   ============"+openID);
	             
	             /**
	              * 获取unionId 判断此qq信息是否已经存在账户中了
	              */
	             String unionId = this.getQQUnionIdByOpenIdAndAccessToken(accessToken);
	             System.out.println("JSONObject.tostring"+unionId);
	             
	             QQClientUserMapping qqUser =  threePartiesLoginService.selectQQClientUserMappingByUnionId(unionId);
	            
	             if(qqUser==null){   //保存qq用户
	            	 LOGGER.info("第一次存入qq用户信息");
	            	 // 利用获取到的accessToken 去获取当前用户的openid --------- end
		             UserInfo qzoneUserInfo = new UserInfo(accessToken, openID); 		
		             UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();   
		             QQClientUserMapping qq = new QQClientUserMapping();
		             
		             qq.setId(UUID.randomUUID().toString().replace("-", ""));
		             qq.setOpenId(openID);
		             qq.setNickname(userInfoBean.getNickname());
		             qq.setGender(userInfoBean.getGender());
		             qq.setLevel(userInfoBean.getLevel());
		             qq.setVip(userInfoBean.isVip());
		             qq.setYellowYearVip(userInfoBean.isYellowYearVip());
		             qq.setFigureurl(userInfoBean.getAvatar().getAvatarURL30());
		             qq.setFigureurl1(userInfoBean.getAvatar().getAvatarURL50());
		             qq.setFigureurl2(userInfoBean.getAvatar().getAvatarURL100());
		             qq.setUnionId(unionId);
		             
		             threePartiesLoginService.saveQQClientUserMapping(qq);
		             
		             //直接重定向到完善信息
		             res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openID+"&type="+ThirdPartyType.QQ.getCode());
		             
				}else if(StringUtils.isNotBlank(qqUser.getUserId())){ //绑定了用户信息
					
				    LOGGER.info("熊猫中医用户id   ============已绑定用户信息"+qqUser.getUserId());
					
					OnlineUser ou =  onlineUserService.findUserById(qqUser.getUserId());
					ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
					
					Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
					//把用户中心的数据给他  --这些数据是IM的
					ou.setUserCenterId(iu.getId());
					ou.setPassword(iu.getPassword());
					ou.setTicket(t.getTicket());
					/**
					 *	 直接让登录了  返回状态值：200
					 */
					this.onlogin(request,res,t,ou,t.getTicket());
					
					//重定向到推荐首页
					 res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
					//return ResponseObject.newSuccessResponseObject(ou,UserUnitedStateType.BINDING.getCode());
				}else if(StringUtils.isNotBlank(qqUser.getUserId())){
					
				    LOGGER.info("熊猫中医用户id 没有绑定用户信息"+qqUser.getUserId());
					
					mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
					mapRequest.put("openId",openID);
					 //直接重定向到完善信息
		            res.sendRedirect(returnOpenidUri + "/xcview/html/evpi.html?openId="+openID+"&type="+ThirdPartyType.QQ.getCode());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	/**
	 * Description：app -- QQ回调接口  -- 回调了接口后需要请求  
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="appEvokeQQRedirect")
	@ResponseBody
	public ResponseObject appEvokeQQRedirect(HttpServletRequest request,
			HttpServletResponse res,
			@RequestParam("accessToken")String accessToken,
			@RequestParam("openId")String openId,
			@RequestParam("model")String model){
		try {
			LOGGER.info("进入	qq回调函数   ============：qq_connect_state");
			
			String userId = request.getParameter("userId");
		    if(StringUtils.isNotBlank(userId)){
		    	OnlineUser ou =  onlineUserService.findUserById(userId);
		    	if(ou == null){
		    		return	ResponseObject.newErrorResponseObject("获取用户信息有误");
		    	}
		    }
		    
		    LOGGER.info("绑定呢还是解除绑定呢： "+ userId);
		    
		    
	        long tokenExpireIn = 0L;
	        Map<String,String> mapRequest = new HashMap<String,String>();
	        mapRequest.put("type",ThirdPartyType.QQ.getCode()+"");
	        
			if (accessToken ==null ) {
				LOGGER.info("获取accessToken信息有误：");
				return ResponseObject.newErrorResponseObject("获取QQ：accessToken 有误");
			}else{
				
 LOGGER.info("accessToken   ============"+accessToken);
 LOGGER.info("qq用户openid   ============"+openId);
	             /**
	              * 获取qq unionId
	              * 
	              */
	             String unionId = this.getQQUnionIdByOpenIdAndAccessToken(accessToken);
	             
 LOGGER.info("qq用户unionId   ============"+unionId);
	             
 				QQClientUserMapping qqUser =  threePartiesLoginService.selectQQClientUserMappingByUnionId(unionId);
	            	             
	             if(qqUser==null){   //保存qq用户
	            	 LOGGER.info("第一次存入qq用户信息");
	            	 
	            	 // 利用获取到的accessToken 去获取当前用户的openid --------- end
		             //UserInfo qzoneUserInfo = new UserInfo(accessToken, openId); 
		             
		            //UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();   
		             
		             UserInfoBean userInfoBean = this.getUserInfo(accessToken,openId);
		             
LOGGER.info("userInfoBean   ============"+userInfoBean.toString());
		             
		             QQClientUserMapping qq = new QQClientUserMapping();
		             qq.setId(UUID.randomUUID().toString().replace("-", ""));
		             qq.setOpenId(openId);
		             qq.setNickname(userInfoBean.getNickname());
		             qq.setGender(userInfoBean.getGender());
		             qq.setLevel(userInfoBean.getLevel());
		             qq.setVip(userInfoBean.isVip());
		             qq.setYellowYearVip(userInfoBean.isYellowYearVip());
		             qq.setFigureurl(userInfoBean.getAvatar().getAvatarURL30());
		             qq.setFigureurl1(userInfoBean.getAvatar().getAvatarURL50());
		             qq.setFigureurl2(userInfoBean.getAvatar().getAvatarURL100());
		             qq.setUnionId(unionId);
		           
		            //用户id不等于null时，就判定用户第三方登录是通过手机号来绑定 第三方登录信息的
		             if(userId!=null){  // 绑定成功
		            	 qq.setUserId(userId);	
		            	 mapRequest.put("code",UserUnitedStateType.MOBILE_BINDING.getCode()+"");
		 			 }else{
		 				 mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
		 			 }
		             mapRequest.put("unionId",unionId);
		             
		             threePartiesLoginService.saveQQClientUserMapping(qq);
					
					return ResponseObject.newSuccessResponseObject(mapRequest,Integer.parseInt(mapRequest.get("code").toString()));
				}else if(StringUtils.isNotBlank(qqUser.getUserId())){ //绑定了用户信息
					
					LOGGER.info("熊猫中医用户id   ============已绑定用户信息"+qqUser.getUserId());  
					
					if(userId!=null){  // 这里说明人家这个已经绑定过其他信息了。
						mapRequest.put("code",UserUnitedStateType.MOBILE_UNBOUNDED.getCode()+"");	
						return ResponseObject.newSuccessResponseObject(mapRequest,UserUnitedStateType.MOBILE_UNBOUNDED.getCode());
		 			}
					
					OnlineUser ou =  onlineUserService.findUserById(qqUser.getUserId());
					ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
					
					Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
					//把用户中心的数据给他  --这些数据是IM的
					ou.setUserCenterId(iu.getId());
					ou.setPassword(iu.getPassword());
					ou.setTicket(t.getTicket());
					/**
					 *	 直接让登录了  返回状态值：200
					 */
					this.onlogin(request,res,t,ou,t.getTicket());
					return ResponseObject.newSuccessResponseObject(ou,UserUnitedStateType.BINDING.getCode());
				}else if(!StringUtils.isNotBlank(qqUser.getUserId())){
					
				    LOGGER.info("熊猫中医用户id 没有绑定用户信息"+qqUser.getUserId());
					
				    if(userId!=null){  // 绑定成功
		            	 mapRequest.put("code",UserUnitedStateType.MOBILE_BINDING.getCode()+"");
		            	 /**
		            	  * 更改qq信息	--》增加用户id
		            	  */
		            	 qqUser.setUserId(userId);
		            	 threePartiesLoginService.updateQQInfoAddUserId(qqUser);
		            	 
		 			}else{
		 				 mapRequest.put("code",UserUnitedStateType.UNBOUNDED.getCode()+"");
		 			}
					mapRequest.put("unionId",unionId);
					
					return ResponseObject.newSuccessResponseObject(mapRequest,Integer.parseInt(mapRequest.get("code").toString()));
				}
				return ResponseObject.newSuccessResponseObject("");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 
	 * Description：获取ios的配置信息啦
	 * @param openid
	 * @return
	 * @throws QQConnectException
	 * @return UserInfoBean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
    private UserInfoBean getUserInfo(String token,String openid)   throws QQConnectException  {
//    	this.client.setToken(token);
//    	this.client.setOpenID(openid);
    	//appKey：1106654789和appSecret：aTCTEkW72WqUeMqB
    	
		return new UserInfoBean(this.client.get(QQConnectConfig.getValue("getUserInfoURL"), new PostParameter[] { 
			new PostParameter("openid", openid), 
			new PostParameter("oauth_consumer_key","1106654789"), 
			new PostParameter("access_token", token), new PostParameter("format", "json") }).asJSONObject());
	}
	
	
	/**
	 * Description：h5 --》 唤起qq第三方登录授权页面
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="evokeQQOuth")
	public void evokeQQOuth(HttpServletRequest request,
			HttpServletResponse response){
		try {
			
		 LOGGER.info("进入唤起qq第三方登录方法============");
		 /**
		  * client端的状态值。
		  * 用于第三方应用防止CSRF攻击(跨站请求伪造)	
		  */
		 String state = RandomStatusGenerator.getUniqueState();
	     ((HttpServletRequest)request).getSession().setAttribute("qq_connect_state", state);
	     String scope = QQConnectConfig.getValue("scope");
	     String  state1 = (String)((HttpServletRequest)request).getSession().getAttribute("qq_connect_state");
		 System.out.println("state1"+state1);
	     /**
	      * 请求转发	
	      */
	     String redirectUrl =   getCustomAuthorizeURL("code",state,scope,"mobile");	
	     LOGGER.info("打印的从定向地址：+"+redirectUrl);
	     response.sendRedirect(redirectUrl);
		  
		  
		} catch (IOException | QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   /**
    * 
    * Description：获取第一步  得到code
    * @param response_type  默认值code
    * @param state		state
    * @param scope
    * @param display
    * @return
    * @throws QQConnectException
    * @return String
    * @author name：yangxuan <br>email: 15936216273@163.com
    *
    */
   public String getCustomAuthorizeURL(String response_type, String state, String scope,String display)throws QQConnectException {
        
	   
	   return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + 
			   "&redirect_uri=" + QQConnectConfig.getValue("redirect_URI").trim() + 
			   "&response_type=" + response_type + "&state=" + state + "&scope=" + scope;
   }
   
   
  public String getQQUnionIdByOpenIdAndAccessToken(String access_token)throws QQConnectException {
        
	     String unionid ="";
	     String jsonp = client.get(QQConnectConfig.getValue("getOpenIDURL").trim() + "?access_token=" + access_token +"&unionid=1").asString();
	     //String jsonp = this.client.get(QQConnectConfig.getValue("getOpenIDURL"), new PostParameter[] { new PostParameter("access_token", accessToken) }).asString();
	     Matcher m = Pattern.compile("\"unionid\"\\s*:\\s*\"(\\w+)\"").matcher(jsonp);
	        
	     if (m.find()) {
	    	 unionid = m.group(1);
	     } else {
	         throw new QQConnectException("server error!");
	     }
	     return unionid;
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
    
}
