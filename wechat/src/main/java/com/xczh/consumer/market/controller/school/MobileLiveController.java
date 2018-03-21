package com.xczh.consumer.market.controller.school;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.service.IMobileBannerService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直播控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/live")
public class MobileLiveController {

	@Autowired
	private OnlineCourseService onlineCourseService;

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private ICourseService courseService;


	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileLiveController.class);

	/*****************************************
	 * 		新版app关于学堂的接口   -- 直播接口
	 * **************************************
	 */
	@RequestMapping("onlineLive")
	@ResponseBody
	public ResponseObject onlineLive(HttpServletRequest req,
									 HttpServletResponse res)
			throws Exception {
		Integer current = 1;
		Integer size = 100;
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//直播banner
		Page<MobileBanner> MobileBannerPage = new Page<>();
		MobileBannerPage.setCurrent(current);
		MobileBannerPage.setSize(size);
		int bannerType = 3;
		mapAll.put("banner",mobileBannerService.selectMobileBannerPage(MobileBannerPage,bannerType));

		//直播 中的课程
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();

		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		Map<String,Object> mapZz = new HashMap<String, Object>();
		Map<String,Object> mapHf = new HashMap<String, Object>();
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listZz = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listHf = new ArrayList<CourseLecturVo>();


		//List<CourseLecturVo> list = onlineCourseService.findLiveListInfo();
		List<CourseLecturVo> list = courseService.findLiveListInfo();
		for (CourseLecturVo courseLecturVo : list) {
			if("正在直播".equals(courseLecturVo.getNote())){
				listTj.add(courseLecturVo);
			}
			if("即将直播".equals(courseLecturVo.getNote())){
				listNw.add(courseLecturVo);
			}
			if("直播课程".equals(courseLecturVo.getNote())){
				listZz.add(courseLecturVo);
			}
			if("精彩直播回放".equals(courseLecturVo.getNote())){
				listHf.add(courseLecturVo);
			}
		}

		if(listTj.size()>0){    
			mapTj.put("title","正在直播");
			mapTj.put("lineState","1");
			mapTj.put("courseList",listTj);
			mapCourseList.add(mapTj);
		}

		if(listNw.size()>0){
			mapNw.put("title","即将直播");
			mapNw.put("lineState","2");
			mapNw.put("courseList",listNw);
			mapCourseList.add(mapNw);
		}

		if(listZz.size()>0){
			mapZz.put("title","直播课程");
			mapZz.put("lineState","1234");
			mapZz.put("courseList",listZz);
			mapCourseList.add(mapZz);
		}

		if(listHf.size()>0){
			mapHf.put("title","精彩直播回放");
			mapHf.put("lineState","3");
			mapHf.put("courseList",listHf);
			mapCourseList.add(mapHf);
		}

		mapAll.put("allCourseList",mapCourseList);

		return ResponseObject.newSuccessResponseObject(mapAll);
	}



}
