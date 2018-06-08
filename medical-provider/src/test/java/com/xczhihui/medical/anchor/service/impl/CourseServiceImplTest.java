package com.xczhihui.medical.anchor.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

import test.BaseJunit4Test;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.c	om <br>
 * Create Time:  2018/3/19 0019-下午 8:11<br>
 */
public class CourseServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICourseApplyService service;
    
    
    @Autowired
    private CourseApplyResourceMapper courseApplyResourceMapper;
    
    @Test
    public void updateCourseApplyResource() throws Exception {
        service.updateCourseApplyResource();
    }
    
    @Test
	public void TestSelectCourseListByVideoRecourse(){

		List<Integer> list  =courseApplyResourceMapper.selectCourseListByVideoRecourse("123");
		for (Integer integer : list) {
			System.out.println(integer);
		}
		System.out.println(list.size());
		
		//courseApplyResourceMapper.updateBatchCourseLength("6",list);
	}
    

}