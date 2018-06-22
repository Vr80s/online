package com.xczh.test.thirdparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.MobileBannerMapper;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IOfflineCityService;
import com.xczhihui.course.vo.CourseLecturVo;

import test.BaseJunit4Test;

/**
 * 医馆入驻测试类
 */
public class CourseMapperTest extends BaseJunit4Test {

	@Autowired
	public CourseMapper courseMapper;
	
	@Autowired
	public ICourseService  courseService;
	
	@Autowired
	public MobileBannerMapper mobileBannerMapper;
	
	
	  @Autowired
	    private MobileBannerMapper iMobileBannerMapper;
	  
	  @Autowired
	    private  IMobileBannerService  mobileBannerService;
	  
    @Autowired
    private IOfflineCityService offlineCityService;
	
	@Test
	public void aaaa(){
		Page<CourseLecturVo> page = new Page<>();
		page.setCurrent(1);
		page.setSize(5);
		//return ResponseObject.newSuccessResponseObject(courseService.selectMyFreeCourseList(page, user.getUserId()));
	//	Page<CourseLecturVo>
//		List<CourseLecturVo> list  =courseMapper.selectMyFreeCourseList(page,"9cfa53e6cea044e3b4279a86bc3b382c");
//		System.out.println(list.size());
	}
	
	@Test
	public void bbbb(){
        Page<CourseLecturVo> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        Page<CourseLecturVo> list = courseService.selectLecturerAllCourse(page,
            		"b6df19ee5ff64d96bddcf0b0f1fd8325",3,false);
        System.out.println(list.getTotal());
		
	}
	
	@Test
	public void cccc(){
		Integer count = courseService.selectLiveCountByUserIdAndType("603606b2804a476380f78c72b460c71b",null);
        System.out.println(count);
	}
	
	@Test
	public void ddd(){
		
		
		
        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
	    Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
	    
	}
	
	@Test
	public void eee(){
		
		
        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
	    Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
	    System.out.println("start:-------------"+System.currentTimeMillis());
	    long start = System.currentTimeMillis();
	    mobileBannerMapper.realTest1();
	    for (int i = 0; i < ocl.getRecords().size(); i++) {
	    	mobileBannerMapper.realTest2(ocl.getRecords().get(i).getCityName());
		}
	    System.out.println("end:"+System.currentTimeMillis());
	    System.out.println("cha:---------------"+(System.currentTimeMillis()-start));
	    
	    
	    System.out.println("start1=================:"+System.currentTimeMillis());
	    long start1 = System.currentTimeMillis();
	    
	    List<CourseLecturVo> list = iMobileBannerMapper.realCourseList(ocl.getRecords(), 4, 4,false);
        List<Map<String, Object>> mapCourseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTj = new HashMap<String, Object>();
        List<CourseLecturVo> listqg = new ArrayList<CourseLecturVo>();
        if (list != null) {
            for (CourseLecturVo courseLecturVo : list) {
                if ("全国课程".equals(courseLecturVo.getNote())) {
                    listqg.add(courseLecturVo);
                }
            }
        }
        if (listqg.size() > 0) {
            mapTj.put("title", "全国课程");
            mapTj.put("courseList", listqg);
            mapCourseList.add(mapTj);
        }
        if(ocl.getRecords() != null){
            for (OfflineCity oc : ocl.getRecords()) {
                Map<String, Object> mapMenu = new HashMap<String, Object>();
                List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
                if (list != null) {
                    for (CourseLecturVo courseLecturVo : list) {
                        if (oc.getCityName().equals(courseLecturVo.getNote())) {
                            listMenu.add(courseLecturVo);
                        }
                    }
                }
                if (listMenu.size() > 0) {
                    mapMenu.put("title", oc.getCityName());
                    mapMenu.put("courseList", listMenu);
                    mapCourseList.add(mapMenu);
                }
            }
        }
	    System.out.println("end1:"+System.currentTimeMillis());
	    System.out.println("cha1===================:"+(System.currentTimeMillis()-start1));
	}
	

}