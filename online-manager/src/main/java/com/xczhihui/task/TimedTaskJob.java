package com.xczhihui.task;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.MessageRemindingService;
import com.xczhihui.medical.service.DoctorService;
import com.xczhihui.medical.service.DoctorSolrService;

/**
 * @ClassName: TimedTaskJob
 * @Description: 定时恢复推荐值
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/3/14 15:47
 **/
@Component
public class TimedTaskJob {

    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageRemindingService messageRemindingService;
    @Autowired
    private DoctorSolrService doctorSolrService;
    @Autowired
    private DoctorService doctorService;
    
    
    //0 0/30 * * * ?
    @Scheduled(cron = "0 0/5 * * * ?")
    public void courSerecommendAging() {
    	//课程有效期设置
        courseService.updateDefaultSort();
        //医师有效期设置
        doctorService.updateDefaultSort();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void liveCourseMessage() throws ClientException {
        messageRemindingService.liveCourseMessageReminding();
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void offlineCourseMessage() throws ClientException {
        messageRemindingService.offlineCourseMessageReminding();
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void remindCollectionUpdate() {
        messageRemindingService.collectionUpdateRemind();
    }

    /**
     * Description：每天凌晨两点更新solr数据
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 11:27
     **/
    @Scheduled(cron = "0 0 2 * * ?")
    public void initSolrData() throws IOException, SolrServerException {
        doctorSolrService.initDoctorsSolrData();
    }
}