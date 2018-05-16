package com.xczhihui.bxg.online.web.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IMobileHotSearchService;
import com.xczhihui.course.service.IMobileProjectService;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;

/**
 * Created by admin on 2016/11/30.
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
	  

    public void testGetCourseTimetable() throws Exception {
//
    }

    @Test
    public void selectMobileBannerPage() throws Exception {
    	List<MobileBanner>  list = mobileBannerService.selectMobileBannerPage(BannerType.RECOMMENDATION.getCode());
    	System.out.println(list.size());
    }

    @Test
    public void mobileProjectService() throws Exception {
    	List<MenuVo>  list = mobileProjectService.selectMenuVo();
    	System.out.println(list.size());
    }

    @Test
    public void recommendation() throws Exception {
    	/**
         * 课程分类   -- 暂时固定
         */
		List<MenuVo> listMenu  = mobileProjectService.selectMenuVo();
        /**
         * 课程列表
         */
		int pageSize = 3;
		List<CourseLecturVo>  listCourse =  mobileBannerService.recommendCourseList(listMenu,pageSize);
		System.out.println(listCourse.size());
    }
    

}