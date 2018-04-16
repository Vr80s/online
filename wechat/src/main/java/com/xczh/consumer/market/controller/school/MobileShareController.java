package com.xczh.consumer.market.controller.school;

import java.net.URLEncoder;
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
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ClientUserUtil;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.ThridFalg;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.vo.LecturVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczhihui.wechat.course.util.XzStringUtils;

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
	@Autowired
	private ICourseService courseServiceImpl;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileShareController.class);

	/**
	 *	app---> 课程、主播分享
	 */
	@RequestMapping("courseShare")
	@ResponseBody
	public ResponseObject courseShare(
			@RequestParam(value="shareId")String shareId,
			@RequestParam(value="shareType")Integer shareType)throws Exception{
		try {
			if(shareType==1){ // 课程分享 
				CourseLecturVo courseLectur = onlineCourseService.courseShare(Integer.parseInt(shareId));
				
				if(courseLectur==null){
					return ResponseObject.newErrorResponseObject("课程信息有误");
				}
				courseLectur.setGradeName("中医好课程:"+courseLectur.getGradeName());
				if(courseLectur.getDescription()!=null){
					String description = courseLectur.getDescription();
					description = XzStringUtils.delHTMLTag(description);
					courseLectur.setDescription(description);
				}
				courseLectur.setLink(returnOpenidUri+"/wx_share.html?shareType=1&shareId="+Integer.parseInt(shareId));
				return ResponseObject.newSuccessResponseObject(courseLectur);
			}else {			 //  主播分享
				LecturVo lectur = onlineCourseService.lectureShare(shareId);
				if(lectur==null){
					return ResponseObject.newErrorResponseObject("主播信息有误");
				}
				
				/*
				 * 课程名增加一个中医好课程  
				 */
				lectur.setName("中医好主播:"+lectur.getName());
				
				if(lectur.getDescription()!=null){
					String description = lectur.getDescription();
					description = XzStringUtils.delHTMLTag(description);
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
		String shareIdAndType = shareId+"_"+shareType;
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
		if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
			String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/xczh/share/viewUser?shareIdAndType="+shareIdAndType+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
			res.sendRedirect(strLinkHome);
		}else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
			res.sendRedirect(returnOpenidUri +"/xczh/share/viewUser?shareId="+shareId+"&wxOrbrower=brower"+"&shareType="+shareType);//
		}
	}
	public static void main(String[] args) {
		System.out.println("============");
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
	public void viewUser(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		LOGGER.info("WX return code:" + req.getParameter("code"));
		
		try {
			String code = req.getParameter("code");
			String shareId = req.getParameter("shareId");
			String shareType = req.getParameter("shareType");
			String wxOrbrower = req.getParameter("wxOrbrower");
			
			String shareIdAndType = req.getParameter("shareIdAndType");
			
			if(StringUtils.isNotBlank(shareIdAndType)){
				String [] idAndType =shareIdAndType.split("_");
				shareId = idAndType[0];
				shareType = idAndType[1];
			}
			LOGGER.info("shareId:" +shareId+"shareType:" +shareType+"wxOrbrower:" +wxOrbrower);
			OnlineUser ou =null;
			if(!StringUtils.isNotBlank(wxOrbrower)){ //微信浏览器
				WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
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
				/**
				 * 写入这个cookie
				 */
				ThridFalg tf = new ThridFalg(); 
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName(wxw.getNickname());
				tf.setHeadImg(wxw.getHeadimgurl());
				UCCookieUtil.writeThirdPartyCookie(res,tf);
			}else
			{
				ou =  appBrowserService.getOnlineUserByReq(req);
			}
			
			/**
			 * 如果这个用户信息已经保存进去了，那么就直接登录就ok
			 */
			ConfigUtil cfg = new ConfigUtil(req.getSession());
			String returnOpenidUri = cfg.getConfig("returnOpenidUri");
			if("1".equals(shareType)){ //课程分享啦
				
				LOGGER.info("shareType:"+shareType);
				LOGGER.info("shareId:+"+shareId);
				
				Integer courseId = Integer.parseInt(shareId);
				com.xczhihui.wechat.course.vo.CourseLecturVo  cv=null;
				
				//判断这个课程类型啦
				if(ou!=null){  //说明已经登录了
					cv= courseServiceImpl.selectUserCurrentCourseStatus(courseId,ou.getId());
				}else{
					cv= courseServiceImpl.selectCurrentCourseStatus(courseId);
				}	
				//如果课程id没有找到，就去首页
				if(cv==null){
					res.sendRedirect(returnOpenidUri + "/xcview/html/home_page.html");
					return;
				}
				
				LOGGER.info("cv.getWatchState():+"+cv.getWatchState());
				LOGGER.info("cv.getType()=:+"+cv.getType());
				LOGGER.info("cv.getCollection()=:+"+cv.getCollection());
				
				if(cv.getWatchState() == 0){
					if(cv.getType()==1 || cv.getType()==2){
						//视频音频购买
						res.sendRedirect(returnOpenidUri + "/xcview/html/school_audio.html?shareBack=1&course_id="+shareId);
					}else if(cv.getType()==3){
						//直播购买
						res.sendRedirect(returnOpenidUri + "/xcview/html/school_play.html?shareBack=1&course_id="+shareId);
					}else{
						//线下课购买
						res.sendRedirect(returnOpenidUri + "/xcview/html/school_class.html?shareBack=1&course_id="+shareId);
					}	
				}else if(cv.getWatchState()==1 || cv.getWatchState() == 2 || cv.getWatchState()==3 ){
					if(cv.getType()==1||cv.getType()==2){
						if(cv.getCollection()){
							//专辑视频音频播放页
							res.sendRedirect(returnOpenidUri + "/xcview/html/live_select_album.html?shareBack=1&course_id="+shareId);
						}else{
							
							if("3".equals(shareType)) { //说明是单个专辑
								//单个视频音频播放
								res.sendRedirect(returnOpenidUri + "/xcview/html/live_audio.html?shareBack=1&my_study="+shareId);
							}else {
								//单个视频音频播放
								res.sendRedirect(returnOpenidUri + "/xcview/html/live_audio.html?shareBack=1&my_study="+shareId);
							}
						}
					}else if(cv.getType()==3){
						//播放页面
						res.sendRedirect(returnOpenidUri + "/xcview/html/live_play.html?shareBack=1&my_study="+shareId);
					}else{
						//线下课页面
						res.sendRedirect(returnOpenidUri + "/xcview/html/live_class.html?shareBack=1&my_study="+shareId);
					}
				}
			}else if("2".equals(shareType)){ //主播分享  -->设置下cookie
				res.sendRedirect(returnOpenidUri + "/xcview/html/live_personal.html?shareBack=1&userLecturerId="+shareId);
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

	/**
	 * 通过连接我们的二维码来获取用户信息啦，
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("xcCustomQrCode")
	public void xcCustomQrCode(HttpServletRequest req,
			HttpServletResponse res)throws Exception{
		
		/**
		 * 这里有个问题就是。如果去分享页面的话
		 */
		String searchUrl = req.getParameter("search_url");  //来自哪里的浏览器
		String wxOrbrower = req.getParameter("wxOrbrower");  //来自哪里的浏览器
		String realUrl = URLEncoder.encode(searchUrl, "UTF-8");
		
		LOGGER.info("啦啦啦，我是卖报的小画家：searchUrl:"+searchUrl+"==="+",wxOrbrower:"+wxOrbrower);
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
		if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
			String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/xczh/share/xcCustomQrCodeViewUser?realUrl="+realUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
			res.sendRedirect(strLinkHome);
		}else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
			res.sendRedirect(returnOpenidUri +"/xczh/share/xcCustomQrCodeViewUser?realUrl="+realUrl+"&wxOrbrower=brower");//
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
	@RequestMapping("xcCustomQrCodeViewUser")
	public void xcCustomQrCodeViewUser(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		LOGGER.info("WX return code:" + req.getParameter("code"));
		try {
			String code = req.getParameter("code");
			String realUrl = req.getParameter("realUrl");
			String wxOrbrower = req.getParameter("wxOrbrower");
			
			LOGGER.info("code:" +code+"realUrl:" +realUrl+"wxOrbrower:" +wxOrbrower);
			OnlineUser ou =null;
			
			if(!StringUtils.isNotBlank(wxOrbrower)){ //微信浏览器
				WxcpClientUserWxMapping wxw = ClientUserUtil.saveWxInfo(code,wxcpClientUserWxMappingService);
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
				/**
				 * 写入这个cookie
				 */
				ThridFalg tf = new ThridFalg(); 
				tf.setOpenId(wxw.getOpenid());
				tf.setUnionId(wxw.getUnionid());
				tf.setNickName(wxw.getNickname());
				tf.setHeadImg(wxw.getHeadimgurl());
				UCCookieUtil.writeThirdPartyCookie(res,tf);
			}else{
				ou =  appBrowserService.getOnlineUserByReq(req);
			}
			res.sendRedirect(returnOpenidUri +"/"+ realUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
