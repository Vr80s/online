package com.xczhihui.course.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void initCourseSolrDataById(Integer id) throws IOException, SolrServerException {
        if(id != null){
            CourseSolrVO courseSolrVO = selectDoctor4SolrById(id);
            if(courseSolrVO != null){
                solrUtils.addBean(courseSolrVO);
                logger.warn("课程数据更新完成，{}",courseSolrVO.toString());
            }else{
                deleteCoursesSolrDataById(id);
                logger.warn("课程数据删除完成，{}",id);
            }
        }
    }

    @Override
    public List<CourseSolrVO> selectCourses4Solr() {
        List<CourseSolrVO> courseSolrVOs = iCourseMapper.selectCourses4Solr(null);
        courseSolrVOs.forEach(courseSolrVO -> handleMedicalCoursesolrVO(courseSolrVO));
        return courseSolrVOs;
    }

    @Override
    public void deleteCoursesSolrDataById(Integer courseId) throws IOException, SolrServerException {
        solrUtils.deleteById(courseId);
        logger.warn("课程数据删除:{}",courseId);
    }


    @Override
    public CourseSolrVO selectDoctor4SolrById(Integer id) {
        List<CourseSolrVO> courseSolrVOS = iCourseMapper.selectCourses4Solr(id);
        if(courseSolrVOS.size() == 0){
            return null;
        }
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
        if(queryConditionVo.getCourseForm()!=null && (CourseForm.LIVE.getCode() == queryConditionVo.getCourseForm() || CourseForm.OFFLINE.getCode() == queryConditionVo.getCourseForm())){
            sortedMap.put("startTime", SolrQuery.ORDER.desc);
        }
        sortedMap.put("releaseTime", SolrQuery.ORDER.desc);

        SolrPages courses = solrUtils.getByPage(searchStr, page.getCurrent(), page.getSize(), CourseSolrVO.class, sortedMap);
        page.setTotal(courses.getTotal());
        page.setRecords(courses.getList());

        handleCourses4Result(page.getRecords());
        return page;
    }

    static void handleCourses4Result(List<CourseSolrVO> courses) {
        courses.forEach(course->{
            if(course.getCourseForm().equals(CourseForm.LIVE.getCode())){
                course.setStartDateStr(handleTimeShow(course.getLineState(),course.getStartTime()));
                course.setLineState(handleLineState(course.getLineState(),course.getStartTime()));
            }
        });
    }

    /** 
     * Description：1直播中， 2预告，3直播结束
     * 当状态为预告时，若距离开播小于十分钟则返回4 即将直播
     * 当状态为预告时，若距离开播大于十分钟，小于两小时则返回5 准备直播
     *
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/27 0027 下午 2:38
     **/
    static Integer handleLineState(Integer liveStatus, Date startTime) {
        long m = startTime.getTime() - System.currentTimeMillis();
        if(liveStatus == 1||liveStatus==3){
            return liveStatus;
        }else if(m>2*60*60*1000){
            return 2;
        }else if(m>10*60*1000){
            return 5;
        }else {
            return 4;
        }
    }

    /**
     * Description：当天未结束直播课程的开始时间，显示格式：HH:mm
     *              其他所有直播课程的开始时间，显示格式：MM.dd
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/27 0027 下午 2:56
     **/
    static String handleTimeShow(Integer liveStatus, Date startTime) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(startTime);
        boolean today = day.equals(nowDay);
        if(liveStatus!=3 && today){
            sf = new SimpleDateFormat("HH:mm");
        }else{
            sf = new SimpleDateFormat("MM.dd");
        }
        return sf.format(startTime);
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
        if(queryConditionVo.getCourseForm() != null){
            searchCourseType = "courseForm:"+queryConditionVo.getCourseForm();
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
        if(StringUtils.isNotBlank(queryConditionVo.getCity())){
            searchCity = "city:"+queryConditionVo.getCity();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchCity);
        }

        return query.toString();
    }

}
