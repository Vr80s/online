package com.xczh.consumer.market.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.MessageService;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.VersionInfoVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.common.util.SLEmojiFilter;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.ThridFalg;

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
	private AppBrowserService appBrowserService;
	
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
	

	
	@Autowired
	private CacheService cacheService;
    
    
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;

	@Value("${webdomain}")
	private String webdomain;
	
	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;
	@Value("${gift.im.host}")
	private  String host;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XzCommonController.class);
	
	
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
	 * app端 tokenfilter 验证token是否有效
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkToken")
	@ResponseBody
	public ResponseObject checkToken(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		String token = req.getParameter("token");
		if (!StringUtils.isNotBlank(token)) {
			return ResponseObject.newErrorResponseObject("token不能为空", 1001);
		}
		OnlineUser ou = cacheService.get(token);
		if (null == ou) {
			return ResponseObject.newErrorResponseObject("已过期", 1002);
		} else if (null != ou && cacheService.get(ou.getId()) != null && cacheService.get(ou.getId()).equals(token)) {
			return ResponseObject.newSuccessResponseObject("有效", 1000);
		} else if (ou.getLoginName() != null) {
//			 String model = cacheService.get(ou.getLoginName());
//			 if(model!=null){ 
//				 return  ResponseObject.newErrorResponseObject(model,1005); 
//			 }else {
//				 return  ResponseObject.newErrorResponseObject("其他设备",1005);
//			 } 
			 //return  ResponseObject.newErrorResponseObject(model,1005); 
			//暂时有效，为了方便测试使用
			return ResponseObject.newSuccessResponseObject("有效", 1000);
		} else {
			return ResponseObject.newSuccessResponseObject("有效", 1000);
		}
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
		
   /**
    * 意见反馈接口
    * @param req
    * @param content
    * @return
    * @throws Exception
    */
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
	 * 获取 同环境下的 pc端主域名
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 得到服务器当前时间的毫秒值
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSystemTime")
	@ResponseBody
	public String getSystemTime(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		Long l = System.currentTimeMillis();
		return l.toString();
	}
	/**
	 * Description：获取 所有问题
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping(value="getProblems")
	@ResponseBody
	public ResponseObject getProblems(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
	public ResponseObject getProblemAnswer(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("id")String id) throws Exception {
		return ResponseObject.newSuccessResponseObject(commonApiService.getProblemAnswer(id));
    }
	
}
