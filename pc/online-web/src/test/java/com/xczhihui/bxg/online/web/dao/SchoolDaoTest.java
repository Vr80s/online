package com.xczhihui.bxg.online.web.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.service.IOfflineCityService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

import junit.framework.TestCase;

/**
 * Created by yangxuan on 2018/05/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config.xml" })
public class SchoolDaoTest extends TestCase {

    
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

    public void testGetCourseTimetable() throws Exception {
//
    }
    /**
     * 测试banner
     * @throws Exception
     */
    @Test
    public void selectMobileBannerPage() throws Exception {
    	List<MobileBanner>  list = mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode());
    	System.out.println(list.size());
    }

    /**
     * 测试专题
     * @throws Exception
     */
    @Test
    public void mobileProjectService() throws Exception {
    	List<MenuVo>  list = mobileProjectService.selectMenuVo();
    	System.out.println(list.size());
    }
    
    /**
     * 测试医师
     * @throws Exception
     */
    @Test
    public void doctorList() throws Exception {
    	List<Map<String, Object>>  list = myInfoService.hostInfoRec();
    	System.out.println(list.size());
    }

    /**
     *  测试推荐页面
     * @throws Exception
     */
    @Test
    public void recommendation() throws Exception {
    	/**
         * 课程分类   -- 暂时固定
         */
		List<MenuVo> listMenu  = mobileProjectService.selectMenuVo();
        System.out.println(listMenu.size());
		/**
         * 课程列表
         */
		int pageSize = 3;
		List<Map<String, Object>>   listCourse =  mobileBannerService.recommendCourseList(listMenu,pageSize);
		System.out.println(listCourse.size());
    }
    
    /**
     *  测试线下培训班 页面
     * @throws Exception
     */
    @Test
    public void real() throws Exception {
		 Page<OfflineCity> OfflineCity = new Page<>();
		 OfflineCity.setCurrent(1);
		 OfflineCity.setSize(4);
		 Page<OfflineCity> ocl = offlineCityService.
				 selectOfflineRecommendedCityPage(OfflineCity);
		 
		 List<Map<String, Object>>   listCourse =  mobileBannerService.realCourseList(ocl.getRecords(),PagingFixedType.PC_REAL_PAGETYPE_UP.getValue(),
				 PagingFixedType.PC_REAL_PAGETYPE_DOWN.getValue());
		 
		 System.out.println(listCourse.size());
    }
    
    /**
     *  测试 -- 直播页面
     * @throws Exception
     */
    @Test
    public void live() throws Exception {

		 List<Map<String, Object>>   listCourse =
				 mobileBannerService.liveCourseList(PagingFixedType.PC_LIVE_PAGETYPE.getValue());
		 
		 System.out.println(listCourse.size());
    }
    
    /**
     *  听课测试
     * @throws Exception
     */
    @Test
    public void listen() throws Exception {

		 List<CourseLecturVo>   listCourse =  courseService.listenCourseList();
		 System.out.println(listCourse.size());
    }
    
    /**
     *  听课测试
     * @throws Exception
     */
    @Test
    public void list() throws Exception {

    	
    	QueryConditionVo  qcv = new QueryConditionVo();
    	qcv.setPageSize(1);   
    	qcv.setPageNumber(1);
    	qcv.setQueryKey("我爱你");
    	 
    	List<CourseLecturVo> listCourse = mobileBannerService.searchQueryKeyCourseList(qcv);
		System.out.println(listCourse.size());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}