package com.xczh.consumer.market.controller.course;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IFocusService;
/**
 * 我的信息管理页面
 * ClassName: MyManagerController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月22日<br>
 */
@Controller
@RequestMapping("/xczh/myinfo")
public class MyManagerController {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService ifocusService;
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private OnlineCourseService onlineCourseService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	
	@Autowired
	private UserCoinService userCoinService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyManagerController.class);
	
	/**
	 * Description：开始直播时调用，因为要区分这个直播来自app直播呢，还是pc端直播了。
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("appLivePre")
	@ResponseBody
	public ResponseObject myFocus(HttpServletRequest req)
			throws Exception {
		
		/*
		 * 显示熊猫币、已购买的课程（不包含免费的）、是否是主播
		 * 获取用户信息
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		/**
		 * 判断这个用户有没有主播权限
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		if(user == null){
			//熊猫币
			map.put("xmbCount", userCoinService.getBalanceByUserId(user.getId()).get("balanceTotal"));
			//更新下用户信息
			map.put("user",onlineUserService.findUserById(user.getId()));
			//查找购买的课程数
			map.put("courseCount",user);
		}else{
			map.put("xmbCount",0);
			map.put("user","");
			map.put("courseCount",0);
		}
		
		String courseId = req.getParameter("courseId");
	    onlineCourseService.updateLiveSourceType(courseId);
		return ResponseObject.newSuccessResponseObject("操作成功");
	}
}
