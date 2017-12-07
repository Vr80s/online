package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.service.UserCoinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/focus")
public class FocusController {

	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private FocusService focusService;
	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private UserCoinService userCoinService;
	/**
	 * Description： 我的主页
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@SuppressWarnings("unused")
	@RequestMapping("myHome")
	@ResponseBody
	public ResponseObject myHome(HttpServletRequest req,
								 HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		String userId = req.getParameter("userId");
		String token = req.getParameter("token");
		
		String appUniqueId = req.getParameter("appUniqueId");
		Map<String, Object> mapAppRecord = onlineUserService.getAppTouristRecord(appUniqueId);
		
		Boolean regis =  (Boolean) mapAppRecord.get("isRegis");
		OnlineUser user =null;
		
		if(!regis){ //返回用户基本信息   --主要是不返回loginName
			user = onlineUserService.findUserByIdAndVhallNameInfo(mapAppRecord.get("userId").toString());
		}else{ //返回用户信息 -- 包括loginName
			user = onlineUserService.findUserById(mapAppRecord.get("userId").toString());
		}
		
		Map<String,Object> map =new HashMap<String, Object>();
		if(null == user){	
			//return ResponseObject.newErrorResponseObject("获取用户信息异常");
			map.put("countFans", 0);
			map.put("countFocus", 0);
			map.put("xmbCount", 0);
			/**
			 * 查下房间号
			 *  是否是讲师：0,用户，1既是用户也是讲师
			 */
			map.put("user", null);
		}else{
			//我的粉丝总数
			Integer countFans =	focusService.findMyFansCount(user.getId());
			//我的关注总数
			Integer countFocus =focusService.findMyFocusCount(user.getId());
			map.put("countFans", countFans);
			map.put("countFocus", countFocus);
			map.put("xmbCount", userCoinService.getBalanceByUserId(user.getId()).get("balanceTotal"));
			/**
			 * 查下房间号
			 *  是否是讲师：0,用户，1既是用户也是讲师
			 */
			user.setTicket(token);
			map.put("user", user);
		}
		return ResponseObject.newSuccessResponseObject(map);
	}
	/**
	 * Description： 我的粉丝
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("myFans")
	@ResponseBody
	public ResponseObject myFans(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String pageNumberS = req.getParameter("pageNumber");
		String pageSizeS = req.getParameter("pageSize");
		if(null == pageNumberS || null == pageSizeS){	
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		int pageNumber =Integer.parseInt(pageNumberS);
		int pageSize = Integer.parseInt(pageSizeS);

		System.err.println("pageNumber:"+pageNumber+"=============="+"pageSize:"+pageSize);
		return ResponseObject.newSuccessResponseObject(focusService.findMyFans(user.getId(),pageNumber,pageSize));
	}
	
	/**
	 * Description： 关注的人（我的关注）
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("myFocus")
	@ResponseBody
	public ResponseObject myFocus(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String pageNumberS = req.getParameter("pageNumber");
		String pageSizeS = req.getParameter("pageSize");
		if(null == pageNumberS || null == pageSizeS){	
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req, params);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		int pageNumber =Integer.parseInt(pageNumberS);
		int pageSize = Integer.parseInt(pageSizeS);
		return ResponseObject.newSuccessResponseObject(focusService.findmyFocus(user.getId(),pageNumber,pageSize));
	}
	/**
	 * Description： 取消关注
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("cancel")
	@ResponseBody
	public ResponseObject cancelFocus(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String lecturerId = req.getParameter("lecturerId");
		OnlineUser onlineUser =  appBrowserService.getOnlineUserByReq(req, params);
	    if(onlineUser==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		OnlineUser onlineLecturer= onlineUserService.findUserById(lecturerId);
		if(null == onlineLecturer){	
			return ResponseObject.newErrorResponseObject("获取讲师信息异常");
		}
		return focusService.cancelFocus(lecturerId,onlineUser.getId());
	}
	
	/**
	 * Description： 增加关注信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("add")
	@ResponseBody
	public ResponseObject addFocus(HttpServletRequest req,
			HttpServletResponse res)
			throws Exception {
		Map<String, String> params=new HashMap<>();
		params.put("token",req.getParameter("token"));
		String lecturerId = req.getParameter("lecturerId");
		String courseId = req.getParameter("courseId");
		OnlineUser onlineUser =  appBrowserService.getOnlineUserByReq(req, params);
	    if(onlineUser==null){
	    	return ResponseObject.newErrorResponseObject("获取用户信息异常");
	    }
		int course_id =0;
		if(null != courseId){
			course_id = Integer.parseInt(courseId);
		}
		OnlineUser onlineLecturer= onlineUserService.findUserById(lecturerId);
		if(null == onlineLecturer){	
			return ResponseObject.newErrorResponseObject("获取讲师信息异常");
		}
		try {
			Integer isFours = focusService.myIsFourslecturer(onlineUser.getId(),lecturerId);
			if(isFours != 0){
				return ResponseObject.newErrorResponseObject("你已经关注了这个主播啦");
			}else{
				focusService.addFocusInfo(onlineUser,onlineLecturer,course_id);
				return ResponseObject.newSuccessResponseObject("关注成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台异常,请联系客服!");
		}
	}

	
}
