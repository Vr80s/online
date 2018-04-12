package com.xczh.consumer.market.controller.common;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.service.MessageService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SLEmojiFilter;
import com.xczh.consumer.market.utils.ThridFalg;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.vo.VersionInfoVo;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.CommonApiService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.wechat.course.service.ICourseService;

/**
 * 通用控制器 ClassName: CommonController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月12日<br>
 */
@Controller
@RequestMapping("/xczh/common")
public class XzCommonController {

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
	
	@Autowired
	private VersionService versionService;
	
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserCenterAPI userCenterAPI;

    @Autowired
    private CommonApiService commonApiService;

	@Autowired
	private ICourseService courseServiceImpl;
    
    
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	@Value("${webdomain}")
	private String webdomain;
	
	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(XzCommonController.class);
	
	
	/**
	 * 查询单个详情
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/richTextDetails")
	@ResponseBody
	public ResponseObject richTextDetails(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("type")Integer type,
			@RequestParam("typeId")String typeId)throws Exception{
		
		return ResponseObject.newSuccessResponseObject(courseServiceImpl.selectCourseDescription(type,typeId));
	}
	
	
	
	/**
	 * 请求转发用于验证用户的登录状态
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyLoginStatus")
	@ResponseBody
	public ResponseObject verifyLoginStatus(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)throws Exception{
		Integer statusFalg = 1000;
		if(req.getParameter("statusFalg")!=null){
			statusFalg = Integer.parseInt(req.getParameter("statusFalg"));
		}
		String openId = req.getParameter("openId");
		String unionId = req.getParameter("unionId");
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(unionId)){
			ThridFalg tf = new ThridFalg();
			tf.setOpenId(openId);
			tf.setUnionId(unionId);
			return ResponseObject.newSuccessResponseObject(tf,statusFalg);
		}
		return ResponseObject.newSuccessResponseObject("登录状态验证",statusFalg);
	}
	
	
	
   /**
    * Description：获取IM服务连接配置信息
    * @param req
    * @return
    * @throws Exception
    * @return ResponseObject
    * @author name：yangxuan <br>email: 15936216273@163.com
    */
   @RequestMapping("getImServerConfig")
   @ResponseBody
   public ResponseObject getImServerConfig(
		   HttpServletRequest req,
		   Integer courseId) throws Exception{
	    Map<String,Object> map = new HashMap<String, Object>();
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user!=null){
			ItcastUser iu = userCenterAPI.getUser(user.getLoginName());
			map.put("guId", iu.getId());
			map.put("guPwd",iu.getPassword());
			map.put("host", host);
			map.put("boshService", boshService);
			map.put("roomJId", courseId+postfix);
	        return ResponseObject.newSuccessResponseObject(map);
		}else{
			 return ResponseObject.newErrorResponseObject("登录失效");
		}
   }
		
	
   @RequestMapping("addOpinion")
   @ResponseBody
   public ResponseObject addOpinion(HttpServletRequest req,
		   @RequestParam("content")String content) throws Exception{
    	 OnlineUser user = appBrowserService.getOnlineUserByReq(req);
    	 
         content = SLEmojiFilter.filterEmojiToNullStr11(content);
         if(!StringUtils.isNotBlank(content)){
             return ResponseObject.newErrorResponseObject("暂不支持添加表情");
         }
         
         LOGGER.info("content"+content);
         content = SLEmojiFilter.filterEmoji(content);
    	 if(user!=null){
    		 messageService.add(content,user.getId());
    	 }else{
    		 messageService.add(content,null);
    	 }
         return ResponseObject.newSuccessResponseObject(null);
   }
	
	/**
	 * 
	 * Description：检查更新
	 * @param req
	 * @param res
	 * @param userVersion
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("checkUpdate")
	@ResponseBody
	public ResponseObject checkUpdate(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("type") Integer type,
			@RequestParam("version") String userVersion)
			throws Exception {

		VersionInfoVo newVer = versionService.getNewVersion(type);
		VersionInfoVo defaultNoUpdateResult = new VersionInfoVo();
		defaultNoUpdateResult.setIsUpdate(false);
		if (newVer == null) {
			return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
		}
		LOGGER.info("userVersion:" + userVersion);
		LOGGER.info("newVer.getVersion():" + newVer.getVersion());
		// 对比版本号
		String newVersion = newVer.getVersion();
		int diff = VersionCompareUtil.compareVersion(newVersion, userVersion);
		if (diff <= 0) {
			LOGGER.info("{}{}{}{}{}-----》已经是最新版本，不需要更新");
			return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
		}
		LOGGER.info("{}{}{}{}{}-----》已经是最新版本，需要更新了"+"-------ismustipdate:"+newVer.getIsMustUpdate());
		
		newVer.setIsUpdate(true);

		return ResponseObject.newSuccessResponseObject(newVer);
	}
	public static void main(String[] args) {
		
		String userVersion = "2.2.1";
		String newVersion = "2.2.2";
		int diff = VersionCompareUtil.compareVersion(newVersion, userVersion);
		if (diff <= 0) {
			System.out.println("已经是最新版本");
		}else{
			System.out.println("需要更新了啊");
		}
	}
	/**
	 * Description：微吼签名认证得到微吼的视频播放权
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("getWeihouSign")
	@ResponseBody
	public ResponseObject getWeihouSign(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("token", req.getParameter("token"));
		String roomNumber = req.getParameter("video"); // 视频id
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		
//		String gvhallId1 = user.getVhallId();
//		LOGGER.info("微吼gvhallId:" + gvhallId);
		// JSONObject json =
		// WeihouInterfacesListUtil.getUserinfo(gvhallId,"name,head,id");
		// String vh_app_key = "71a22e5b4a41483d41d96474511f58f3";
		
		String vhName = user.getName();
		String email = user.getLoginName();
		if (email != null && email.indexOf("@") == -1) {
			email += "@163.com";
		}
		Date d = new Date();
		String start_time = d.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);

		Map<String, String> map = new TreeMap<String, String>();
		map.put("app_key", WeihouInterfacesListUtil.APP_KEY); // 微吼key
		map.put("signedat", start_time); // 时间戳，精确到秒
		map.put("email", email); // email 自己写的
		map.put("roomid", roomNumber); // 视频id
		map.put("account", user.getId()); // 用户帐号
		map.put("username", vhName); // 用户名
		map.put("sign", getSign(map));

		
		return ResponseObject.newSuccessResponseObject(map);
	}

	@RequestMapping("h5ShareAfter")
	@ResponseBody
	public ResponseObject h5ShareLink(HttpServletRequest req,
			HttpServletResponse res,@RequestParam("courseId")Integer courseId)
			throws Exception {
		/*
		 * 需要判断这个课程是直播呢，还是公开课 因为他们的文案不在一个地方存
		 */
		try {
			Integer type = onlineCourseService.getIsCouseType(courseId);
			LOGGER.info("type:" + type);
			CourseLecturVo courseLecturVo = onlineCourseService.h5ShareAfter(courseId, type);
			if (type == 1) {
				// 礼物数：
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
			HttpServletResponse res,@RequestParam("courseId")Integer courseId)
			throws Exception {
		/*
		 * 需要判断这个课程是直播呢，还是公开课, 因为他们的文案不在一个地方存
		 */
		try {
			Integer type = onlineCourseService.getIsCouseType(courseId);
			LOGGER.info("type:" + type);
			Map<String, Object> mapCourseInfo = onlineCourseService.shareLink(courseId, type);
			if (mapCourseInfo.get("description") != null) {
				String description = mapCourseInfo.get("description")
						.toString();
				description = com.xczh.consumer.market.utils.XzStringUtils
						.delHTMLTag(description);
				mapCourseInfo.put("description", description);
			} else {
				mapCourseInfo.put("description", "");
			}
			// mapCourseInfo.put("link",returnOpenidUri+"/bxg/common/pcShareLink?courseId="+Integer.parseInt(courseId));
			// wx_share.html
			mapCourseInfo.put("link", returnOpenidUri+ "/wx_share.html?courseId=" +courseId);
			return ResponseObject.newSuccessResponseObject(mapCourseInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	/**
	 * Description：pc端分享
	 * 
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("pcShareLink")
	public void pcShareLink(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("courseId")Integer courseId) throws Exception {
		/*
		 * 这里需要判断下是不是微信浏览器
		 */
		String wxOrbrower = req.getParameter("wxOrbrower"); // 视频id
		if (StringUtils.isNotBlank(wxOrbrower) && "wx".equals(wxOrbrower)) {
			String strLinkHome = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ WxPayConst.gzh_appid
					+ "&redirect_uri="
					+ returnOpenidUri
					+ "/bxg/wxpay/h5ShareGetWxUserInfo?courseId="
					+ courseId
					+ "&response_type=code&scope=snsapi_userinfo&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect"
							.replace("appid=APPID", "appid="
									+ WxPayConst.gzh_appid);
			res.sendRedirect(strLinkHome);
		} else if (StringUtils.isNotBlank(wxOrbrower)
				&& "brower".equals(wxOrbrower)) {
			res.sendRedirect(returnOpenidUri
					+ "/bxg/wxpay/h5ShareGetWxUserInfo?courseId=" + courseId
					+ "&wxOrbrower=brower");//
		}
	}

	/**
	 * Description：根据课程id得到判断此课程是：直播呢，还是点播呢，还是预约呢？
	 * 
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("shareJump")
	@ResponseBody
	public ResponseObject shareJump(HttpServletRequest req,
			HttpServletResponse res,@RequestParam("courseId")Integer courseId)
			throws Exception {
		// type 1 直播 2其他
		// state 0 直播已结束 1 直播还未开始 2 正在直播
		try {
			Map<String, Object> map = onlineCourseService.shareJump(courseId);
			return ResponseObject.newSuccessResponseObject(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	@RequestMapping("getDomain")
	@ResponseBody
	public ResponseObject getDomain(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		try {
			return ResponseObject.newSuccessResponseObject(webdomain);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("请求有误");
		}
	}

	@RequestMapping("getSystemTime")
	@ResponseBody
	public String getSystemTime(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		Long l = System.currentTimeMillis();
		return l.toString();
	}
	/**
	 * Description：获取问题
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="getProblems")
	@ResponseBody
	public ResponseObject JobVo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return ResponseObject.newSuccessResponseObject(commonApiService.getProblems("common_problems"));
    }
	
	/**
	 * Description：获取单个问题和答案
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="getProblemAnswer")
	@ResponseBody
	public ResponseObject JobVo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("id")String id) throws Exception {
		return ResponseObject.newSuccessResponseObject(commonApiService.getProblemAnswer(id));
    }
	
	public String getSign(Map<String, String> signkv) {
		Set<String> keySet = signkv.keySet();
		Iterator<String> iter = keySet.iterator();
		StringBuilder sb = new StringBuilder();
		// String APP_SECRET_KEY = "1898130bad871d1bf481823ba1f3ffb1";
		sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
		while (iter.hasNext()) {
			String key = iter.next();
			// LOGGER.info(key + ":" + signkv.get(key));
			sb.append(key + signkv.get(key));
		}
		sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
		// LOGGER.info(sb.toString());
		// LOGGER.info(getMD5(sb.toString()));
		return getMD5(sb.toString());
	}

	/**
	 * 生成md5
	 * 
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
