package com.xczhihui.task;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.course.service.MessageRemindingService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.service.DoctorService;
import com.xczhihui.medical.service.HospitalService;

/**
 * @ClassName: TimedTaskJob
 * @Description: 定时任务
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/3/14 15:47
 **/
@Component
public class TimedTaskJob {

    private Logger logger = LoggerFactory.getLogger(TimedTaskJob.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageRemindingService messageRemindingService;
    @Autowired
    private ICourseSolrService courseSolrService;
    @Autowired
    private IMedicalDoctorSolrService medicalDoctorSolrService;
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private HospitalService hospitalService;

    /**
     * Description：课程推荐值更新
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/27 0027 下午 3:29
     **/
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void courseRecommendAging() {
        List<Integer> ids = courseService.updateDefaultSort();
        ids.forEach(id -> {
            try {
                courseSolrService.initCourseSolrDataById(id);
            } catch (Exception e) {
                logger.error("课程信息同步至solr有误,{}", id);
                logger.error(e.getMessage());
            }
        });
    }

    /**
     * Description：医师推荐值更新
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void doctorRecommendAging() {
        List<String> ids = doctorService.updateDefaultSort();
        ids.forEach(id -> {
            try {
                medicalDoctorSolrService.initDoctorsSolrDataById(id);
            } catch (Exception e) {
                logger.error("医师信息同步至solr有误,{}", id);
                logger.error(e.getMessage());
            }
        });
    }
    
    /**
     * Description：医馆推荐值更新
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void hospitalRecommendAging() {
        
        hospitalService.updateDefaultSort();
    }
    
    

    @Scheduled(cron = "0 0/1 * * * ?")
    public void liveCourseMessage() throws ClientException {
        messageRemindingService.liveCourseMessageReminding();
    }

//    @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void offlineCourseMessage() throws ClientException {
        messageRemindingService.offlineCourseMessageReminding();
    }

//    @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void remindCollectionUpdate() {
        messageRemindingService.collectionUpdateRemind();
    }

    /**
     * Description：每天凌晨两点更新solr数据
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 11:27
     **/
    @Scheduled(cron = "0 0 2 * * ?")
    public void initSolrData() throws IOException, SolrServerException {
        medicalDoctorSolrService.initDoctorsSolrData();
        courseSolrService.initCoursesSolrData();
    }
}