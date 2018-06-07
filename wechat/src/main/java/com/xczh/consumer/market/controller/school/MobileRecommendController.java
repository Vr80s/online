package com.xczh.consumer.market.controller.school;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.IOSVersionInterceptor;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseVo;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.common.util.enums.ProjectType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.MobileProject;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

/**
 * 推荐控制器 ClassName: MobileRecommendController.java <br>
 * Description: <br>
 * Create by: name：wangyishuai <br>
 * email: 15210815880@163.com <br>
 * Create Time: 2018年1月16日<br>
 */
@Controller
@RequestMapping("/xczh/recommend")
public class MobileRecommendController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MobileRecommendController.class);

    @Autowired
    private OLCourseServiceI wxcpCourseService;

    @Autowired
    private IMobileBannerService mobileBannerService;

    @Autowired
    private IMobileProjectService mobileProjectService;

    @Autowired
    private IMyInfoService myInfoService;

    /**
     * 推荐  包含的信息:banner  ,推荐导航, 名师推荐
     */
    @RequestMapping("recommendTop")
    @ResponseBody
    public ResponseObject recommendTop() throws Exception {
//		Integer current = 1;
//		Integer size = 100;
        Map<String, Object> mapAll = new HashMap<String, Object>();
        //课程banner
        Page<MobileBanner> MobileBannerPage = new Page<>();
        MobileBannerPage.setRecords(mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode(),IOSVersionInterceptor.onlyThread.get()));
        mapAll.put("banner", MobileBannerPage);

        //课程专题
        Page<MobileProject> MobileProjectPage = new Page<>();
        List<MobileProject> listMobileProject = mobileProjectService.
                selectMobileProjectPage(ProjectType.PROJECT.getCode());
        mapAll.put("project", MobileProjectPage.setRecords(listMobileProject));
        //名医推荐
        mapAll.put("doctorList", myInfoService.hostInfoRec());
        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    /**
     * 推荐      包含的下面的课程 :精品课程, 最新课程, 分类课程
     */
    @RequestMapping("recommendCourse")
    @ResponseBody
    public ResponseObject recommendBunch(HttpServletRequest req,
                                         HttpServletResponse res)
            throws Exception {
        /**
         * 精品课程 按照推荐值来排序。
         * 最新课程 课程的时间排序
         * 针灸课程
         * 古书经典
         */
        List<MenuVo> listMenu = mobileProjectService.selectMenuVo();
        List<Map<String, Object>> mapCourseList = mobileBannerService.recommendCourseList(listMenu, PagingFixedType.RECOMMENDATION_PAGETYPE_UP.getValue(),
                PagingFixedType.RECOMMENDATION_PAGETYPE_DOWN.getValue(), IOSVersionInterceptor.onlyThread.get());
        return ResponseObject.newSuccessResponseObject(mapCourseList);
    }

    /*****************************************
     *
     * 		检索管理
     *
     * **************************************
     */
    /**
     * 搜索所有的课程
     * 可通过关键字  -- 关键字是全文匹配
     * 上面哪个是
     * 分类搜索
     * 是否付费
     * 类型
     * 城市
     */
//    @RequestMapping("queryAllCourse")
//    @ResponseBody
//    public ResponseObject queryAllCourse(String menuType, 
//    		String city,String isFree,
//    		Integer lineState, Integer courseType,
//    		String queryKey,
//    		Integer pageNumber, Integer pageSize)
//            throws Exception {
//        List<CourseVo> list = wxcpCourseService.queryAllCourse(menuType, lineState, courseType, isFree, city, queryKey, pageNumber, pageSize,IOSVersionInterceptor.onlyThread.get());
//        return ResponseObject.newSuccessResponseObject(list);
//    }
    
	  @RequestMapping("queryAllCourse")
	  @ResponseBody
	  public ResponseObject queryAllCourse(
			QueryConditionVo queryConditionVo,
	  		Integer pageNumber, Integer pageSize)
	          throws Exception {
	      
		  pageNumber = pageNumber == null ? 1 : pageNumber;
		  pageSize = pageSize == null ? 10 : pageSize;
		  
	      if(queryConditionVo.getMenuType()!=null && "0".equals(queryConditionVo.getMenuType())) {
	         queryConditionVo.setMenuType(null);
	      }
	      if(queryConditionVo.getLineState()!=null && queryConditionVo.getLineState() == 0) {
	        queryConditionVo.setLineState(null);
	      }
		  
	      Page<CourseLecturVo> page = new Page<CourseLecturVo>(pageNumber, pageSize);
	       // 课程列表
	      if (StringUtils.isNotBlank(queryConditionVo.getQueryKey())) {
	            queryConditionVo.setQueryKey("%" + queryConditionVo.getQueryKey() + "%");
	            page= mobileBannerService.searchQueryKeyCourseList(page, queryConditionVo,IOSVersionInterceptor.onlyThread.get());
	      } else {
	    	    page =mobileBannerService.searchCourseList(page, queryConditionVo, IOSVersionInterceptor.onlyThread.get());
	      }
	      return ResponseObject.newSuccessResponseObject(page.getRecords());
	  }
    

    /**
     * banner点击量
     */
    @RequestMapping("clickBanner")
    @ResponseBody
    public ResponseObject clickBanner(HttpServletRequest req,
                                      HttpServletResponse res, @RequestParam("id") String id)
            throws Exception {

        mobileBannerService.addClickNum(id);

        return ResponseObject.newSuccessResponseObject("点击量+1");
    }
}
