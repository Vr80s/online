package com.xczhihui.bxg.online.web.dao;

import java.util.List;

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
    
    @Autowired
    private VideoDao videoDao;

    public void testGetCourseTimetable() throws Exception {
//
    }

    @Test
    public void testGetCoursesByCollectionId() throws Exception {
       
    	List<Integer> list = videoDao.getCoursesIdListByCollectionId(733);
    	

    	System.out.println(list.size());
    }
    
}