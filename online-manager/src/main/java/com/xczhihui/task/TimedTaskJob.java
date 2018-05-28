package com.xczhihui.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.MessageRemindingService;

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

    public void courSerecommendAging() {
        System.out.println("work done----------" + new Date());
        courseService.updateDefaultSort();

    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void liveCourseMessage() throws ClientException {
        messageRemindingService.liveCourseMessageReminding();
    }

    //    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0 8 * * ?")
    public void offlineCourseMessage() throws ClientException {
        messageRemindingService.offlineCourseMessageReminding();
    }

    //    @Scheduled(cron = "0 8 0/1 * * ?"
    @Scheduled(cron = "0 0 8 * * ?")
    public void remindCollectionUpdate() {
        messageRemindingService.collectionUpdateRemind();
    }
}