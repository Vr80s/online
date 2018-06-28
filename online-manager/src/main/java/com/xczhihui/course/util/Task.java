package com.xczhihui.course.util;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.common.util.enums.PlayBackType;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.vhall.VhallUtil;

public class Task extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(TimerTask.class);
    private CourseService courseService;

    private String videoId;

    private Integer courseId;

    public Task(String videoId, Integer courseId, CourseService courseService) {
        this.videoId = videoId;
        this.courseId = courseId;
        this.courseService = courseService;
    }

    /**
     * 直接结束后30秒执行这个方法判断是否生成回放，如果没有生成回放，就下架好了
     *
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        logger.info("定时时间到了：判断回放是否生产成功");
        logger.info(courseId + "courseId  {}{}{}{}  videoId：" + videoId);
        Integer result = VhallUtil.recordList(videoId);
        System.out.println("回放状态：" + PlayBackType.getTypeText(result));
        //回放生产成功
        courseService.updatePlaybackState(courseId, result);
        try {
            courseService.initCourseSolrDataById(courseId);
        } catch (Exception e) {
            logger.error("直播状态同步至solr失败：{}", courseId);
        }
    }

}