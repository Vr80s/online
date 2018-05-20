package com.xczhihui.bxg.online.web.controller.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.common.util.enums.ProjectType;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.model.MobileProject;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IOfflineCityService;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

/**
 * Description：医馆页面 creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>
 * 		email: yuruixin@ixincheng.com
 * @Date: 2018/3/28 0028 下午 4:50
 **/
@Controller
@RequestMapping(value = "/courses")
public class SchoolController extends AbstractFtlController {

	@Autowired
	private IMobileBannerService mobileBannerService;

	@Autowired
	private IMyInfoService myInfoService;

	@Autowired
	private IMobileProjectService mobileProjectService;

	@Autowired
	private IMobileHotSearchService mobileHotSearchService;

	@Autowired
	private IOfflineCityService offlineCityService;

	@Autowired
	private ICourseService courseService;

	/**
	 * 推荐页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "recommendation", method = RequestMethod.GET)
	public ModelAndView recommendation() {
		ModelAndView view = new ModelAndView("school/school_index");
		/**
		 * banner图
		 */
		view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode()));
		/**
		 * 默认搜索、热门搜索
		 */
		view.addObject("defaultSearch", mobileHotSearchService.HotSearchList(SearchType.DEFAULT_SEARCH.getCode()));
		view.addObject("hotList", mobileHotSearchService.HotSearchList(SearchType.HOT_SEARCH.getCode()));
		/**
		 * 名医推荐
		 */
		view.addObject("doctorList", myInfoService.hostInfoRec());
		/**
		 * 课程专题啦
		 */
		// 课程专题
		view.addObject("projectList", mobileProjectService.selectMobileProjectPage(ProjectType.PROJECT.getCode()));
		/**
		 * 课程分类
		 */
		List<MenuVo> listMenu = mobileProjectService.selectMenuVo();
		view.addObject("cateGoryList", listMenu);
		/**
		 * 课程列表
		 */
		view.addObject("courseTypeList",
				mobileBannerService.recommendCourseList(listMenu, PagingFixedType.PC_RECOMMENDATION.getValue()));
		return view;
	}

	/**
	 * 线下培训班
	 * 
	 * @return
	 */
	@RequestMapping(value = "real", method = RequestMethod.GET)
	public ModelAndView real() {
		ModelAndView view = new ModelAndView("school/school_real");
		// 线下课banner
		view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode()));
		// 线下培训班课程
		Page<OfflineCity> OfflineCity = new Page<>();
		OfflineCity.setCurrent(1);
		OfflineCity.setSize(4);
		Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);

		view.addObject("courseTypeList", mobileBannerService.realCourseList(ocl.getRecords(),
				PagingFixedType.REAL_PAGETYPE_UP.getValue(), PagingFixedType.REAL_PAGETYPE_DOWN.getValue()));
		// 名医推荐
		view.addObject("doctorList", myInfoService.hostInfoRec());
		return view;
	}

	/**
	 * 直播课
	 * 
	 * @return
	 */
	@RequestMapping(value = "live", method = RequestMethod.GET)
	public ModelAndView live() {
		ModelAndView view = new ModelAndView("school/school_live");

		// 直播课banner
		view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LIVE.getCode()));

		// 直播课程
		view.addObject("courseTypeList",
				mobileBannerService.liveCourseList(PagingFixedType.PC_LIVE_PAGETYPE.getValue()));

		// 名医推荐
		view.addObject("doctorList", myInfoService.hostInfoRec());
		return view;
	}

	/**
	 * 听课页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "listen", method = RequestMethod.GET)
	public ModelAndView listen() {
		ModelAndView view = new ModelAndView("school/school_video");
		// 听课banner
		view.addObject("bannerList", mobileBannerService.selectMobileBannerPage(BannerType.LISTEN.getCode()));
		// 听课
		view.addObject("courseList", courseService.listenCourseList());
		// 名医推荐
		view.addObject("doctorList", myInfoService.hostInfoRec());
		return view;
	}

	/**
	 * 检索列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(QueryConditionVo queryConditionVo) {

		ModelAndView view = new ModelAndView("school/school_list");
		// 课程列表
		if (StringUtils.isNotBlank(queryConditionVo.getQueryKey())) {
			view.addObject("courseList", mobileBannerService.searchQueryKeyCourseList(queryConditionVo));
		} else {
			view.addObject("courseList", mobileBannerService.searchCourseList(queryConditionVo));
		}
		/**
		 * 判断如果是ajax请求的话，那么就不请求下面的分类列表了。
		 * 	如果是页面跳转过来的话需要请求下呢。
		 * 目前是先全部请求吧
		 */
		// 课程分类
		view.addObject("courseMenuList",mobileProjectService.selectMenuVo());
		
		// 是否付费
		view.addObject("freeTypeEnum",PayStatus.getPayStatusList());
		
		// 课程类型
		view.addObject("courseTypeEnum",CourseType.getCourseType());
		// 直播状态
		view.addObject("liveStatusEnum",LiveStatus.getLiveStatusList());
		
		// 城市
		Page<OfflineCity> OfflineCityPage = new Page<>();
		OfflineCityPage.setCurrent(1);
		OfflineCityPage.setSize(5);
		List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
		if (oclist.size() == 5) {
			OfflineCity oc = new OfflineCity();
			oc.setCityName("其他");
			oclist.add(oc);
		}
		for (OfflineCity city : oclist) {
			String name = city.getCityName();
			city.setName(name);
		}
		view.addObject("cityList",oclist);
		return view;
	}

}
