package com.xczhihui.course.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.ShareInfoVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface CourseMapper extends BaseMapper<Course> {

    //关注数
    List<CourseLecturVo> selectCoursePage(@Param("page") Page<CourseLecturVo> page);

    //粉丝数
    CourseLecturVo selectCourseById(@Param("courseId") Integer courseId);

    CourseLecturVo selectCourseDetailsById(@Param("courseId") Integer courseId);

    List<CourseLecturVo> selectLearningCourseListByUserId(@Param("pageSize") Integer pageSize, @Param("userId") String userId);

    Integer selectMyFreeCourseListCount(@Param("userId") String userId);

    List<CourseLecturVo> selectMyPurchasedCourseList(@Param("page") Page<CourseLecturVo> page, @Param("userId") String id);

    List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId);

    CourseLecturVo selectLecturerRecentCourse(@Param("userId") String userId, @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> selectLecturerAllCourseByType(@Param("page") Page<CourseLecturVo> page,
                                                       @Param("userId") String id, @Param("type") Integer type,
                                                       @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> selectUserConsoleCourse(@Param("userId") String userId);

    List<CourseLecturVo> selectMenuTypeAndRandCourse(@Param("page") Page<CourseLecturVo> page, @Param("courseId") Integer courseId);

    /**
     * Description：我的课程  包含审批未审批的
     *
     * @param page
     * @param userId
     * @param courseFrom
     * @param multimediaType
     * @return List<CourseLecturVo>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<CourseLecturVo> selectAppCourseApplyPage(@Param("page") Page<CourseLecturVo> page,
                                                  @Param("userId") String userId, @Param("courseForm") Integer courseForm, @Param("multimediaType") Integer multimediaType);

    CourseLecturVo selectCourseMidileDetailsById(Integer courseId);


    List<CourseLecturVo> selectUserConsoleCourseLiveByPage(@Param("page") Page<CourseLecturVo> page, @Param("userId") String userId);

    List<CourseLecturVo> listenCourseList(@Param("onlyFree") boolean onlyFree);

    /**
     * Description：用户当前课程状态   User current course status. 用户登录了
     *
     * @param courseId
     * @param id
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectUserCurrentCourseStatus(@Param("courseId") Integer courseId, @Param("userId") String userId);

    /**
     * Description：课程状态   User current course status. 用户没有登录
     *
     * @param courseId
     * @return CourseLecturVo
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    CourseLecturVo selectCurrentCourseStatus(@Param("courseId") Integer courseId);

    /**
     * Description：根据type类型查找不同类型的富文本内容
     *
     * @param type
     * @param typeId
     * @return String
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    String selectCourseDescription(@Param("type") Integer type, @Param("typeId") String typeId);

    List<Integer> selectCourseIdByCollectionId(Integer courseId);

    List<CourseLecturVo> myCourseType(@Param("page") Page<CourseLecturVo> page, @Param("userId") String userId,
                                      @Param("type") Integer type);

    /**
     * 随机学习人数最高的
     *
     * @param type null 所有推荐     1 直播  2 视频  3 线下课
     * @return
     */
    List<CourseLecturVo> selectCourseByLearndCount(@Param("page") Page<CourseLecturVo> page,
                                                   @Param("type") Integer type);

    /**
     * 查询4条该课程分类下的其他课程
     *
     * @param id     id
     * @param menuId menuId
     * @return
     */
    @Select({"select oc.id, oc.grade_name as gradeName, oc.learnd_count as learndCount, ca.name, oc.current_price * 10 as currentPrice," +
            " oc.smallimg_path as smallImgPath" +
            " from oe_course oc inner join course_anchor as ca on oc.user_lecturer_id = ca.user_id" +
            " where oc.menu_id = #{menuId} and oc.id != #{id} and oc.status = 1 and oc.is_delete = 0" +
            " order by oc.release_time desc " +
            " limit 4"})
    List<Map<String, Object>> selectByMenuIdExcludeSelf(@Param("menuId") Integer menuId, @Param("id") Integer id);


    CourseLecturVo selectCourseStatusDeleteUserLecturerId(@Param("courseId") Integer courseId);

    @Select("SELECT IF( oc.live_status = 2,\n" +
            "    IF((DATE_SUB(NOW(), INTERVAL 30 MINUTE) < oc.start_time AND NOW() > oc.start_time) OR (DATE_ADD(NOW(), INTERVAL 10 MINUTE) >= oc.start_time AND NOW() < oc.start_time),4,\n" +
            "IF(DATE_ADD(NOW(), INTERVAL 2 HOUR) >= oc.start_time AND NOW() < oc.start_time,5,oc.live_status)),oc.live_status) AS lineState FROM oe_course oc " +
            " WHERE oc.id = #{courseId}")
    Integer getLineStatus(String courseId);

    /**
     * 查询主播线下课
     *
     * @param page     分页参数
     * @param anchorId 主播id
     * @return
     */
    @Select({"select oc.id, oc.grade_name as courseName " +
            " from oe_course oc" +
            " where oc.user_lecturer_id = #{anchorId} and type = 3 and is_delete = 0 " +
            " order by oc.create_time desc"})
    List<Map<String, Object>> selectOfflineCourseByAnchorId(Page<Map<String, Object>> page, @Param("anchorId") String anchorId);

    Integer selectLiveCountByUserIdAndType(@Param("userId") String userId, @Param("type") Integer type);

    /**
     * 查询医师所有的课程
     *
     * @param type     类型
     * @param anchorId 主播id
     * @return
     */
    @Select({"select oc.id, oc.grade_name as courseName " +
            " from oe_course oc" +
            " where oc.user_lecturer_id = #{anchorId} and (#{type} is null OR type = #{type}) and is_delete = 0 and status = 1" +
            " order by oc.create_time desc"})
    List<Map<String, Object>> selectCourseByType(@Param("type") Integer type, @Param("anchorId") String anchorId);


    List<CourseSolrVO> selectCourses4Solr(@Param("courseId") Integer courseId);

    ShareInfoVo selectShareInfoByType(@Param("type") Integer type,
                                      @Param("shareId") String shareId);

    /**
     * 查询课程的简单信息
     *
     * @param id id
     * @return
     */
    @Select({"select id, grade_name as gradeName, status from oe_course where id = #{id}"})
    Course findSimpleInfoById(@Param("id") int id);


    List<CourseLecturVo> selectRecommendSortAndRandCourse(@Param("page") Page<CourseLecturVo> page);

    /**
     * 获取所有上架课程
     *
     * @return
     */
    @Select({"select id, grade_name as gradeName, subtitle as subtitle, smallimg_path as smallImgPath, user_lecturer_id as userLecturerId, release_time as releaseTime" +
            " from oe_course where status = 1 and is_delete = 0"})
    List<Course> getAllCourseByStatus();

    /**
     * 查询师承课程
     *
     * @param userId   医师用户id
     * @param page     分页参数
     * @param onlyFree 是否仅查询免费
     * @return
     */
    List<CourseLecturVo> selectTeachingCourse(@Param("userId") String userId, @Param("page") Page<CourseLecturVo> page,
                                              @Param("onlyFree") boolean onlyFree);

    @Select("SELECT COUNT(*) FROM `course_teaching` ct WHERE ct.`course_id`=#{courseId} AND ct.`user_id`=#{accountId}")
    int selectQualification4TeachingCourse(@Param("accountId") String accountId, @Param("courseId") Integer courseId);

    CourseLecturVo selectDoctorLiveRoomRecentCourse(@Param("userId")String userId, @Param("onlyFree") boolean onlyFreee);
}