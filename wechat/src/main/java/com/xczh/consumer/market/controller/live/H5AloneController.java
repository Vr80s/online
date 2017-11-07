package com.xczh.consumer.market.controller.live;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

//import java.util.UUID;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/live")
public class H5AloneController {

	@Autowired
	private OnlineCourseService onlineCourseService;

	@Autowired
	private OLCourseServiceI wxcpCourseService;
	
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
	
	
	/**
	 * 这个方法暂时先这样提供，能用到的就用呗
	 * 
	 * 只需要得到这个课程的状态集合,用户判断前台的页面跳转
	 * watchState 观看状态  0 免费观看  1 需要收费  2 需要密码
	 * lineState: 0 直播已结束 1 直播还未开始 2 正在直播
	 * type: 1直播  2点播 3音频 
	 * 
	 */
	@RequestMapping("commonCourseStatus")
	@ResponseBody
	public ResponseObject commonCourseStatus(HttpServletRequest req,
											 HttpServletResponse res)throws Exception {
		
		if(null == req.getParameter("course_id")){
			return ResponseObject.newErrorResponseObject("缺少参数");
		}
		Map<String, String> params=new HashMap<>();
		params.put("token",req.getParameter("token"));
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		if(null == user){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		
		int course_id =Integer.parseInt(req.getParameter("course_id"));
		CourseLecturVo courseLecturVo = onlineCourseService.
				courseStatusList(course_id,user.getId()); //课程简介
		
		
		if(null == courseLecturVo){
			return ResponseObject.newErrorResponseObject("获取课程数据有误");
		}
		/**
		 * 判断用户是否需要密码或者付费
		 */
//		if(courseLecturVo.getWatchState()==2){  //是否已经认证了密码了
//			ResponseObject resp = onlineCourseService.courseIsConfirmPwd(user,course_id);
//			if(resp.isSuccess()){//认证通过
//				courseLecturVo.setWatchState(0);
//			}
//		}else if(courseLecturVo.getWatchState()==1){  //是否已经付过费了
//			ResponseObject resp = onlineCourseService.courseIsBuy(user,course_id);
//			if(resp.isSuccess()){//已经付过费了
//				courseLecturVo.setWatchState(0);
//			}
//		}
		if(courseLecturVo.getWatchState()!=0){
			if(courseLecturVo.getUserId().equals(user.getId()) 
					|| onlineWebService.getLiveUserCourse(course_id,user.getId()).size()>0){
		       //System.out.println("同学,当前课程您已经报名了!");
		       courseLecturVo.setWatchState(0);    
		    };
		}
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}
}
