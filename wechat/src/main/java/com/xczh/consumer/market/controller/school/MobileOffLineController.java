package com.xczh.consumer.market.controller.school;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IOfflineCityService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线下课控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/bunch")
public class MobileOffLineController {

	@Autowired
	private OLCourseServiceI wxcpCourseService;

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private IOfflineCityService offlineCityService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileOffLineController.class);


	/*****************************************
	 *
	 *
	 * 	新版app关于学堂的接口   -- 线下培训班接口
	 *
	 *
	 * **************************************
	 */

	@RequestMapping("offLine")
	@ResponseBody
	public ResponseObject offLine() throws Exception {
		Integer current = 1;
		Integer size = 100;
		Map<String, Object> mapAll = new HashMap<String, Object>();
		//线下课banner
		Page<MobileBanner> MobileBannerPage = new Page<>();
		MobileBannerPage.setCurrent(current);
		MobileBannerPage.setSize(size);
		mapAll.put("banner",mobileBannerService.selectMobileBannerPage(2));
		//城市
		Page<OfflineCity> OfflineCityPage = new Page<>();
		OfflineCityPage.setCurrent(current);
		OfflineCityPage.setSize(size);
		Page<OfflineCity> oclist = offlineCityService.selectOfflineRecommendedCityPage(OfflineCityPage);
		mapAll.put("cityList",oclist);

		Page<OfflineCity> OfflineCity = new Page<>();
		OfflineCity.setCurrent(1);
		OfflineCity.setSize(4);
		Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
		//城市  城市中的课程

		LOGGER.info( ocl.getRecords().size()+"");
		
		List<CourseLecturVo> list = wxcpCourseService.offLineClassList(ocl.getRecords());
		
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();

		Map<String,Object> mapTj = new HashMap<String, Object>();

		List<CourseLecturVo> listqg = new ArrayList<CourseLecturVo>();
		if(list!=null){
			for (CourseLecturVo courseLecturVo : list) {
				if("全国课程".equals(courseLecturVo.getNote())){
					listqg.add(courseLecturVo);
				}
			}
		}
		if(listqg.size()>0){
			mapTj.put("title","全国课程");
			mapTj.put("courseList",listqg);
			mapCourseList.add(mapTj);
		}
		for (OfflineCity oc : ocl.getRecords()) {
			Map<String,Object> mapMenu = new HashMap<String, Object>();
			List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
			if(list!=null){
				for (CourseLecturVo courseLecturVo : list) {
					if(oc.getCityName().equals(courseLecturVo.getNote())){
						listMenu.add(courseLecturVo);
					}
				}
			}

			if(listMenu.size()>0){
				mapMenu.put("title", oc.getCityName());
				mapMenu.put("courseList", listMenu);
				mapCourseList.add(mapMenu);
			}
		}
		mapAll.put("allCourseList",mapCourseList);

		return ResponseObject.newSuccessResponseObject(mapAll);
	}

	
}
