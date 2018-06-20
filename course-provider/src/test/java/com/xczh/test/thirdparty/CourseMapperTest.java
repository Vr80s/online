package com.xczh.test.thirdparty;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.service.ICourseService;
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
	

}