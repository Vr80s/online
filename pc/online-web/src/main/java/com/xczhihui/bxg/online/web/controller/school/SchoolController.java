package com.xczhihui.bxg.online.web.controller.school;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.web.controller.ftl.AbstractFtlController;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.SearchType;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
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
	/**
	 * 推荐页面
	 * @return
	 */
	@RequestMapping(value = "recommendation", method = RequestMethod.GET)
    public ModelAndView recommendation() {
		
        ModelAndView view = new ModelAndView("doctor/details");
        /**
         * banner图
         */
		view.addObject("banner",mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode()));
		/**
         * 默认搜索、热门搜索
         */
		view.addObject("defaultSearch",mobileHotSearchService.HotSearchList(SearchType.DEFAULT_SEARCH.getCode()));
		view.addObject("hotSearch",mobileHotSearchService.HotSearchList(SearchType.HOT_SEARCH.getCode()));
       
		/**
         * 名医推荐
         */
		view.addObject("doctorList",myInfoService.hostInfoRec());
		
        /**
         * 课程分类   -- 暂时固定
         */
		List<MenuVo> listMenu  = mobileProjectService.selectMenuVo();
		view.addObject("cateGoryList",listMenu);
		
        /**
         * 课程列表
         */
		int pageSize = 3;
		view.addObject("courseList",mobileBannerService.recommendCourseList(listMenu,pageSize));
        return view;
    }
	
	
	
	
	
	
}
