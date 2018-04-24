package com.xczh.consumer.market.controller.school;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.service.ListenCourseService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.vo.CourseLecturVo;
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
 * 听课控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileListenCourseController {

	@Autowired
	private ListenCourseService listenCourseService;

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private ICourseService courseService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileListenCourseController.class);

	/**
	 * 听课
	 */
	@RequestMapping("listenCourse")
	@ResponseBody
	public ResponseObject onlineLive(HttpServletRequest req,
									 HttpServletResponse res)
			throws Exception {

		Map<String, Object> mapAll = new HashMap<String, Object>();
		//听课banner
		Page<MobileBanner> MobileBannerPage = new Page<>();
		MobileBannerPage.setCurrent(1);
		MobileBannerPage.setSize(100);
		int bannerType = 4;
		mapAll.put("banner",mobileBannerService.selectMobileBannerPage(MobileBannerPage,bannerType));

		//听课课程
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();

		Map<String,Object> mapTj = new HashMap<String, Object>();
		//List<CourseLecturVo> list = listenCourseService.listenCourseList();
		List<CourseLecturVo> listenCourseList = courseService.listenCourseList();
		mapAll.put("listenCourseList",listenCourseList);

		return ResponseObject.newSuccessResponseObject(mapAll);
	}

	
}
