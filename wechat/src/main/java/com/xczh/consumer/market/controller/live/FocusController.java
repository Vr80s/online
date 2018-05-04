package com.xczh.consumer.market.controller.live;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.utils.ResponseObject;

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

	
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FocusController.class);
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
		
		
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String userId = req.getParameter("userId");
//		String token = req.getParameter("token");
//		OnlineUser user =null;
//		if(token!=null){
//			user = appBrowserService.getOnlineUserByReq(req);
//		}else{
//			String appUniqueId = req.getParameter("appUniqueId");
//			if(appUniqueId!=null){
//				Map<String, Object> mapAppRecord = onlineUserService.getAppTouristRecord(appUniqueId);
//				Boolean regis =  (Boolean) mapAppRecord.get("isRegis");
//				if(!regis){ //返回用户基本信息   --主要是不返回loginName
//					user = onlineUserService.findUserByIdAndVhallNameInfo(mapAppRecord.get("userId").toString());
//				}else{ //返回用户信息 -- 包括loginName
//					user = onlineUserService.findUserById(mapAppRecord.get("userId").toString());
//				}
//			}else{
//				user = onlineUserService.findUserById(userId);
//			}
//		}
//		Map<String,Object> map =new HashMap<String, Object>();
//		if(null == user){	
//			//return ResponseObject.newErrorResponseObject("登录失效");
//			map.put("countFans", 0);
//			map.put("countFocus", 0);
//			map.put("xmbCount", 0);
//			/**
//			 * 查下房间号
//			 *  是否是讲师：0,用户，1既是用户也是讲师
//			 */
//			map.put("user", null);
//		}else{
//			//我的粉丝总数
//			Integer countFans =	focusService.findMyFansCount(user.getId());
//			//我的关注总数
//			Integer countFocus =focusService.findMyFocusCount(user.getId());
//			map.put("countFans", countFans);
//			map.put("countFocus", countFocus);
//			map.put("xmbCount", userCoinService.getBalanceByUserId(user.getId()));
//			/**
//			 * 查下房间号
//			 *  是否是讲师：0,用户，1既是用户也是讲师
//			 */
//			user.setTicket(token);
//			map.put("user", user);
//		}
//		return ResponseObject.newSuccessResponseObject(map);
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
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
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
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
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
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
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
		LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
	}

	
}
