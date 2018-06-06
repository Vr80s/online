package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.util.DateUtil;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.mapper.WatchHistoryMapper;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.util.DateDistance;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.WatchHistoryVO;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class WatchHistoryServiceImpl extends ServiceImpl<WatchHistoryMapper, WatchHistory> implements IWatchHistoryService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchHistoryServiceImpl.class);

    @Autowired
    private WatchHistoryMapper watchHistoryMapper;

    @Autowired
    private ICourseService courseServiceImpl;

    @Override
    public Page<WatchHistoryVO> selectWatchHistory(Page<WatchHistoryVO> page,
                                                   String userId) {

        List<WatchHistoryVO> records = watchHistoryMapper.selectWatchHistory(page, userId);

        for (WatchHistoryVO watchHistoryVO : records) {
            String watch = DateUtil.formatDate(watchHistoryVO.getWatchTime(), DateUtil.FORMAT_DAY_TIME);
            String current = DateUtil.formatDate(new Date(), DateUtil.FORMAT_DAY_TIME);
            String distance = DateDistance.getNewDistanceTime(watch, current);
            LOGGER.info("watch:" + watch + "========current:" + current);
            LOGGER.info("distance:" + distance);
            watchHistoryVO.setTimeDifference(distance);
        }
        return page.setRecords(records);
    }

    @Override
    public void addWatchHistory(Integer courseId, String userId, String userLecturerId, Integer collectionId) {
        watchHistoryMapper.insertWatchHistory(courseId,userId,userLecturerId,collectionId);
    }

    @Override
    public void deleteBatch(String userId) {
        watchHistoryMapper.deleteWatchHistoryByUserId(userId);
    }

    @Override
    public void addLearnRecord(Integer courseId, String userId) {
        String id = CodeUtil.getRandomUUID();
        watchHistoryMapper.insertApplyRGradeCourse(id, courseId, userId);
    }

    @Override
    public void addLookHistory(Integer courseId, String userId, Integer recordType, Integer collectionId) {
        CourseLecturVo course = courseServiceImpl.selectCurrentCourseStatus(courseId);
        if (course == null) {
            throw new RuntimeException("课程信息有误");
        }
        //增加学习记录
        if (recordType == 1) {
            if (course.getWatchState() == 1) {
                this.addLearnRecord(courseId, userId);
            }
        } else if (recordType == 2) {
            //更新观看记录
            this.addWatchHistory(courseId,userId,course.getUserLecturerId(),collectionId);
        }
    }
}
