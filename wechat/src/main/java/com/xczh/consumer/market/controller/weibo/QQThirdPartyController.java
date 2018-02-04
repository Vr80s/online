package com.xczh.consumer.market.controller.weibo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.wechat.course.model.QQClientUserMapping;
import com.xczhihui.wechat.course.service.IThreePartiesLoginService;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/user")
public class QQThirdPartyController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(QQThirdPartyController.class);
	
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private IThreePartiesLoginService threePartiesLoginService;
	
	
	/**
	 * Description：QQ回调接口  -- 回调了接口后需要请求  
	 * @param req
	 * @param userId
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="evokeQQRedirect")
	public ResponseObject evokeWeiBoRedirect(HttpServletRequest request){
		
		try {
			
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			
		    String accessToken   = null,openID        = null;
	        long tokenExpireIn = 0L;
			if (accessTokenObj.getAccessToken().equals("")) {
				
				LOGGER.info("获取accessToken信息有误：");
				return ResponseObject.newErrorResponseObject("获取QQ：accessToken 有误");
			}else{
				 accessToken = accessTokenObj.getAccessToken();
	             tokenExpireIn = accessTokenObj.getExpireIn();
			
	             // 利用获取到的accessToken 去获取当前用的openid -------- start
	             OpenID openIDObj =  new OpenID(accessToken);
	             openID = openIDObj.getUserOpenID();
	             
	             
	             
	             QQClientUserMapping qqUser =  threePartiesLoginService.selectQQClientUserMappingByOpenId(openID);
	             
	             if(qqUser==null){   //保存qq用户
	            	 
	            	  // 利用获取到的accessToken 去获取当前用户的openid --------- end
		             UserInfo qzoneUserInfo = new UserInfo(accessToken, openID); 		
		             UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();   
		             
		             QQClientUserMapping qq = new QQClientUserMapping();
		             
		             qq.setOpenId(openID);
		             qq.setNickname(userInfoBean.getNickname());
		             qq.setGender(userInfoBean.getGender());
		             qq.setLevel(userInfoBean.getLevel());
		             qq.setVip(userInfoBean.isVip());
		             qq.setIsYellowYearVip(userInfoBean.isYellowYearVip());
		             qq.setFigureurl(userInfoBean.getAvatar().getAvatarURL30());
		             qq.setFigureurl1(userInfoBean.getAvatar().getAvatarURL50());
		             qq.setFigureurl2(userInfoBean.getAvatar().getAvatarURL100());
	             }
	             
	           
	             
	             
	             
	             
	             
	             
			
			}
		
		
		} catch (QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		
		return null;
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
	public void getUserInfo(HttpServletRequest request,
			HttpServletResponse response){
		try {
			
		 /**
		  * client端的状态值。
		  * 用于第三方应用防止CSRF攻击(跨站请求伪造)	
		  */
		 String state = RandomStatusGenerator.getUniqueState();
		 
	     ((HttpServletRequest)request).getSession().setAttribute("qq_connect_state", state);
	     
	     String scope = QQConnectConfig.getValue("scope");
	     /**
	      * 请求转发	
	      */
	     String redirectUrl =   getCustomAuthorizeURL("code",state,scope,"mobile");	
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

	
    
}
