package com.xczh.consumer.market.controller.live;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/bxg/bunch")
public class BunchPlanController {

	@Autowired
	private OLCourseServiceI wxcpCourseService;

	@Autowired
	private OnlineCourseService onlineCourseService;
	
	@Autowired
	private FocusService focusService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	@Autowired
	private OnlineWebService onlineWebService;
	
	@Autowired
	private GiftService giftService;

	@Value("${gift.im.room.postfix}")
	private String postfix;
	@Value("${gift.im.boshService}")
	private String boshService;//im服务地址
	@Value("${gift.im.host}")
	private  String host;

	// 新增的课程列表页
	@RequestMapping("categorylist")
	@ResponseBody
	public ResponseObject categoryXCList(HttpServletRequest req,HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		return ResponseObject.newSuccessResponseObject(wxcpCourseService.categoryXCList());
	}

	// 新增点播课程list
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject courseXCList(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String menid = req.getParameter("menu_id");
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		System.out.println("pageNumber:"+s+"===========================pageSize:"+e);
		String multimedia_type = req.getParameter("multimedia_type");
		if ("".equals(menid) || menid == null || "null".equals(menid)) {
			return ResponseObject.newErrorResponseObject("分类id不能为空");
		}
		if ("".equals(multimedia_type) || multimedia_type == null || "null".equals(multimedia_type)) {
			return ResponseObject.newErrorResponseObject("媒体类型不能为空");
		}
		int number = 0;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.courseXCListByCategory(menid,number, pageSize,Integer.parseInt(multimedia_type));
		System.out.println("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}
	/***
	 * 课程详细信息
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detail")
	@ResponseBody
	@Transactional
	public ResponseObject courseDetail(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		String courseid = req.getParameter("course_id");
		if ("".equals(courseid) || courseid == null || "null".equals(courseid)) {
			return ResponseObject.newErrorResponseObject("课程ID是空的");
		}
		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		if(user == null ){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
		CourseLecturVo courseLecturVo =wxcpCourseService.bunchDetailsByCourseId(Integer.parseInt(courseid));
		/*
		 * 得到此课程下的排序最靠上面的一个
		 */
		if(courseLecturVo == null){
			return ResponseObject.newSuccessResponseObject("获取课程异常");
		}
		//courseLecturVo.setImRoomId(courseLecturVo.getId()+postfix);
		/**
	     * 是否关注
	     */
		Integer isFours  = focusService.myIsFourslecturer(user.getId(), courseLecturVo.getUserId());
		courseLecturVo.setIsfocus(isFours);
		
		/*
		 * 我的粉丝总数
		 */
		Integer countFans =	focusService.findMyFansCount(courseLecturVo.getUserId());
		courseLecturVo.setCountFans(countFans);
		/*
		 * 我的礼物总数 
		 */
		courseLecturVo.setCountGift(giftService.findByUserId(courseLecturVo.getUserId()));

		if(courseLecturVo.getWatchState()!=0){
			if(courseLecturVo.getUserId().equals(user.getId()) ||
					onlineWebService.getLiveUserCourse(Integer.parseInt(courseid),user.getId()).size()>0){
		       //System.out.println("同学,当前课程您已经报名了!");
		       courseLecturVo.setWatchState(0);    
		    };
		}
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}
	
	/**
	 * 线下培训班
	 */
	@RequestMapping("offLineClass")
	@ResponseBody
	public ResponseObject offLineClass(HttpServletRequest req,
			HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String keyWord = req.getParameter("keyWord");
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		System.out.println("pageNumber:"+s+"===========================pageSize:"+e);
		int number = 0;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.offLineClass(keyWord,number, pageSize);
		System.out.println("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}

	/**
	 * 线下培训班列表
	 */
	@RequestMapping("offLineClassList")
	@ResponseBody
	public ResponseObject offLineClassList(HttpServletRequest req,
									   HttpServletResponse res, Map<String, String> params)
			throws Exception {
		//多媒体类型1视频2音频
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
		System.out.println("pageNumber:"+s+"===========================pageSize:"+e);
		int number = 1;
		if (!"".equals(s) && s != null && !"null".equals(s)) {
			number = Integer.valueOf(s);
		}
		int pageSize = 0;
		if ("".equals(e) || e == null || "null".equals(e)) {
			pageSize = 6;
		} else {
			pageSize = Integer.valueOf(e);
		}
		List<CourseLecturVo> list = wxcpCourseService.offLineClassList(number, pageSize);
		/**
		 * 循环把城市展示出来
		 */
		for (CourseLecturVo courseLecturVo : list) {
			String city = courseLecturVo.getAddress();
			String [] citys = city.split("-");
			courseLecturVo.setCity(citys[1]);
		}
		System.out.println("list.size():"+list.size());
		return ResponseObject.newSuccessResponseObject(list);
	}

	/**
	 * 线下培训班详情
	 */
	@RequestMapping("offLineClassItem")
	@ResponseBody
	public ResponseObject offLineClassItem(HttpServletRequest req,
										   HttpServletResponse res, Integer id)
			throws Exception {
	/*	Map<String, String> params =new HashMap<>();
		params.put("token",req.getParameter("token"));

		OnlineUser user = appBrowserService.getOnlineUserByReq(req, params);
		if(null == user){
			return ResponseObject.newErrorResponseObject("获取用户信息异常");
		}
*/

		String userId=req.getParameter("userId");
		OnlineUser onlineUser=new OnlineUser();
		onlineUser.setId(userId);
		CourseLecturVo courseLecturVo=wxcpCourseService.offLineClassItem(id,userId);
		ResponseObject resp = onlineCourseService.courseIsBuy(onlineUser,id);
		if(resp.isSuccess()){//已经付过费了
			courseLecturVo.setWatchState(0);
		}
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}
}
