package com.xczhihui.medical.anchor.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void updateCourseApplyResource() throws Exception {
        service.updateCourseApplyResource();
    }

}