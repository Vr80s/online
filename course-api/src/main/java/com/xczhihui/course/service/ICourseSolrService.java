package com.xczhihui.course.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.QueryConditionVo;

public interface ICourseSolrService {

    void initCoursesSolrData() throws IOException, SolrServerException;

    void initCourseSolrDataById(Integer id) throws IOException, SolrServerException;

    List<CourseSolrVO> selectCourses4Solr();

    void deleteCoursesSolrDataById(Integer courseId) throws IOException, SolrServerException;

    CourseSolrVO selectDoctor4SolrById(Integer id);

    Page<CourseSolrVO> selectCourseListBySolr(Page page, QueryConditionVo queryConditionVo) throws IOException, SolrServerException;
}
