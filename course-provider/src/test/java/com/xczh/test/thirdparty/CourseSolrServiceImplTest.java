package com.xczh.test.thirdparty;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.QueryConditionVo;

import test.BaseJunit4Test;

/**
 * 医馆入驻测试类
 */
public class CourseSolrServiceImplTest extends BaseJunit4Test {

    @Autowired
    private ICourseSolrService iCourseSolrService;

    @Test
    public void testInit() {
        System.out.println("start");
    }

    @Test
    public void testQuery() throws IOException, SolrServerException {
        QueryConditionVo queryConditionVo = new QueryConditionVo();
        queryConditionVo.setMultimediaType(1);
        queryConditionVo.setType(1);
        queryConditionVo.setQueryKey("针灸");
        queryConditionVo.setLineState(1);
        queryConditionVo.setIsFree(1);
        queryConditionVo.setMenuType("1");

        Page<CourseSolrVO> courseSolrVOPage = iCourseSolrService.selectCourseListBySolr(new Page(1, 10), queryConditionVo);
        System.out.println(courseSolrVOPage.getTotal());
    }

}