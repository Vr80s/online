package com.xczhihui.course.service.impl;

import static com.xczhihui.course.enums.RouteTypeEnum.COMMON_LEARNING_COURSE_DETAIL_PAGE;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.WatchHistoryMapper;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.model.WatchHistory;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.service.IWatchHistoryService;
import com.xczhihui.course.util.DateDistance;
import com.xczhihui.course.util.DateUtil;
import com.xczhihui.course.util.TextStyleUtil;
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
    private static final String APPLY_SUCCESS_TIPS = "恭喜您成功购买课程" + TextStyleUtil.LEFT_TAG + "《{0}》~" + TextStyleUtil.RIGHT_TAG;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatchHistoryServiceImpl.class);
    @Value("${weixin.course.apply.code}")
    private String weixinApplySuccessMessageCode;
    @Autowired
    private WatchHistoryMapper watchHistoryMapper;

    @Autowired
    private ICourseService courseServiceImpl;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private CourseMapper courseMapper;

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
        watchHistoryMapper.insertWatchHistory(courseId, userId, userLecturerId, collectionId);
    }

    @Override
    public void deleteBatch(String userId) {
        watchHistoryMapper.deleteWatchHistoryByUserId(userId);
    }

    @Override
    public void addLearnRecord(Integer courseId, String userId) {
        String id = CodeUtil.getRandomUUID();
        int i = watchHistoryMapper.insertApplyRGradeCourse(id, courseId, userId);
        if (i > 0) {
            saveApplySuccessMessage(courseId, userId);
        }
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
            if (collectionId != null) {  //如果是专辑，并且存在的话，做更新操作
                WatchHistory wh = watchHistoryMapper.findWatchHistoryByUserIdAndCollectionId(userId, collectionId);
                if (wh != null) {
                    wh.setCourseId(courseId);
                    wh.setCreateTime(new Date());
                    watchHistoryMapper.updateById(wh);
                    return;
                }
            } else {
                collectionId = 0;
            }
            //更新观看记录
            this.addWatchHistory(courseId, userId, course.getUserLecturerId(), collectionId);
        }
    }

    protected void saveApplySuccessMessage(Integer courseId, String userId) {
        try {
            Course course = courseMapper.selectById(courseId);
            String messageId = CodeUtil.getRandomUUID();
            String courseName = course.getGradeName();
            Date startTime = course.getStartTime();
            String content = MessageFormat.format(APPLY_SUCCESS_TIPS, courseName);
            Map<String, String> weixinParams = new HashMap<>(3);
            weixinParams.put("first", TextStyleUtil.clearStyle(content));
            weixinParams.put("keyword1", courseName);
            weixinParams.put("keyword2", startTime != null ? TimeUtil.getYearMonthDayHHmm(startTime) : "随到随学");
            weixinParams.put("remark", "");

            commonMessageService.saveMessage(
                    new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                            .buildWeb(content)
                            .buildWeixin(weixinApplySuccessMessageCode, weixinParams)
                            .detailId(String.valueOf(courseId))
                            .build(userId, COMMON_LEARNING_COURSE_DETAIL_PAGE, userId));
        } catch (Exception e) {
            LOGGER.error("报名成功时，推送消息异常: courseId: {}", courseId);
            e.printStackTrace();
        }
    }
}
