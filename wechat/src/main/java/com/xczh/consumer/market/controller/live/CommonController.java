package com.xczh.consumer.market.controller.live;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SmsUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

/**
 * 通用控制器
 * ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/bxg/common")
public class CommonController {

	@Autowired
	private OnlineCourseService onlineCourseService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private GiftService giftService;
	
	@Autowired
	private OnlineWebService onlineWebService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	
	@Value("${webdomain}")
	private String webdomain;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
	
	
	/**
	 * Description：用户主页    -- 讲师基本信息、是否已经关注了这个主播、关注该主播的粉丝数、主播关注的主播数、关注该主播的前六个人的头像
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userHomePage")
	@ResponseBody
	public ResponseObject userHomePage(HttpServletRequest req,
									   HttpServletResponse res, Map<String,String> params)throws Exception {
		//用户主页   -- 》接口
		//关注人数         用户头像
		//直播的课程     传递一个讲师id 就ok了... 得到讲师下的所有课程，得到讲师下的所有粉丝，得到讲师的
		
		String lecturerId = req.getParameter("lecturerId");
		
		Map<String,Object> mapAll = new HashMap<String,Object>();
		/**
		 * 得到讲师   主要是房间号，缩略图的信息啦
		 */
		Map<String,Object> lecturerInfo = onlineUserService.findUserRoomNumberById(lecturerId);
		/**
		 * 粉丝总数 
		 */
		Integer fansCount  = focusService.findMyFansCount(lecturerId);
		/**
		 * 关注总数 
		 */
		Integer focusCount  = focusService.findMyFocusCount(lecturerId);
		
		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
		mapAll.put("fansCount", fansCount);       		   //粉丝总数
		mapAll.put("focusCount", focusCount);   	  	   //关注总数
		
		//讲师的精彩简介  
		mapAll.put("videoId", "F89D83B02BCE744D9C33DC5901307461");  //
		//坐诊医馆
		MedicalHospitalVo   mh = new MedicalHospitalVo();
		mh.setDetailedAddress("北京市丰台区开阳路一号瀚海花园大厦一层底商 北京海淀区中关村南大街19号院");
		mh.setTel("010-68412758");
		mh.setVisitTime("周一到周五");
		mh.setCertificationType(1);
		//认证的主播 还是 医馆
		mapAll.put("hospital",mh);
		/**
		 * 关注讲师的粉丝  显示六个
		 */
		//List<FocusVo> listFans = focusService.findMyFans(lecturerId,0,6);
		/**
		 * 得到讲师下面的所有课程数  ---》如果是视频数的话客户会比较蒙
		 */
		//Integer courseAll = onlineCourseService.liveAndBunchAndAudioCount(lecturerId);
		/**
		 * 得到这个讲师的所有   礼物数
		 */
		//Integer giftAll = giftService.findByUserId(lecturerId);
		/**
         * 得到判断这个主播有没有正在直播的课程啦	
         */
		//Map<String,String> mapLiveState  =  onlineCourseService.teacherIsLive(lecturerId);
		
//		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
//		//mapAll.put("mapLiveState", mapLiveState); // 1 表示有直播  null表示没直播
//		mapAll.put("fansCount", fansCount);       //粉丝总数
//		mapAll.put("focusCount", focusCount);   	  // 关注总数
		//mapAll.put("giftAll", giftAll);           // 礼物数 
		//mapAll.put("courseAll", courseAll);       // 课程数 
		//mapAll.put("listFans", listFans);   	  // 前六个的粉丝数
		//主播最近一次直播
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
		if(null == lecturerId){  //讲师id
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
	    if(user==null){
	    	mapAll.put("isFours", 0); 
	    }else{
	    	/**
			 * 是否已经关注了这个主播：0 未关注  1已关注
			 */
			Integer isFours  = focusService.myIsFourslecturer(user.getId(), lecturerId);
			mapAll.put("isFours", isFours); 		  //是否关注       0 未关注  1已关注
	    }
	    return ResponseObject.newSuccessResponseObject(mapAll);
	}
	/**
	 * Description：用户主页    -- 课程列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userHomePageCourseList")
	@ResponseBody
	public ResponseObject userHomePageCourseList(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		
		String lecturerId = req.getParameter("lecturerId");
		String pageNumberS = req.getParameter("pageNumber");
		String pageSizeS = req.getParameter("pageSize");
		String type = req.getParameter("type"); 
		if(null == type || null == lecturerId || null == pageNumberS || null == pageSizeS){  //讲师id
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		//根据讲师id得到下面的所有课程啊，包括点播，包括直播....... 直播的在前面
		//后期音频增加上的话，还需要增加音频了
		int pageNumber =Integer.parseInt(pageNumberS);
		int pageSize = Integer.parseInt(pageSizeS);
		try {
			List<CourseLecturVo> list = onlineCourseService.liveAndBunchAndAudio(lecturerId,pageNumber,pageSize,type!=null ? Integer.parseInt(type):1);
			return ResponseObject.newSuccessResponseObject(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台数据异常");
		}
	}
	
	/**
	 * 判断这个用户是不是讲师
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("judgeUserIsTeacher")
	@ResponseBody
	public ResponseObject judgeUserIsTeacher(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		String userId = req.getParameter("userId");
		//是否是讲师：0,用户，1既是用户也是讲师  is_lecturer
	    Map<String,Object>  map =  onlineUserService.judgeUserIsTeacher(userId);
	    LOGGER.info("map.get(is_lecturer)"+map.get("is_lecturer"));
	    if(null !=map && "0".equals(map.get("is_lecturer").toString())){
	    	return ResponseObject.newErrorResponseObject(map.get("is_lecturer").toString());
	    }else{
	    	return ResponseObject.newSuccessResponseObject(map);
	    }
	}
	
	
	/**
	 * 判断该课程是否已经购买，付费认证
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("courseIsBuy")
	@ResponseBody
	public ResponseObject courseIsBuy(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
	    OnlineUser ou =  appBrowserService.getOnlineUserByReq(req, params);
	    if(ou==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		
		return onlineCourseService.courseIsBuy(ou,course_id);
	}
	/**
	 * 判断当前用户针对这个课程是否还需要密码确认  
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("courseIsPwd")
	@ResponseBody
	public ResponseObject courseIsConfirmPwd(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		
		//onlineCourseService.
		//course_id
		
		if(user.getId().equals(onlineCourseService.getlecturerIdByCourseId(course_id)) || onlineWebService.getLiveUserCourse(course_id,user.getId()).size()>0){
			return ResponseObject.newSuccessResponseObject("认证通过");
		}else{
			return ResponseObject.newErrorResponseObject("需要进行密码认证");
		}
		//return onlineCourseService.courseIsConfirmPwd(user,course_id);
	}
	
	/**
	 *  
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("courseStatus")
	@ResponseBody
	public ResponseObject courseStatus(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		return onlineCourseService.courseStatus(user,course_id);
	}
	
	
	/**
	 * 用户输入密码后比较密码是否一致
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("coursePwdConfirm")
	@ResponseBody
	public ResponseObject coursePwdConfirm(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		if(null == req.getParameter("course_pwd") && null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		String course_pwd =req.getParameter("course_pwd");
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		return onlineCourseService.saveCoursePwdAndConfirm(user,course_id,course_pwd);
	}
	/**
	 * 预约接口
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：liutao
	 */
	@RequestMapping("subscribe")
	@ResponseBody
	public ResponseObject subscribe(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		String mobile =req.getParameter("mobile");
		String course_id =req.getParameter("course_id");
	    if(course_id==null){
	    	return ResponseObject.newErrorResponseObject("缺少参数");
	    }
		//获取用户信息
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
	    int isSubscribeInfo =  onlineCourseService.selectSubscribeInfoIs(Integer.parseInt(course_id),user.getId());
	    if(isSubscribeInfo>0){
	    	return ResponseObject.newErrorResponseObject("你已经预约过了");
	    }
		CourseLecturVo courseVo =onlineCourseService.get(Integer.parseInt(course_id));
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时mm分");
		String start = sdf.format(courseVo.getStartTime());
		
		if(!StringUtils.isNotBlank(mobile)){
			mobile = user.getLoginName();
		}
		//发送短信
		SmsUtil.sendSmsSubscribe(mobile, courseVo.getGradeName(), start, null, true);
		return onlineCourseService.addSubscribeInfo(user,mobile,Integer.parseInt(course_id));
	}
	
	/**
	 * 判断这个用户是否已经预约
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userIsSubscribe")
	@ResponseBody
	public ResponseObject userIsSubscribe(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		return onlineCourseService.courseStatus(user,course_id);
	}
	
	/**
	 * Description：微吼签名认证得到微吼的视频播放权
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("getWeihouSign")
	@ResponseBody
	public ResponseObject getWeihouSign(HttpServletRequest req,
			HttpServletResponse res )throws Exception{
		Map<String,String> params=new HashMap<>();
		params.put("token",req.getParameter("token"));
		String roomNumber = req.getParameter("video");  //视频id
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		String gvhallId = user.getVhallId();
		LOGGER.info("微吼gvhallId:"+gvhallId);
		
		//JSONObject json = WeihouInterfacesListUtil.getUserinfo(gvhallId,"name,head,id");
		//String vh_app_key = "71a22e5b4a41483d41d96474511f58f3";
		
		String vhName = user.getName();
		String email = user.getLoginName();
		if(email!=null && email.indexOf("@")==-1){
			email+="@163.com";
		}
		Date d = new Date();
		String start_time = d.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		
		Map<String,String> map = new TreeMap<String,String>();
		map.put("app_key", WeihouInterfacesListUtil.APP_KEY);  //微吼key
		map.put("signedat", start_time); //时间戳，精确到秒  
		map.put("email", email);         //email 自己写的
		map.put("roomid", roomNumber);   //视频id
		map.put("account",user.getId());       //用户账号
		map.put("username",vhName);      //用户名
		map.put("sign", getSign(map));
		
		return ResponseObject.newSuccessResponseObject(map);
	}
	
	
	@RequestMapping("h5ShareAfter")
	@ResponseBody
	public ResponseObject h5ShareLink(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception {
		
		String courseId = req.getParameter("course_id");  //视频id
		if(courseId == null ){
			return ResponseObject.newErrorResponseObject("获取参数异常");
		}
		/*
		 * 需要判断这个课程是直播呢，还是公开课
		 *     因为他们的文案不在一个地方存
		 */
		try {
			Integer type = onlineCourseService.getIsCouseType(Integer.parseInt(courseId));
			LOGGER.info("type:"+type);
			CourseLecturVo courseLecturVo = onlineCourseService.h5ShareAfter(Integer.parseInt(courseId), type);
			if(type ==1){
				//礼物数：
				courseLecturVo.setGiftCount(giftService.findByUserId(courseLecturVo.getUserId()));
			}
			return ResponseObject.newSuccessResponseObject(courseLecturVo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}
	
	
	@RequestMapping("shareLink")
	@ResponseBody
	public ResponseObject shareLink(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception{
		String courseId = req.getParameter("courseId");  //视频id
		if(courseId == null ){
			return ResponseObject.newErrorResponseObject("获取参数异常");
		}
		/*
		 * 需要判断这个课程是直播呢，还是公开课, 因为他们的文案不在一个地方存
		 */
		try {
			Integer type = onlineCourseService.getIsCouseType(Integer.parseInt(courseId));
			LOGGER.info("type:"+type);
			Map<String,Object> mapCourseInfo = onlineCourseService.shareLink(Integer.parseInt(courseId), type);
			if(mapCourseInfo.get("description")!=null){
				String description = mapCourseInfo.get("description").toString();
				description = com.xczh.consumer.market.utils.StringUtils.delHTMLTag(description);
				mapCourseInfo.put("description", description);
			}else{
				mapCourseInfo.put("description", "");
			}
			//mapCourseInfo.put("link",returnOpenidUri+"/bxg/common/pcShareLink?courseId="+Integer.parseInt(courseId));
			//wx_share.html
			mapCourseInfo.put("link",returnOpenidUri+"/wx_share.html?courseId="+Integer.parseInt(courseId));
			return ResponseObject.newSuccessResponseObject(mapCourseInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}
	
	/**
	 * Description：pc端分享
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("pcShareLink")
	public void pcShareLink(HttpServletRequest req,HttpServletResponse res, Map<String,String> params)throws Exception{
		/*
		 * 难道这里就需要搞下吗。
		 */
		//判断这个用户是否已经存在了。
		/**
		 * 这里有个问题就是。如果去分享页面的话
		 */
		String courseId = req.getParameter("courseId");  //视频id
		
		System.out.println("========"+courseId);
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
		String wxOrbrower = req.getParameter("wxOrbrower");  //视频id
		System.out.println();
		if(StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)){
			String strLinkHome 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WxPayConst.gzh_appid+"&redirect_uri="+returnOpenidUri+"/bxg/wxpay/h5ShareGetWxUserInfo?courseId="+courseId+"&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect".replace("appid=APPID", "appid="+ WxPayConst.gzh_appid);
			res.sendRedirect(strLinkHome);
		}else if(StringUtils.isNotBlank(wxOrbrower) && "brower".equals(wxOrbrower)){
			res.sendRedirect(returnOpenidUri +"/bxg/wxpay/h5ShareGetWxUserInfo?courseId="+courseId+"&wxOrbrower=brower");//
		}
		System.out.println("{}{}{}{}{}="+courseId);
//		if(courseId == null ){
//			LOGGER.info("参数异常啦");
//		}
//        LOGGER.info("===========================================");
//		String url  ="/xcviews/html/share.html?course_id="+Integer.parseInt(courseId);
		/*
		 * 需要判断这个课程是直播呢，还是公开课, 因为他们的文案不在一个地方存
		 */
//		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
//		if(user == null){ //直接跳转到分享页面
//			res.sendRedirect(returnOpenidUri +url);//
//		}else{
//			try {
//				Integer type = onlineCourseService.getIsCouseType(Integer.parseInt(courseId));
//				Map<String,Object> mapCourseInfo = onlineCourseService.shareLink(Integer.parseInt(courseId), type);
//				
//				LOGGER.info("type:"+type);
//				if(type == 1){ //直播或者预约详情页           
//					
//					//1.直播中，2预告，3直播结束
//					if(null != mapCourseInfo.get("lineState") && mapCourseInfo.get("lineState").toString().equals("2")){  //预告
//					
//						url = "/xcviews/html/foreshow.html?course_id="+Integer.parseInt(courseId);
//					
//					}else if(null != mapCourseInfo.get("lineState")){  //直播获取直播结束的
//						url = "/bxg/xcpage/courseDetails?courseId="+Integer.parseInt(courseId);
//					}
//				}else{ //视频音频详情页
//					url = "/xcviews/html/particulars.html?courseId="+Integer.parseInt(courseId);
//				}
//				res.sendRedirect(returnOpenidUri +url);//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * Description：根据课程id得到判断此课程是：直播呢，还是点播呢，还是预约呢？
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("shareJump")
	@ResponseBody
	public ResponseObject shareJump(HttpServletRequest req,
			HttpServletResponse res, Map<String,String> params)throws Exception{
		String courseId = req.getParameter("courseId");  //视频id
		if(courseId == null ){
			return ResponseObject.newErrorResponseObject("获取参数异常");
		}
		//type 1 直播  2其他
		//state  0 直播已结束	 1 直播还未开始	 2 正在直播
		try {
			Map<String,Object> map = onlineCourseService.shareJump(Integer.parseInt(courseId));
			return ResponseObject.newSuccessResponseObject(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}
	
	@RequestMapping("getDomain")
	@ResponseBody
	public ResponseObject getDomain(HttpServletRequest req,
			HttpServletResponse res)throws Exception{
		try {
			return ResponseObject.newSuccessResponseObject(webdomain);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}
	
	
	@RequestMapping("getSystemTime")
	@ResponseBody
	public String getSystemTime(HttpServletRequest req,
			HttpServletResponse res)throws Exception{
		
	    Long l =	System.currentTimeMillis();
	    return l.toString();
	}
	
	public String getSign(Map<String,String> signkv){
		Set<String> keySet = signkv.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        //String APP_SECRET_KEY = "1898130bad871d1bf481823ba1f3ffb1";
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        while (iter.hasNext()) {
            String key = iter.next();
            //LOGGER.info(key + ":" + signkv.get(key));
            sb.append(key + signkv.get(key));
        }
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        //LOGGER.info(sb.toString());
        //LOGGER.info(getMD5(sb.toString()));
        return getMD5(sb.toString());
	}
	
	/** 
     * 生成md5 
     * @param message 
     * @return 
     */  
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 2 将消息变成byte数组
			byte[] input = message.getBytes();
			// 3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);
			// 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str.toLowerCase();
	}  
  
    /** 
     * 二进制转十六进制 
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytesToHex(byte[] bytes) {  
	    StringBuffer md5str = new StringBuffer();  
	    // 把数组每一字节换成16进制连成md5字符串  
	    int digital;  
	    for (int i = 0; i < bytes.length; i++) {  
	        digital = bytes[i];  
	  
	        if (digital < 0) {  
	        digital += 256;  
	        }  
	        if (digital < 16) {  
	        md5str.append("0");  
	        }  
	        md5str.append(Integer.toHexString(digital));  
	    }  
	    return md5str.toString().toUpperCase();  
    }  
}
