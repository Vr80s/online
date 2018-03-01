package com.xczh.consumer.market.controller.course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

import org.apache.commons.lang.StringUtils;
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

import java.util.Date;
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
	
	@Value("${live.preheating}")
	private Integer livePreheating;
	
	/**
	 * Description：课程详情（展示页面）页面
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
		
		CourseLecturVo  cv= courseServiceImpl.selectCourseMiddleDetailsById(courseId);
		if(cv==null){
			return ResponseObject.newErrorResponseObject("课程信息有误");
		}
		
		cv.setStartLevel(criticizeStartLevel(cv.getStartLevel()));
		
		

		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user!=null){
			/**
			 * 如果用户不等于null,且是主播点击的话，就认为是免费的
			 */
			if(cv.getUserLecturerId().equals(user.getId())){
			    cv.setWatchState(3);
			    return ResponseObject.newSuccessResponseObject(cv);
		    }
			
//			客户端主动增加播放历史--》注释掉这里的			
//	    	WatchHistory target1 = new WatchHistory();
//	    	target.setCourseId(courseId);
//			target.setUserId(user.getId());
			
			if(cv.getWatchState()==1){  //免费的课程啦
				onlineWebService.saveEntryVideo(courseId, user);
				
				//watchHistoryServiceImpl.addOrUpdate(target);
			}else if(cv.getWatchState()==0){ //收费课程
				if(onlineWebService.getLiveUserCourse(courseId,user.getId()).size()>0){  //大于零--》用户购买过  
					cv.setWatchState(2);
					
					//watchHistoryServiceImpl.addOrUpdate(target);
				}
			}
			//是否关注
			Integer isFours = focusService.myIsFourslecturer(user.getId(),cv.getUserLecturerId());
			if(isFours != 0){  
				cv.setIsFocus(1);
			}
		}
		/*
		 * 如果开始前的一个时间段  小于等于 当前直播开始时间,就是即将直播状态   
		 */
//		if(null!=cv.getType() && cv.getType()==3 && cv.getLineState() ==2 ){
//			long currentTime = System.currentTimeMillis();
//			currentTime += 1*livePreheating*60*60*1000;
//			long startTimeLong = cv.getStartTime().getTime();
//			
//			LOGGER.info("========currentTime："+startTimeLong);
//			LOGGER.info("========startTimeLong："+startTimeLong);
//			LOGGER.info("========currentTime>=startTimeLong："+(currentTime>=startTimeLong));
//			if(currentTime>=startTimeLong){
//				cv.setLineState(4);
//			}
//		}
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
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("liveDetails")
	public ResponseObject liveDetails(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("courseId")Integer courseId)
			throws Exception {
		
		CourseLecturVo  cv= courseServiceImpl.selectCourseDetailsById(courseId);
		if(cv==null){
			return ResponseObject.newErrorResponseObject("获取课程有误");
		}
		
		cv.setStartLevel(criticizeStartLevel(cv.getStartLevel()));
		
		
		
		/**
		 * 这里需要判断是否购买过了
		 */
		OnlineUser user = appBrowserService.getOnlineUserByReq(req);
		if(user!=null){
			/**
			 * 如果用户不等于null,且是主播点击的话，就认为是免费的
			 */
			if(cv.getUserLecturerId().equals(user.getId())){
			    cv.setWatchState(3);
			    return ResponseObject.newSuccessResponseObject(cv);
		    }
//			客户端主动增加播放历史--》注释掉这里的			
//	    	WatchHistory target = new WatchHistory();
//	    	target.setCourseId(courseId);
//			target.setUserId(user.getId());
			if(cv.getWatchState()==1){  //免费的课程啦
				onlineWebService.saveEntryVideo(courseId, user);
//				watchHistoryServiceImpl.addOrUpdate(target);
			}else if(cv.getWatchState()==0   ){ //收费课程
				if(onlineWebService.getLiveUserCourse(courseId,user.getId()).size()>0){  //大于零--》用户购买过  
					cv.setWatchState(2);
//					watchHistoryServiceImpl.addOrUpdate(target);
				}
			}
			//是否关注
			Integer isFours = focusService.myIsFourslecturer(user.getId(),cv.getUserLecturerId());
			if(isFours != 0){  
				cv.setIsFocus(1);
			}
		}
		/*
		 * 如果开始前的一个时间段  小于等于 当前直播开始时间,就是即将直播状态   
		 */
//		if(cv.getType()!=null && cv.getType()==3 && cv.getLineState() ==2 ){
//			LOGGER.info("========修改状态");
//			long currentTime = System.currentTimeMillis();
//			currentTime += 1*livePreheating*60*60*1000;
//			long startTimeLong = cv.getStartTime().getTime();
//			// 30 +1 > 30.5
//			LOGGER.info("========currentTime："+startTimeLong);
//			LOGGER.info("========startTimeLong："+startTimeLong);
//			LOGGER.info("========currentTime>=startTimeLong："+(currentTime>=startTimeLong));
//			if(currentTime>=startTimeLong){
//				cv.setLineState(4);
//			}
//		}
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
	
	/**
	 * 
	 * Description：猜你喜欢
	 * @param courseId
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
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
	
	/**
	 * Description：计算评论星级
	 * @return
	 * @return Double
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Double criticizeStartLevel(Double startLevel){
		
		LOGGER.info("之前  startLevel  ------》   {}{}{}{}"+startLevel);
		if(startLevel!=null && startLevel!=0){ //不等于0
			  String b = startLevel.toString();
			  if(b.length()>1 && !b.substring(b.length()-1,b.length()).equals("0")){ //不等于整数
				  String [] arr = b.split("\\.");
				  Integer tmp = Integer.parseInt(arr[1]);
				  if(tmp>=5){
					  LOGGER.info("之后     startLevel  ------》   【】【】【】【】【】"+startLevel);
					  return  (double) (Integer.parseInt(arr[0])+1);
				  }else{
					  LOGGER.info("之后     startLevel  ------》   【】【】【】【】【】"+startLevel);
					  return Double.valueOf(arr[0]+"."+5);
				  }
			  }else{
				  LOGGER.info("之后     startLevel  ------》   【】【】【】【】【】"+startLevel);
				  return startLevel; 
			  }
		}
		LOGGER.info("之后     startLevel  ------》   【】【】【】【】【】"+startLevel);
		return startLevel;
	}
	
	public static void main(String[] args) {
		//计算星级
		Double a = 4.1;
		if(a != 0){ //不等于0
		  String b = a.toString();
		  if(b.length()>1){ //不等于整数
			  String [] arr = b.split("\\.");
			  Integer tmp = Integer.parseInt(arr[1]);
			  if(tmp>=5){
				  System.out.println(Integer.parseInt(arr[0])+1);
			  }else{
				  System.out.println(arr[0]+"."+5);
			  }
		  }	
		}
	}

}
