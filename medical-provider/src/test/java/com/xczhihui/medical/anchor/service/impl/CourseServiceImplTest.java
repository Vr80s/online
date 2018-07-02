package com.xczhihui.medical.anchor.service.impl;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(CourseServiceImplTest.class);
    @Autowired
    private ICourseApplyService service;


    @Autowired
    private CourseApplyResourceMapper courseApplyResourceMapper;

    @Test
    public void updateCourseApplyResource() throws Exception {
        service.updateCourseApplyResource();
    }

    @Test
    public void TestSelectCourseListByVideoRecourse() {

        List<Integer> list = courseApplyResourceMapper.selectCourseListByVideoRecourse("123");
    }


}