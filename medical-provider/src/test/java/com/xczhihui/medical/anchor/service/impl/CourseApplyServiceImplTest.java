package com.xczhihui.medical.anchor.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.service.ICourseApplyService;

import test.BaseJunit4Test;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/19 0019-下午 8:11<br>
 */
public class CourseApplyServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICourseApplyService service;
    
    @Autowired
    private  CourseApplyInfoMapper courseApplyInfoMapper;

    @Test
    public void updateCourseApplyResource(String ccId) throws Exception {
        service.updateCourseApplyResource(ccId);
    }

    @Test
    public void updateCourseState() throws Exception {
        
       Integer i =  courseApplyInfoMapper.updateSaleState("2c9aec356231d035016231eb7b0b0000", "259", 1);
   
       System.out.println("i:"+i);
    }
    
}