package com.xczh.consumer.market.controller.course;

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
import com.xczh.consumer.market.service.FocusService;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.service.IMedicalHospitalApplyService;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IFocusService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
/**
 * 
 * ClassName: HostController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/host")
public class HostController {
	
	
	@Autowired
	private OnlineCourseService onlineCourseService;
	@Autowired
	private OnlineUserService onlineUserService;

	@Autowired
	private AppBrowserService appBrowserService;

	@Autowired
	@Qualifier("focusServiceImpl")
	private FocusService focusService;
	
	@Autowired
	@Qualifier("focusServiceRemote")
	private IFocusService focusServiceRemote;
	
	@Autowired
	private OnlineWebService onlineWebService;
	
	@Autowired
	private IMedicalHospitalApplyService medicalHospitalApplyService;
	
	@Autowired
	private ICourseService courseService;

	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	@Value("${webdomain}")
	private String webdomain;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HostController.class);
	
	
	/**
	 * Description：用户主页
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("hostPageInfo")
	@ResponseBody
	public ResponseObject userHomePage(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("lecturerId")String lecturerId)throws Exception {
		
		
		LOGGER.info("lecturerId-->id"+lecturerId);
		
		Map<String,Object> mapAll = new HashMap<String,Object>();
		/**
		 * 得到讲师   主要是房间号，缩略图的信息、讲师的精彩简介  
		 * 
		 * 这个主播可能认证的是医馆，也可能认证的是医师
		 */
		Map<String,Object> lecturerInfo = onlineUserService.findHostById(lecturerId);
		if(lecturerInfo == null){
			return ResponseObject.newErrorResponseObject("获取医师信息有误");
		}
		
		mapAll.put("lecturerInfo", lecturerInfo);          //讲师基本信息
		MedicalHospital mha = null;
		
		LOGGER.info("lecturerInfo"+lecturerInfo.toString());
		
		//1.医师2.医馆
		if(lecturerInfo.get("type").toString().equals("1")){
			 mha = 	medicalHospitalApplyService.getMedicalHospitalByMiddleUserId(lecturerId);	
		}else if(lecturerInfo.get("type").toString().equals("2")){
			 mha =  medicalHospitalApplyService.getMedicalHospitalByUserId(lecturerId);	
		}
		//认证的主播 还是 医馆
		mapAll.put("hospital",mha);
		List<Integer> listff =   focusServiceRemote.selectFocusAndFansCount(lecturerId);
		mapAll.put("fansCount", listff.get(0));       	   //粉丝总数
		mapAll.put("focusCount", listff.get(1));   	  	   //关注总数
		mapAll.put("criticizeCount", listff.get(2));   	  	   //关注总数
		/**
		 * 判断用户是否已经关注了这个主播
		 */
		OnlineUser user =  appBrowserService.getOnlineUserByReq(req);
	    if(user==null){
	    	mapAll.put("isFours", 0); 
	    }else{
			Integer isFours  = focusService.myIsFourslecturer(user.getId(), lecturerId);
			mapAll.put("isFours", isFours); 		  //是否关注       0 未关注  1已关注
	    }
	    
		/**
		 * 此主播最近一次的直播
		 */
		CourseLecturVo cv = courseService.selectLecturerRecentCourse(lecturerId);
		if(user!=null && cv!=null){
			/**
			 * 如果用户不等于null,且是主播点击的话，就认为是免费的
			 */
			if(cv.getUserLecturerId().equals(user.getId())){
			    cv.setWatchState(3);
		    }
			if(cv.getWatchState()==1){  //免费的课程啦
				onlineWebService.saveEntryVideo(cv.getId(), user);
				
			}else if(cv.getWatchState()==0){ //收费课程
				if(onlineWebService.getLiveUserCourse(cv.getId(),user.getId())){  //大于零--》用户购买过
					cv.setWatchState(2);
				}
			}
		}
		mapAll.put("recentCourse", cv);
	    return ResponseObject.newSuccessResponseObject(mapAll);
	}
	/**
	 * Description：用户主页    -- 课程列表
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("hostPageCourse")
	@ResponseBody
	public ResponseObject userHomePageCourseList(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("lecturerId")String lecturerId,
			@RequestParam("pageNumber")Integer pageNumber,
			@RequestParam("pageSize")Integer pageSize)throws Exception {
		
		Page<CourseLecturVo> page = new Page<>();
	    page.setCurrent(pageNumber);
	    page.setSize(pageSize);
		try {
			Page<CourseLecturVo> list = courseService.selectLecturerAllCourse(page,lecturerId);
			return ResponseObject.newSuccessResponseObject(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("后台数据异常");
		}
	}
}
