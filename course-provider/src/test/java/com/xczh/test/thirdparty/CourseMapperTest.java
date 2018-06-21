package com.xczh.test.thirdparty;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.PagingFixedType;
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
    private IMobileBannerService mobileBannerService;

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
		
		
		System.out.println("start:"+System.currentTimeMillis());
		
        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
	    Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
	    
	    //城市  城市中的课程
	    List<Map<String, Object>> mapCourseList = mobileBannerService.realCourseList(ocl.getRecords(), PagingFixedType.PC_REAL_PAGETYPE_UP.getValue(),
	            PagingFixedType.PC_REAL_PAGETYPE_DOWN.getValue(), false);
	
	    System.out.println("end:"+System.currentTimeMillis());
	
	}
	
	@Test
	public void eee(){
		
		System.out.println("start:"+System.currentTimeMillis());
        Page<OfflineCity> OfflineCity = new Page<>();
        OfflineCity.setCurrent(1);
        OfflineCity.setSize(4);
	    Page<OfflineCity> ocl = offlineCityService.selectOfflineRecommendedCityPage(OfflineCity);
	    mobileBannerMapper.realTest1();
	    for (int i = 0; i < ocl.getRecords().size(); i++) {
	    	mobileBannerMapper.realTest2(ocl.getRecords().get(i).getCityName());
		}
	    System.out.println("end:"+System.currentTimeMillis());
	
	}
	

}