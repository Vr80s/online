package com.xczhihui.course.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.ShareInfoVo;

/**
 * 
* @ClassName: CourseMapper
* @Description: 课程dao类
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月20日
*
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

    List<CourseLecturVo> selectTeachingCourse(@Param("page") Page<CourseLecturVo> page, @Param("lecturerId") String lecturerId, @Param("userId") String userId);

    List<CourseLecturVo> selectUserConsoleCourse(@Param("userId") String userId);

    List<CourseLecturVo> selectMenuTypeAndRandCourse(@Param("page") Page<CourseLecturVo> page, @Param("courseId") Integer courseId);

    /**
     * Description：我的课程  包含审批未审批的
     *
     * @param page
     * @param userId
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

    @Select("SELECT COUNT(*) FROM `course_teaching` ct WHERE ct.`course_id`=#{courseId} AND ct.`user_id`=#{accountId}")
    int selectQualification4TeachingCourse(@Param("accountId") String accountId, @Param("courseId") Integer courseId);

    CourseLecturVo selectDoctorLiveRoomRecentCourse(@Param("userId")String userId, @Param("onlyFree") boolean onlyFreee);



    /**
     * 回放状态
     * @param documentId 回放id
     * @param status     回放状态
     */
    @Update({" update oe_course set play_back_type = #{status} where record_id = #{recordId} "})
    void updatePlayBackStatusByRecordId(@Param("recordId") String recordId, @Param("status") Integer status);
    
    
    /**
     * 通过回放id查找渠道id
     * @param documentId 回放id
     * @param status     回放状态
     */
    @Select({"select  channel_id as channelId,is_record record,id as id from oe_course where record_id = #{recordId}"})
    CourseLecturVo selectCourseByRecordId(@Param("recordId") String recordId);

    @Select({"SELECT max(record_count) FROM oe_live_time_record WHERE live_id = #{liveId}"})
    Integer maxRecordCount(@Param("liveId") String liveId);

    @Insert({"insert into oe_live_time_record (course_id,live_id, start_time, end_time, record_count) values(#{courseId}, #{liveId}, #{startTime}, #{endTime}, #{recordCount})"})
    void insertRecordLiveTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                              @Param("courseId") Integer courseId, @Param("liveId") String liveId, @Param("recordCount") Integer recordCount);

    /**
     * 获取
     * @param directId
     * @return
     */
    @Select({"select live_status from oe_course where direct_id = #{directId}"})
    Integer selectCourseLiveStatusByDirectId(@Param("directId") String directId);

    
    /**
     * 
     * <p>Title: selectTheirCollection</p>  
     * <p>Description: 如果是付费的音频或者视频判断其是否包含在某个付费的专辑中</p>  
     * @param courseId
     * @return
     */
    @Select({" select oc.grade_name as gradeName,oc.id,oc.smallimg_path as smallImgPath  from  collection_course  cc inner join  oe_course oc on cc.collection_id= oc.id " + 
    		"   where cc.course_id = 582 and oc.is_free = 0 order by cc.create_time limit 0,1 "})
	Map<String,Object> selectTheirCollection(@Param("directId")Integer courseId);
}