package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xczhihui.wechat.course.model.WatchHistory;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IWatchHistoryService;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	private OnlineWebService  onlineWebService;
	
	@Autowired
	private IWatchHistoryService watchHistoryServiceImpl;
	
	@Autowired
	private FocusService focusService;

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
	public ResponseObject details(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("courseId")Integer courseId)
			throws Exception {
		
		CourseLecturVo  cv= courseServiceImpl.selectCourseDetailsById(courseId);
		if(cv==null){
			return ResponseObject.newErrorResponseObject("获取课程有误");
		}
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user!=null){
			/**
			 * 如果用户不等于null,且是主播点击的话，就认为是免费的
			 */
			if(cv.getUserLecturerId().equals(user.getId())){
			    cv.setWatchState(0);
			    return ResponseObject.newSuccessResponseObject(cv);
		    }
	    	WatchHistory target = new WatchHistory();
	    	target.setCourseId(courseId);
			target.setUserId(user.getId());
			if(cv.getWatchState()==0){
				onlineWebService.saveEntryVideo(courseId, user);
				watchHistoryServiceImpl.addOrUpdate(target);
			}else if(cv.getWatchState()==1 
					&& onlineWebService.getLiveUserCourse(courseId,user.getId()).size()>0){
				watchHistoryServiceImpl.addOrUpdate(target);
			}
			watchHistoryServiceImpl.addOrUpdate(target);
			//是否关注
			Integer isFours = focusService.myIsFourslecturer(user.getId(),cv.getUserLecturerId());
			if(isFours != 0){  
				cv.setIsFocus(1);
			}
		}
		return ResponseObject.newSuccessResponseObject(cv);
	}
	
	/**
	 * Description：通过合辑id获取合辑中的课程
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 上午 10:29 2018/1/22 0022
	 **/
	@RequestMapping("getCoursesByCollectionId")
	public ResponseObject getCoursesByCollectionId(@RequestParam(value="collectionId")Integer collectionId )
			throws Exception {
		List<CourseLecturVo> courses= courseServiceImpl.selectCoursesByCollectionId(collectionId);
		return ResponseObject.newSuccessResponseObject(courses);
	}
	
	@RequestMapping("guessYouLike")
	public ResponseObject selectMenuTypeAndRandCourse(
			@RequestParam(value="courseId")Integer courseId)
			throws Exception {
		
		Page<CourseLecturVo> page = new Page<CourseLecturVo>();
		page.setCurrent(0);
		page.setSize(2);
		Page<CourseLecturVo> courses= courseServiceImpl.selectMenuTypeAndRandCourse(page,courseId);
		return ResponseObject.newSuccessResponseObject(courses);
	}

}
