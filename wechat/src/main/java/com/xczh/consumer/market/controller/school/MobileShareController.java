package com.xczh.consumer.market.controller.school;

import java.util.Map;

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

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.vo.LecturVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;

/**
 * 热门搜索控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>默认关键字与热门搜索
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/share")
public class MobileShareController {

	@Autowired
	private OnlineCourseService onlineCourseService;
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	@Autowired
	private OnlineUserMapper onlineUserMapper;
	@Autowired
	private AppBrowserService appBrowserService;
	@Autowired
	private UserCenterAPI userCenterAPI;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileShareController.class);

	/**
	 *	app---> 课程、主播分享
	 */
	@RequestMapping("courseShare")
	@ResponseBody
	public ResponseObject courseShare(@RequestParam(value="shareId")String shareId,@RequestParam(value="shareType")Integer shareType)throws Exception{
		try {
			if(shareType==1){ // 课程分享 
				CourseLecturVo courseLectur = onlineCourseService.courseShare(Integer.parseInt(shareId));
				if(courseLectur.getDescription()!=null){
					String description = courseLectur.getDescription();
					description = com.xczh.consumer.market.utils.StringUtils.delHTMLTag(description);
					courseLectur.setDescription(description);
				}
				courseLectur.setLink(returnOpenidUri+"/wx_share.html?shareType=1&shareId="+Integer.parseInt(shareId));
				return ResponseObject.newSuccessResponseObject(courseLectur);
			}else {			 //  主播分享
				LecturVo lectur = onlineCourseService.lectureShare(shareId);
				if(lectur.getDescription()!=null){
					String description = lectur.getDescription();
					description = com.xczh.consumer.market.utils.StringUtils.delHTMLTag(description);
					lectur.setDescription(description);
				}
				lectur.setLink(returnOpenidUri+"/wx_share.html?shareType=2&shareId="+shareId);
				return ResponseObject.newSuccessResponseObject(lectur);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	/**
	 * Description：用户点击my_share.html页面后，进入到这个方法，
	 *   根据用户点击的不同浏览器来进行重定向
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("shareBrowserDifferentiation")
	public void shareBrowserDifferentiation(HttpServletRequest req,
			HttpServletResponse res)throws Exception{
		/**
		 * 这里有个问题就是。如果去分享页面的话
		 */
		String shareId = req.getParameter("shareId");  //分享的id
		String shareType = req.getParameter("shareType");  //分享类型
		String wxOrbrower = req.getParameter("wxOrbrower");  //来自哪里的浏览器
		
		LOGGER.info("shareId:"+shareId+",shareType:"+shareType);
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
		if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
			String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/xczh/share/viewUser?shareId="+shareId+"&shareType="+shareType+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
			res.sendRedirect(strLinkHome);
		}else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
			res.sendRedirect(returnOpenidUri +"/xczh/share/viewUser?shareId="+shareId+"&wxOrbrower=brower"+"&shareType="+shareType);//
		}
	}
	
	/**
	 * 
	 * Description：真正的分享后，响应给用户的页面
	 * @param req
	 * @param res
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("viewUser")
	public void h5ShareGetWxUserInfo(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		LOGGER.info("WX return code:" + req.getParameter("code"));
		LOGGER.info("shareId:" + req.getParameter("shareId"));
		LOGGER.info("shareType:" + req.getParameter("shareType"));
		LOGGER.info("wxOrbrower:" + req.getParameter("wxOrbrower"));
		try {
			String shareId = req.getParameter("shareId");
			String shareType = req.getParameter("shareType");
			String code = req.getParameter("code");
			String wxOrbrower = req.getParameter("wxOrbrower");
			
			OnlineUser ou =null;
			String openid ="";
			
			if(!StringUtils.isNotBlank(wxOrbrower)){ //微信浏览器
				
				WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
				openid = wxw.getOpenid();
				/*
				 * 判断此微信用户是否已经存在与我们的账户系统中不
				 */
				if(StringUtils.isNotBlank(wxw.getClient_id())){ //说明这个用户已经绑定过了
					/**
					 * 判断这个用户session是否有效了，如果有效就不用登录了
					 */
					ou = onlineUserMapper.findUserById(wxw.getClient_id());
					
					OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
					if(user == null){ //直接跳转到分享页面
						//这里不用判断用户有没有登录了。没哟登录帮他登录
					    ItcastUser iu = userCenterAPI.getUser(ou.getLoginName());
						Token t = userCenterAPI.loginThirdPart(ou.getLoginName(),iu.getPassword(), TokenExpires.TenDay);
						
						ou.setTicket(t.getTicket());
						onlogin(req,res,t,ou,t.getTicket());
					}	
				}
			}else{
				ou =  appBrowserService.getOnlineUserByReq(req);
			}
			
			String url1  = "/bxg/page/index/"+ openid + "/" + code;
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		
			if("1".equals(shareType)){ //课程分享啦
				
				//判断这个课程类型啦
				
				
				
				
				
			}else if("2".equals(shareType)){ //主播分享
				
				res.sendRedirect(returnOpenidUri + "/xcview/html/live_personal.html?userLecturerId="+shareId);
			}
			
//			Integer type = onlineCourseService.getIsCouseType(Integer.parseInt(courseId));
//			Map<String,Object> mapCourseInfo = onlineCourseService.shareLink(Integer.parseInt(courseId), type);
//			//这样直接跳转的话，怎样跳转呢，直接到直播页面啊，还是直接到
//			if(type == 1){ //直播或者预约详情页           
//				//1.直播中，2预告，3直播结束
//				if(null != mapCourseInfo.get("lineState") && "2".equals(mapCourseInfo.get("lineState").toString())){  //预告
//					url = "/xcviews/html/foreshow.html?course_id="+Integer.parseInt(courseId)+"&openId="+openid;
//				}else if(null != mapCourseInfo.get("lineState")){  //直播获取直播结束的
//					url = "/bxg/xcpage/courseDetails?courseId="+Integer.parseInt(courseId)+"&openId="+openid;
//				}
//			}else{ //视频音频详情页
//				//haiyao   multimediaType
//				Integer multimediaType = 1;
//				if(null != mapCourseInfo.get("multimediaType") && "2".equals(mapCourseInfo.get("multimediaType").toString())){
//					multimediaType = 2;
//				}
//				url = "/xcviews/html/particulars.html?courseId="+Integer.parseInt(courseId)+"&openId="+openid+"&multimedia_type="+multimediaType;
//			}
//			LOGGER.info("}}}}}}}}}}}}}}}}}} url："+url);
//			res.sendRedirect(returnOpenidUri + url);
//				
				
			
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
