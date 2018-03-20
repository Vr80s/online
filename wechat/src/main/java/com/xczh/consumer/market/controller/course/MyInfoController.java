package com.xczh.consumer.market.controller.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IFocusService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
import com.xczhihui.wechat.course.vo.WatchHistoryVO;
/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/myinfo")
public class MyInfoController {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService ifocusService;
	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private AppBrowserService appBrowserService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);
	
	@Value("${gift.im.room.postfix}")
	private String postfix;
	/**
	 * 
	 * Description：学习模块课程
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResponseObject categoryXCList(HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user == null){
			ResponseObject.newErrorResponseObject("登录失效");
		}
		List<CourseLecturVo>  listAll  = courseServiceImpl.selectLearningCourseListByUserId(user.getId());
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
		for (CourseLecturVo courseLecturVo : listAll) {
			if("我的课程".equals(courseLecturVo.getNote())){
				listTj.add(courseLecturVo);
			}
			if("已结束课程".equals(courseLecturVo.getNote())){
				listNw.add(courseLecturVo);
			}
		}
		mapTj.put("title","我的课程");
		mapTj.put("courseList",listTj);
		
		mapNw.put("title","已结束课程");
		mapNw.put("courseList",listNw);
		
		mapCourseList.add(mapTj);
		mapCourseList.add(mapNw);
		return ResponseObject.newSuccessResponseObject(mapCourseList);
	}
	/**
	 * Description： 关注的主播（我的关注）
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
			HttpServletResponse res)
			throws Exception {
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
		return ResponseObject.newSuccessResponseObject(ifocusService.selectFocusList(user.getId()));
	}
	/**
	 * Description： 取消/增加   关注
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("updateFocus")
	@ResponseBody
	public ResponseObject updateFocus(HttpServletRequest req,
			@RequestParam("lecturerId") String lecturerId,
			@RequestParam("type") Integer type)
			throws Exception {
		OnlineUser onlineUser =  appBrowserService.getOnlineUserByReq(req);
	    if(onlineUser==null){
	    	return ResponseObject.newErrorResponseObject("登录失效");
	    }
		OnlineUser onlineLecturer= onlineUserService.findUserById(lecturerId);
		if(null == onlineLecturer){	
			return ResponseObject.newErrorResponseObject("操作失败");
		}
		String result = ifocusService.updateFocus(lecturerId,onlineUser.getId(),type);
		return ResponseObject.newSuccessResponseObject(result);
	}
}
