package com.xczhihui.medical.anchor.service.impl;

import com.xczhihui.medical.anchor.service.ICourseOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

public class CourseOrderServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICourseOrderService service;

    @Test
    public void testList(){

        List<UserCoinIncreaseVO> userCoinIncreaseVOS =
                service.list("2c9aec35601bb5ae01601c72d49d0000");

        Assert.assertNotNull(userCoinIncreaseVOS);
    }


}