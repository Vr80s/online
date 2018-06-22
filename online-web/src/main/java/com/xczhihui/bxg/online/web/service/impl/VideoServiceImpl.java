package com.xczhihui.bxg.online.web.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.service.VideoService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.util.TextStyleUtil;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:18
 */
@Service
public class VideoServiceImpl extends OnlineBaseServiceImpl implements VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideoDao videoDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Override
    public Page<Criticize> getVideoCriticize(String videoId, Integer name, Integer pageNumber, Integer pageSize, String userId) {
        return videoDao.getUserOrCourseCriticize(videoId, name, pageNumber, pageSize, userId);
    }

    @Override
    public void updateStudyStatus(String studyStatus, String videoId, String userId) {
        videoDao.updateStudyStatus(studyStatus, videoId, userId);
    }

    @Override
    public List<Map<String, Object>> getLearnedUser(String id) {
        return videoDao.getLearnedUser(id);
    }

    @Override
    public List<Map<String, Object>> getPurchasedUser(String id) {
        return videoDao.getPurchasedUser(id);
    }

    @Override
    public String findVideosByCourseId(Integer courseId) {
        /*20170810---yuruixin*/
        CourseVo course = courseDao.findCourseOrderById(courseId);
        if (course.getType() == null && (course.getDirectId() == null || "".equals(course.getDirectId().trim()))) {
            throw new RuntimeException(String.format("视频正在来的路上，请稍后购买"));
        }
        return "购买";
    }
}
