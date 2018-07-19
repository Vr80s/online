package com.xczhihui.course.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.ShareInfoVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper iCourseMapper;
    
    @Autowired
    private CriticizeMapper  criticizeMapper;

    @Autowired
    private FocusMapper focusMapper;


    @Override
    public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page) {
        List<CourseLecturVo> records = iCourseMapper.selectCoursePage(page);
        return page.setRecords(records);
    }

    @Override
    public CourseLecturVo selectCourseDetailsById(String  userId,Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCourseDetailsById(courseId);

        if(cv == null) {
            throw new CourseException("获取课程详情有误");
        }
        
        /**
         * 这里需要判断是否购买过了
         */
        if (userId!=null) {
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(userId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId,userId);
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程
                if (falg > 0) {
                    cv.setWatchState(2);
                }
                //如果是免费的  判断是否学习过
            } else if (cv.getWatchState() == 1) { // 免费课程
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }
        
        return cv;
    }

    @Override
    public List<Map<String, Object>> selectLearningCourseListByUserId(Integer pageSize, String id) {
        List<CourseLecturVo> listAll = iCourseMapper.selectLearningCourseListByUserId(pageSize, id);
       
        List<Map<String, Object>> mapCourseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTj = new HashMap<String, Object>();
        Map<String, Object> mapNw = new HashMap<String, Object>();
        List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
        List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
        for (CourseLecturVo courseLecturVo : listAll) {
            if ("我的课程".equals(courseLecturVo.getNote())) {
                listTj.add(courseLecturVo);
            }
            if ("已结束课程".equals(courseLecturVo.getNote())) {
                listNw.add(courseLecturVo);
            }
        }
        mapTj.put("title", "我的课程");
        mapTj.put("courseList", listTj);

        mapNw.put("title", "已结束课程");
        mapNw.put("courseList", listNw);

        mapCourseList.add(mapTj);
        mapCourseList.add(mapNw);
        
        return mapCourseList;
    }

    @Override
    public Integer selectMyFreeCourseListCount(String id) {
        return iCourseMapper.selectMyFreeCourseListCount(id);
    }

    @Override
    public Page<CourseLecturVo> selectMyPurchasedCourseList(Page<CourseLecturVo> page, String id) {
        List<CourseLecturVo> records = iCourseMapper.selectMyPurchasedCourseList(page, id);

        return page.setRecords(records);
    }

    @Override
    public List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId) {
        List<CourseLecturVo> courses = iCourseMapper.selectCoursesByCollectionId(collectionId);
        return courses;
    }

    @Override
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId) {
        return selectLecturerRecentCourse(lecturerId, false);
    }

    @Override
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId, boolean onlyFreee) {
        return iCourseMapper.selectLecturerRecentCourse(lecturerId, onlyFreee);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId) {
        return selectLecturerAllCourseByType(page, lecturerId, null, false);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourseByType(Page<CourseLecturVo> page, String lecturerId,
                                                              Integer type, boolean onlyFree) {
        List<CourseLecturVo> records = iCourseMapper.selectLecturerAllCourseByType(page, lecturerId, type, onlyFree);
        return page.setRecords(records);
    }

    @Override
    public List<CourseLecturVo> selectUserConsoleCourse(String id) {
        return iCourseMapper.selectUserConsoleCourse(id);
    }

    @Override
    public Page<CourseLecturVo> selectMenuTypeAndRandCourse(Page<CourseLecturVo> page, Integer menuId) {
        List<CourseLecturVo> records = iCourseMapper.selectMenuTypeAndRandCourse(page, menuId);
        return page.setRecords(records);
    }

    @Override
    public Page<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page, String userId, Integer courseForm, Integer multimediaType) {
        List<CourseLecturVo> records = iCourseMapper.selectAppCourseApplyPage(page, userId, courseForm, multimediaType);
        page.setRecords(records);
        return page.setRecords(records);
    }

    @Override
    public CourseLecturVo selectCourseMiddleDetailsById(String  userId,Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCourseMidileDetailsById(courseId);
        if(cv == null) {
            throw new CourseException("获取课程详情有误");
        }
        /**
         * 这里需要判断是否购买过了
         */
        if (userId!=null) {
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(userId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId,userId);
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程
                if (falg > 0) {
                    cv.setWatchState(2);
                }
                //如果是免费的  判断是否学习过
            } else if (cv.getWatchState() == 1) { // 免费课程
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }
        return cv;
    }

    @Override
    public List<CourseLecturVo> selectUserConsoleCourseLiveByPage(
            Page<CourseLecturVo> page, String userId) {
        return iCourseMapper.selectUserConsoleCourseLiveByPage(page, userId);
    }

    @Override
    public List<CourseLecturVo> listenCourseList(boolean onlyFree) {
        return iCourseMapper.listenCourseList(onlyFree);
    }

    @Override
    public List<CourseLecturVo> listenCourseList() {
        return listenCourseList(false);
    }

    @Override
    public CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
                                                        String userId) {
        CourseLecturVo cv = iCourseMapper.selectUserCurrentCourseStatus(courseId, userId);
        return cv;
    }

    @Override
    public CourseLecturVo selectCurrentCourseStatus(Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCurrentCourseStatus(courseId);
        return cv;
    }

    @Override
    public String selectCourseDescription(Integer type, String typeId) {

        return XzStringUtils.formatA(iCourseMapper.selectCourseDescription(type, typeId));
    }

    @Override
    public Page<CourseLecturVo> myCourseType(Page<CourseLecturVo> page, String userId, Integer type) {
        List<CourseLecturVo> list = iCourseMapper.myCourseType(page, userId, type);
        return page.setRecords(list);
    }

    @Override
    public List<Map<String, Object>> getLiveStatusList() {
        List<Map<String, Object>> list = LiveStatus.getLiveStatusList();
        return list;
    }

    @Override
    public List<Map<String, Object>> getPayStatusList() {
        List<Map<String, Object>> list = PayStatus.getPayStatusList();
        return list;
    }


    @Override
    public List<CourseLecturVo> selectCourseByLearndCount(Page<CourseLecturVo> page, Integer type) {
        return iCourseMapper.selectCourseByLearndCount(page, type);
    }

    @Override
    public List<Course> getAllCourseByStatus() {
        List<Course> list = iCourseMapper.getAllCourseByStatus();
        return list;
    }

    @Override
    public List<CourseLecturVo> selectRecommendSortAndRandCourse(Page<CourseLecturVo> page) {

        return iCourseMapper.selectRecommendSortAndRandCourse(page);
    }

    @Override
    public List<Course> findByIds(List<Integer> ids) {
        List<Course> courses = new ArrayList<>();
        for (Integer id : ids) {
            courses.add(iCourseMapper.selectById(id));
        }
        return courses;
    }

    @Override
    public List<Map<String, Object>> findByMenuIdExcludeId(Integer menuId, Integer courseId) {
        return iCourseMapper.selectByMenuIdExcludeSelf(menuId, courseId);
    }

    @Override
    public CourseLecturVo selectCourseStatusDeleteUserLecturerId(Integer courseId) {
        return iCourseMapper.selectCourseStatusDeleteUserLecturerId(courseId);
    }

    @Override
    @Transactional(readOnly = false)
    public String getLiveCourseUrl4Wechat(String userId, String courseId) {
        Integer lineStatus = iCourseMapper.getLineStatus(courseId);
        //直播中、直播结束、即将直播
        if (lineStatus == 1 || lineStatus == 3 || lineStatus == 4) {
            /**
             * 增加播放记录
             */
            return "/xcview/html/details.html?courseId=" + courseId;
        }
        return "/xcview/html/live_play.html?my_study=" + courseId;
    }

    @Override
    public Page<Map<String, Object>> selectOfflineCourseByAnchorId(String anchorId) {
        //TODO 当前需求列出所有线下课
        Page<Map<String, Object>> page = new Page<>(1, 1000);
        List<Map<String, Object>> results = iCourseMapper.selectOfflineCourseByAnchorId(page, anchorId);
        return page.setRecords(results);
    }

    @Override
    public Integer selectLiveCountByUserIdAndType(String userId, Integer type) {
        return iCourseMapper.selectLiveCountByUserIdAndType(userId, type);
    }

    @Override
    public List<Map<String, Object>> list(Integer type, String userId) {
        return iCourseMapper.selectCourseByType(type, userId);
    }

    @Override
    public ShareInfoVo selectShareInfoByType(Integer type, String id) {

        return iCourseMapper.selectShareInfoByType(type, id);
    }

    @Override
    public Course findSimpleInfoById(int id) {
        return iCourseMapper.findSimpleInfoById(id);
    }

    @Override
    public List<Map<String, Object>> doctorCourseList(String lecturerId,String userId) {

        List<Map<String, Object>> alllist = new ArrayList<Map<String, Object>>();

        List<CourseLecturVo> records = selectTeachingCoursesByUserId(new Page<CourseLecturVo>(1,4),lecturerId, userId);
        
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("text", "跟师直播");
        map1.put("code", CourseType.APPRENTICE.getId());
        map1.put("courseList",records);
        alllist.add(map1);

        List<CourseLecturVo> recordsLive = iCourseMapper.selectLecturerAllCourseByType(new Page<CourseLecturVo>(1, 6), userId, CourseType.LIVE.getId(), false);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "直播课程");
        map.put("code", CourseType.LIVE.getId());
        map.put("courseList",recordsLive);
        alllist.add(map);

        return alllist;
    }

    @Override
   public List<CourseLecturVo> selectTeachingCoursesByUserId(Page<CourseLecturVo> page, String lecturerId, String userId) {
        //userId为医师的用户id
        List<CourseLecturVo> courses = iCourseMapper.selectTeachingCourse(page, lecturerId, userId);
        return courses;
    }

    @Override
    public boolean selectQualification4TeachingCourse(String userId, Integer courseId) {
        int count = iCourseMapper.selectQualification4TeachingCourse(userId, courseId);
        return count>0;
    }

    @Override
    public CourseLecturVo selectDoctorLiveRoomRecentCourse(String userId, boolean onlyFreee) {
        return iCourseMapper.selectDoctorLiveRoomRecentCourse(userId, onlyFreee);
    }
    
    
    /**
     * 查看当前用户是否关注了主播以及是否购买了这个课程
     * @param cv
     * @param accountIdOpt
     * @param courseId
     * @return
     */
    private CourseLecturVo assignFocusAndWatchState(CourseLecturVo cv,Optional<String> accountIdOpt,Integer courseId) {
        /**
         * 这里需要判断是否购买过了
         */
        if (accountIdOpt.isPresent()) {
            String accountId = accountIdOpt.get();
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(accountId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId,accountId);
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程
                if (falg > 0) {
                    cv.setWatchState(2);
                }
                //如果是免费的  判断是否学习过
            } else if (cv.getWatchState() == 1) { // 免费课程
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }
        return cv;
    }
}
