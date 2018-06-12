package com.xczhihui.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;

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
    

    @Override
    public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page) {
        List<CourseLecturVo> records = iCourseMapper.selectCoursePage(page);
        return page.setRecords(records);
    }

    @Override
    public CourseLecturVo selectCourseDetailsById(Integer courseId) {
        return iCourseMapper.selectCourseDetailsById(courseId);
    }

    @Override
    public List<CourseLecturVo> selectLearningCourseListByUserId(Integer pageSize, String id) {
        List<CourseLecturVo> listAll = iCourseMapper.selectLearningCourseListByUserId(pageSize, id);
        return listAll;
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
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId){
        return selectLecturerRecentCourse(lecturerId,false);
    }

    @Override
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId, boolean onlyFreee) {
        return iCourseMapper.selectLecturerRecentCourse(lecturerId,onlyFreee);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId) {
        return selectLecturerAllCourse(page,lecturerId,false);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId,boolean onlyFree) {
        List<CourseLecturVo> records = iCourseMapper.selectLecturerAllCourse(page, lecturerId,onlyFree);
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
    public CourseLecturVo selectCourseMiddleDetailsById(Integer courseId) {

        return iCourseMapper.selectCourseMidileDetailsById(courseId);
    }

    @Override
    public List<CourseLecturVo> selectUserConsoleCourseLiveByPage(
            Page<CourseLecturVo> page, String userId) {
//		List<CourseLecturVo> records = iCourseMapper.selectUserConsoleCourseLiveByPage(page,userId);
//		page.setRecords(records);
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
    public List<CourseLecturVo> findLiveListInfo() {
        return iCourseMapper.findLiveListInfo();
    }

    @Override
    public CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
                                                        String userId) {
        return iCourseMapper.selectUserCurrentCourseStatus(courseId, userId);
    }

    @Override
    public CourseLecturVo selectCurrentCourseStatus(Integer courseId) {
        return iCourseMapper.selectCurrentCourseStatus(courseId);
    }

    @Override
    public String selectCourseDescription(Integer type, String typeId) {
        return iCourseMapper.selectCourseDescription(type, typeId);
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
    public String getLiveCourseUrl4Wechat(String userId,String courseId) {
        Integer lineStatus = iCourseMapper.getLineStatus(courseId);
        //直播中、直播结束、即将直播
        if(lineStatus == 1 || lineStatus == 3 || lineStatus == 4){
        	/**
        	 * 增加播放记录
        	 */
        	return "/xcview/html/details.html?courseId="+courseId;
        }
        return "/xcview/html/live_play.html?my_study="+courseId;
    }

    @Override
    public Page<Map<String, Object>> selectOfflineCourseByAnchorId(String anchorId) {
        //TODO 当前需求列出所有线下课
        Page<Map<String, Object>> page = new Page<>(1, 1000);
        List<Map<String, Object>> results = iCourseMapper.selectOfflineCourseByAnchorId(page, anchorId);
        return page.setRecords(results);
    }
}
