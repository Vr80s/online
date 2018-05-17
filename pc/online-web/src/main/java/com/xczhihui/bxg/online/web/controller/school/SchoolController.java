package com.xczhihui.bxg.online.web.controller.school;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
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

/**
 * Description：医馆页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
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
	 * @return
	 */
	@RequestMapping(value = "recommendation", method = RequestMethod.GET)
    public ModelAndView recommendation() {
		
        ModelAndView view = new ModelAndView("school/recommend");
        /**
         * banner图
         */
		view.addObject("bannerList",mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode()));
		/**
         * 默认搜索、热门搜索
         */
		view.addObject("defaultSearch",mobileHotSearchService.HotSearchList(SearchType.DEFAULT_SEARCH.getCode()));
		view.addObject("hotList",mobileHotSearchService.HotSearchList(SearchType.HOT_SEARCH.getCode()));
		/**
         * 名医推荐
         */
		view.addObject("doctorList",myInfoService.hostInfoRec());
		/**
		 * 课程专题啦
		 */
		//课程专题
		Page<MobileProject> MobileProjectPage = new Page<>();
		int projectType = 1;
		view.addObject("projectList",mobileProjectService.selectMobileProjectPage(MobileProjectPage,projectType));
		
		/**
         * 课程分类 
         */
		List<MenuVo> listMenu  = mobileProjectService.selectMenuVo();
		view.addObject("cateGoryList",listMenu);
        /**
         * 课程列表
         */
		view.addObject("courseList",mobileBannerService.recommendCourseList(listMenu,PagingFixedType.PC_RECOMMENDATION.getValue()));
        return view;
    }
	
	/**
	 * 线下培训班
	 * @return
	 */
	@RequestMapping(value = "real", method = RequestMethod.GET)
    public ModelAndView real() {
		 ModelAndView view = new ModelAndView("school/real");
		 //线下课banner
		 view.addObject("bannerList",mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode()));
		 //线下培训班课程
		 Page<OfflineCity> OfflineCity = new Page<>();
		 OfflineCity.setCurrent(1);
		 OfflineCity.setSize(4);
		 Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
		 
		 view.addObject("courseList",mobileBannerService.realCourseList(ocl.getRecords(),PagingFixedType.REAL_PAGETYPE_UP.getValue(),
				 PagingFixedType.REAL_PAGETYPE_DOWN.getValue()));
		 //名医推荐
		 view.addObject("doctorList",myInfoService.hostInfoRec());
		 return view;
	}
	
	/**
	 * 直播课
	 * @return
	 */
	@RequestMapping(value = "live", method = RequestMethod.GET)
    public ModelAndView live() {
		 ModelAndView view = new ModelAndView("school/live");
		
		 //直播课banner
		 view.addObject("bannerList",mobileBannerService.selectMobileBannerPage(BannerType.LIVE.getCode()));
		 
		 //直播课程
		 view.addObject("courseList",mobileBannerService.liveCourseList(PagingFixedType.PC_LIVE_PAGETYPE.getValue()));
		 
		 //名医推荐
		 view.addObject("doctorList",myInfoService.hostInfoRec());
		 return view;
	}
	
	/**
	 * 听课页面
	 * @return
	 */
	@RequestMapping(value = "listen", method = RequestMethod.GET)
    public ModelAndView listen() {
		 ModelAndView view = new ModelAndView("school/listen");
		 //听课banner
		 view.addObject("bannerList",mobileBannerService.selectMobileBannerPage(BannerType.LISTEN.getCode()));
		 //听课
		 view.addObject("courseList",courseService.listenCourseList());
		 //名医推荐
		 view.addObject("doctorList",myInfoService.hostInfoRec());
		 return view;
	}
	
	
}
