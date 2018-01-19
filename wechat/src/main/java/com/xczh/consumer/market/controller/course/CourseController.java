package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.wechat.course.service.ICourseService;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.utils.ResponseObject;
/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/course")
public class CourseController {

	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private AppBrowserService appBrowserService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
	
	@Value("${gift.im.room.postfix}")
	private String postfix;
	/**
	 * Description：课程详情
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("details")
	@ResponseBody
	public ResponseObject categoryXCList(HttpServletRequest req,HttpServletResponse res)
			throws Exception {
		String courseId = req.getParameter("courseId");
		com.xczhihui.wechat.course.vo.CourseLecturVo  cv= courseServiceImpl.selectCourseDetailsById(Integer.parseInt(courseId));
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
//		if(cv.getUserId().equals(user.getId()) || 
//				onlineWebService.getLiveUserCourse(course_id,user.getId()).size()>0){
//	       //LOGGER.info("同学,当前课程您已经报名了!");
//			courseLecturVo.setWatchState(0);    
//	    };
		//显示的礼物数基数  -- 随机生成一个 
		/**
		 * 礼物数、在线观看人数、粉丝数
		 *  这三个可以一下搞出来不
		 *  //礼物数
		 *  select SUM(count) from oe_gift_statement where receiver=?
		 *  //在线观看人数
		 *  1000 * 80%  * 120%
		 */
		return ResponseObject.newSuccessResponseObject(cv);
	}
	
}
