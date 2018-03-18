package com.xczh.consumer.market.controller.school;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.wechat.course.model.MobileProject;
import com.xczhihui.wechat.course.model.OfflineCity;
import com.xczhihui.wechat.course.service.IMobileBannerService;
import com.xczhihui.wechat.course.service.IMobileProjectService;
import com.xczhihui.wechat.course.service.IOfflineCityService;
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
 * 分类控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/classify")
public class MobileClassifyController {

	@Autowired
	private OLCourseServiceI wxcpCourseService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private IMedicalDoctorBusinessService medicalDoctorBusinessService;

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private IMobileProjectService mobileProjectService;

	@Autowired
	private IOfflineCityService offlineCityService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileClassifyController.class);

	/**
	 * 分类
	 */
	@RequestMapping("schoolClass")
	@ResponseBody
	public ResponseObject schoolClass(HttpServletRequest req,
									  HttpServletResponse res)
			throws Exception {

		List<Object> list11 = new ArrayList<Object>();

		//课程分类
		list11.add(menuService.list());

		//课程专题
		Page<MobileProject> MobileProjectPage = new Page<>();
		MobileProjectPage.setCurrent(1);
		MobileProjectPage.setSize(100);
		int projectType = 2;
		Page<MobileProject> mplist = mobileProjectService.selectMobileProjectPage(MobileProjectPage,projectType);
		list11.add(mplist.getRecords());


		//
		List<Map<String, Object>>  list1  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("id", "1");
		m11.put("name", "视频课程");
		Map<String, Object> m21 = new HashMap<String, Object>();
		m21.put("id", "2");
		m21.put("name", "音频课程");
		Map<String, Object> m31 = new HashMap<String, Object>();
		m31.put("id", "3");
		m31.put("name", "直播课程");
		Map<String, Object> m41 = new HashMap<String, Object>();
		m41.put("id", "4");
		m41.put("name", "线下课程");

		list1.add(m11);
		list1.add(m21);
		list1.add(m31);
		list1.add(m41);
		//课程类型
		list11.add(list1);
		return ResponseObject.newSuccessResponseObject(list11);
	}

	/**
	 * 列表筛选
	 */
	@RequestMapping("listScreen")
	@ResponseBody
	public ResponseObject listScreen(HttpServletRequest req,
									  HttpServletResponse res)
			throws Exception {

		List<Object> list11 = new ArrayList<Object>();

		//课程分类
		list11.add(menuService.list());

		//是否付费
		List<Map<String, Object>>  list  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("id", "1");
		m1.put("name", "免费");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("id", "0");
		m2.put("name", "付费");
		list.add(m1);
		list.add(m2);

		list11.add(list);


		//课程类型
		List<Map<String, Object>>  list1  = new ArrayList<Map<String,Object>>();
		Map<String, Object> m11 = new HashMap<String, Object>();
		m11.put("id", "1");
		m11.put("name", "视频课程");
		Map<String, Object> m21 = new HashMap<String, Object>();
		m21.put("id", "2");
		m21.put("name", "音频课程");
		Map<String, Object> m31 = new HashMap<String, Object>();
		m31.put("id", "3");
		m31.put("name", "直播课程");
		Map<String, Object> m41 = new HashMap<String, Object>();
		m41.put("id", "4");
		m41.put("name", "线下课程");

		list1.add(m11);
		list1.add(m21);
		list1.add(m31);
		list1.add(m41);
		list11.add(list1);
		//城市

		Page<OfflineCity> OfflineCityPage = new Page<>();
		OfflineCityPage.setCurrent(1);
		OfflineCityPage.setSize(5);
		List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
		if(oclist.size() == 5){
			OfflineCity oc =new OfflineCity();
			oc.setCityName("其他");
			oclist.add(oc);
		}
		for(OfflineCity city : oclist){
			String name = city.getCityName();
			city.setName(name);
		}
		list11.add(oclist);
		//直播状态
		List<Map<String, Object>>  list2  = new ArrayList<Map<String,Object>>();
		Map<String, Object> zz1 = new HashMap<String, Object>();
		zz1.put("id", "1");
		zz1.put("name", "直播中");
		Map<String, Object> zz2 = new HashMap<String, Object>();
		zz2.put("id", "2");
		zz2.put("name", "未直播");
		Map<String, Object> zz3 = new HashMap<String, Object>();
		zz3.put("id", "3");
		zz3.put("name", "精彩回放");
		list2.add(zz1);
		list2.add(zz2);
		list2.add(zz3);
		list11.add(list2);
		return ResponseObject.newSuccessResponseObject(list11);
	}

	
}
