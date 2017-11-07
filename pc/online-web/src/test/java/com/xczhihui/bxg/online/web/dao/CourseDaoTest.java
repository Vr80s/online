package com.xczhihui.bxg.online.web.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by admin on 2016/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config.xml" })
public class CourseDaoTest extends TestCase {

    @Autowired
    private  CourseDao  dao;

    public void testGetCourseTimetable() throws Exception {
//
    }

    @Test
    public void testGetGoodCriticizSum() throws Exception {
       int a= dao.getGoodCriticizSum(221);

            System.out.println(a);

    }
}