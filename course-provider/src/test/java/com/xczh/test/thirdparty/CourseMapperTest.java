package com.xczh.test.thirdparty;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.BaseJunit4Test;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

/**
 * 医馆入驻测试类
 */
public class CourseMapperTest extends BaseJunit4Test {

	@Autowired
	public CourseMapper courseMapper;
	
	@Test
	public void aaaa(){
		
		Page<CourseLecturVo> page = new Page<>();
		page.setCurrent(1);
		page.setSize(5);
		//return ResponseObject.newSuccessResponseObject(courseService.selectMyFreeCourseList(page, user.getUserId()));
		
	//	Page<CourseLecturVo>
		List<CourseLecturVo> list  =courseMapper.selectMyFreeCourseList(page,"9cfa53e6cea044e3b4279a86bc3b382c");
		
		System.out.println(list.size());
	}
	

}