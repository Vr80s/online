package com.xczhihui.course.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.solr.utils.SolrConstant;
import com.xczhihui.common.solr.utils.SolrPages;
import com.xczhihui.common.solr.utils.SolrUtils;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.QueryConditionVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class CourseSolrServiceImpl implements ICourseSolrService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CourseMapper iCourseMapper;

    private SolrUtils solrUtils;

    @Value("${solr.url}")
    private String url;
    @Value("${solr.heiht.pre}")
    private String pre;
    @Value("${solr.heiht.post}")
    private String post;
    @Value("${solr.core}")
    private String core;

    @PostConstruct
    public void initDoctorsSolr() throws IOException, SolrServerException {
        solrUtils = new SolrUtils(url,core,pre,post);
        this.initCoursesSolrData();
    }

    @Override
    public void initCoursesSolrData() throws IOException, SolrServerException {
        List<CourseSolrVO> courseSolrVOS = selectCourses4Solr();
        solrUtils.init(courseSolrVOS);
        logger.warn("课程数据初始化完成，共{}条",courseSolrVOS.size());
    }

    @Override
    public List<CourseSolrVO> selectCourses4Solr() {
        List<CourseSolrVO> courseSolrVOs = iCourseMapper.selectCourses4Solr(null);
        courseSolrVOs.forEach(courseSolrVO -> handleMedicalCoursesolrVO(courseSolrVO));
        return courseSolrVOs;
    }


    @Override
    public void deleteCoursesSolrDataById(String courseId) throws IOException, SolrServerException {
        solrUtils.deleteById(courseId);
        logger.warn("课程数据删除:{}",courseId);
    }


    @Override
    public CourseSolrVO selectDoctor4SolrById(String id) {
        List<CourseSolrVO> courseSolrVOS = iCourseMapper.selectCourses4Solr(id);
        CourseSolrVO courseSolrVO = courseSolrVOS.get(0);
        handleMedicalCoursesolrVO(courseSolrVO);
        return courseSolrVO;
    }

    /**
     * Description：处理课程数据
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/26 0026 下午 5:51
     **/
    private void handleMedicalCoursesolrVO(CourseSolrVO courseSolrVO){
        if(courseSolrVO.getRecommendSort()==null){
            courseSolrVO.setRecommendSort(0);
        }
    }

    @Override
    public Page<CourseSolrVO> selectCourseListBySolr(Page page, QueryConditionVo queryConditionVo) throws IOException, SolrServerException {
        String searchStr = getSearchStr(queryConditionVo);
        searchStr = searchStr.equals("") ? "*:*" : searchStr;
        Map<String, SolrQuery.ORDER> sortedMap = new HashMap<>();
        sortedMap.put("score", SolrQuery.ORDER.desc);
        sortedMap.put("recommendSort", SolrQuery.ORDER.desc);
        if(CourseForm.LIVE.getCode() == queryConditionVo.getType().intValue() || CourseForm.OFFLINE.getCode() == queryConditionVo.getType().intValue()){
            sortedMap.put("startTime", SolrQuery.ORDER.desc);
        }

        SolrPages Courses = solrUtils.getByPage(searchStr, page.getCurrent(), page.getSize(), CourseSolrVO.class, sortedMap);
        page.setTotal(Courses.getTotal());
        page.setRecords(Courses.getList());
        return page;
    }

    private static String getSearchStr(QueryConditionVo queryConditionVo){
        StringBuilder searchKeyWordStr = new StringBuilder();
        StringBuilder query = new StringBuilder();
        String queryKey = queryConditionVo.getQueryKey();
        if(StringUtils.isNotBlank(queryKey)){
            searchKeyWordStr.append("(");
            searchKeyWordStr.append("gradeName:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("subtitle:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("name:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("courseDetail:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("anchorDetail:"+ queryKey );
            searchKeyWordStr.append(")");
            query.append(searchKeyWordStr);
        }

        String searchMenuType;
        if(StringUtils.isNotBlank(queryConditionVo.getMenuType())){
            searchMenuType = "menuType:"+queryConditionVo.getMenuType();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchMenuType);
        }

        String searchCourseType;
        if(queryConditionVo.getType() != null){
            searchCourseType = "type:"+queryConditionVo.getType();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchCourseType);
        }

        String searchMultimediaType;
        if(queryConditionVo.getMultimediaType() != null){
            searchMultimediaType = "multimediaType:"+queryConditionVo.getMultimediaType();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchMultimediaType);
        }

        String searchLineState;
        if(queryConditionVo.getLineState() != null){
            searchLineState = "lineState:"+queryConditionVo.getLineState();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchLineState);
        }

        String searchIsFree;
        if(queryConditionVo.getIsFree() != null){
            searchIsFree = "watchState:"+queryConditionVo.getIsFree();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchIsFree);
        }

        String searchCity;
        if(queryConditionVo.getCity() != null){
            searchCity = "city:"+queryConditionVo.getCity();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchCity);
        }

        return query.toString();
    }


}
