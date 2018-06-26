package com.xczhihui.course.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.QueryConditionVo;

public interface ICourseSolrService {

    void initCoursesSolrData() throws IOException, SolrServerException;

    List<CourseSolrVO> selectCourses4Solr();

    void deleteCoursesSolrDataById(String courseId) throws IOException, SolrServerException;

    CourseSolrVO selectDoctor4SolrById(String id);

    Page<CourseSolrVO> selectCourseListBySolr(Page page, QueryConditionVo queryConditionVo) throws IOException, SolrServerException;
}
