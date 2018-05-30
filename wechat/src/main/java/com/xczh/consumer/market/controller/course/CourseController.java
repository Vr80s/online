package com.xczh.consumer.market.controller.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.vo.CourseLecturVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@RestController
@RequestMapping("/xczh/course")
public class CourseController {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	private OnlineWebService onlineWebService;

	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService focusServiceRemote;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	@Value("${gift.im.room.postfix}")
	private String postfix;
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	/**
	 * Description：用户当前课程状态   User current course status. 
	 *     用户判断用户是否购买了这个课程
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("userCurrentCourseStatus")
	public ResponseObject userCurrentCourseStatus(HttpServletRequest req,
			HttpServletResponse res, 
			@RequestParam("courseId") Integer courseId)
			throws Exception {
		
		
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		CourseLecturVo cv = null;
		if (user != null) {
			cv = courseServiceImpl.selectUserCurrentCourseStatus(courseId,user.getId());
			/*
			 * 如果是免费的  判断是否学习过
			 */
			if (cv!=null && cv.getWatchState() == 1) { // 免费课程
				if (onlineWebService.getLiveUserCourse(courseId, user.getId())) { // 如果购买过返回true 如果没有购买返回false
					cv.setLearning(1);
				}
			}
		}else{
			cv = courseServiceImpl.selectCurrentCourseStatus(courseId);
		}
		return ResponseObject.newSuccessResponseObject(cv);
	}
	
	
	/**
	 * Description：课程详情（展示页面）页面
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("details")
	public ResponseObject details(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("courseId") Integer courseId)
			throws Exception {

		CourseLecturVo cv = courseServiceImpl.selectCourseMiddleDetailsById(courseId);
		
		if (cv == null) {
			return ResponseObject.newErrorResponseObject("课程信息有误");
		}
		//设置星星级别
		cv.setStartLevel(CourseUtil.criticizeStartLevel(cv.getStartLevel()));
		
		//if(StringUtils.isNotBlank(cv.getDescription())){
			cv.setRichCourseDetailsUrl(returnOpenidUri+"/xcview/html/person_fragment.html?type=1&typeId="+courseId);
		//}
		
		//if(StringUtils.isNotBlank(cv.getLecturerDescription())){
			cv.setRichHostDetailsUrl(returnOpenidUri+"/xcview/html/person_fragment.html?type=3&typeId="+courseId);
		//}
		
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user != null) {
			// 是否关注
			Integer isFours = focusServiceRemote.isFoursLecturer(user.getId(),cv.getUserLecturerId());
			if (isFours != 0) {
				cv.setIsFocus(1);
			}
			/**
			 * 如果用户不等于null,且是主播点击的话，就认为是免费的
			 */
			Boolean falg = onlineWebService.getLiveUserCourse(courseId, user.getId());
			//如果是付费课程，判断这个课程是否已经被购买了
			if (cv.getWatchState() == 0) { // 付费课程
				if (falg) {
					cv.setWatchState(2);
				}
			//如果是免费的  判断是否学习过	
			}else  if (cv.getWatchState() == 1) { // 付费课程
				if (falg) {
					cv.setLearning(1);
				}
			}
		}
		return ResponseObject.newSuccessResponseObject(cv);
	}

	/**
	 * Description：课程详情（播放页面）页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@RequestMapping("liveDetails")
	public ResponseObject liveDetails(HttpServletRequest req,
			HttpServletResponse res, @RequestParam("courseId") Integer courseId)
			throws Exception {

		CourseLecturVo cv = courseServiceImpl.selectCourseDetailsById(courseId);
		if (cv == null) {
			return ResponseObject.newErrorResponseObject("获取课程有误");
		}
		//判断星级
		cv.setStartLevel(CourseUtil.criticizeStartLevel(cv.getStartLevel()));
		
		//if(StringUtils.isNotBlank(cv.getDescription())){
			cv.setRichCourseDetailsUrl(returnOpenidUri+"/xcview/html/person_fragment.html?type=1&typeId="+courseId);
		//}
		
		//if(StringUtils.isNotBlank(cv.getLecturerDescription())){
			cv.setRichHostDetailsUrl(returnOpenidUri+"/xcview/html/person_fragment.html?type=2&typeId="+courseId);
		//}
		
		//判断点钱在线人数
		if(cv.getType()!=null &&  cv.getLineState() != null &&  cv.getType() == 1 && cv.getLineState() == 1){ //表示的是直播中
			Integer lendCount = cv.getLearndCount()+WeihouInterfacesListUtil.getCurrentOnlineNumber(cv.getDirectId());
			cv.setLearndCount(lendCount);
		}
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if (user != null) {
			
			// 是否关注
			Integer isFours = focusServiceRemote.isFoursLecturer(user.getId(),
					cv.getUserLecturerId());
			if (isFours != 0) {
				cv.setIsFocus(1);
			}
			
			Boolean falg = onlineWebService.getLiveUserCourse(courseId, user.getId());
			//如果是付费课程，判断这个课程是否已经被购买了
			if (cv.getWatchState() == 0) { // 付费课程
				if (falg) {
					cv.setWatchState(2);
				}
			//如果是免费的  判断是否学习过	
			}else  if (cv.getWatchState() == 1) { // 付费课程
				if (falg) {
					cv.setLearning(1);
				}
			}
		}
		return ResponseObject.newSuccessResponseObject(cv);
	}

	/**
	 * Description：通过合辑id获取合辑中的课程 creed: Talk is cheap,show me the code
	 * 
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 上午 10:29 2018/1/22 0022
	 **/
	@RequestMapping("getCoursesByCollectionId")
	public ResponseObject getCoursesByCollectionId(
			@RequestParam(value = "collectionId") Integer collectionId)
			throws Exception {
		List<CourseLecturVo> courses = courseServiceImpl.selectCoursesByCollectionId(collectionId);
		return ResponseObject.newSuccessResponseObject(courses);
	}

	/**
	 * 
	 * Description：猜你喜欢
	 * 
	 * @param courseId
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	@RequestMapping("guessYouLike")
	public ResponseObject selectMenuTypeAndRandCourse(
			@RequestParam(value = "courseId") Integer courseId)
			throws Exception {
		Page<CourseLecturVo> page = new Page<CourseLecturVo>();
		page.setCurrent(0);
		page.setSize(2);
		Page<CourseLecturVo> courses = courseServiceImpl
				.selectMenuTypeAndRandCourse(page, courseId);
		return ResponseObject.newSuccessResponseObject(courses);
	}

}
