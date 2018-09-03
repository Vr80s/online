package com.xczhihui.course.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CollectionCoursesVo;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.CourseSolrVO;
import com.xczhihui.course.vo.ShareInfoVo;

/**
 * @author yangxuan
 * @ClassName: CourseMapper
 * @Description: 课程dao类
 * @email yangxuan@ixincheng.com
 * @date 2018年8月20日
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

    List<CollectionCoursesVo> selectCoursesByCollectionId(Integer collectionId);

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

    CourseLecturVo selectDoctorLiveRoomRecentCourse(@Param("userId") String userId, @Param("onlyFree") boolean onlyFreee);


    /**
     * 回放状态
     *
     * @param documentId 回放id
     * @param status     回放状态
     */
    @Update({" update oe_course set play_back_type = #{status} where record_id = #{recordId} "})
    void updatePlayBackStatusByRecordId(@Param("recordId") String recordId, @Param("status") Integer status);


    /**
     * 通过回放id查找渠道id
     *
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
     *
     * @param directId
     * @return
     */
    @Select({"select live_status from oe_course where direct_id = #{directId}"})
    Integer selectCourseLiveStatusByDirectId(@Param("directId") String directId);

    /**
     * 正在直播中的课程
     *
     * @return
     */
    @Select({"select id, direct_id as directId from oe_course where live_status = 1 and is_delete is false and status = 1 and type = 1"})
    List<Course> selectLivingCourse();


    /**
     * <p>Title: selectTheirCollection</p>
     * <p>Description: 如果是付费的音频或者视频判断其是否包含在某个付费的专辑中</p>
     *
     * @param courseId
     * @return
     */
    @Select({" select oc.grade_name as gradeName,oc.id,oc.smallimg_path as smallImgPath  from  collection_course  cc inner join  oe_course oc on cc.collection_id= oc.id " +
            "   where cc.course_id = ${courseId} and oc.is_free = 0 and oc.is_delete =0 and oc.status = 1 order by cc.create_time limit 0,1 "})
    Map<String, Object> selectTheirCollection(@Param("courseId") Integer courseId);

    /**
     * <p>Title: selectTherapyLiveInfo</p>
     * <p>Description: </p>
     *
     * @param id CONCAT(date," ",start_time) as startTime,CONCAT(date," ",end_time) as endTime
     */
    @Select({"select CONCAT(mt.date,' ',mt.start_time) as startTime,CONCAT(mt.date,' ',mt.end_time) as endTime,"
            + " mt.doctor_id as doctorId,mt.create_person as userLecturerId,"
            + " mtai.question as description,mtai.user_id as userId,"
            + " ca.detail as lecturerDescription,"
            + " ca.name as heir,md.name as doctorName from medical_treatment mt " +
            "    inner join medical_treatment_appointment_info mtai on mt.info_id = mtai.id " +
            "	 inner join course_anchor ca on mt.create_person = ca.user_id  " +
            "	 inner join medical_doctor md on mt.doctor_id = md.id  " +
            "	 where mt.info_id =  #{infoId} "})
    CourseLecturVo selectTherapyLiveInfo(@Param("infoId") Integer infoId);

    /**
     * <p>Title: selectDoctorCurrentDayTherapyNumber</p>
     * <p>Description: </p>
     *
     * @return
     */
    @Select({" select grade_name  from oe_course "
            + " where appointment_info_Id 	is not null and to_days(start_time) = to_days(#{startTime}) "
            + " and user_lecturer_id  = #{userLecturerId}   order by grade_name desc "})
    List<String> selectDoctorCurrentDayTherapyNumber(@Param("startTime") Date startTime, @Param("userLecturerId") String userLecturerId);

    /**
     * <p>Title: updateAppointmentInfoStatus</p>
     * <p>Description: </p>
     *
     * @param id
     */
    @Update({" update medical_treatment_appointment_info set status = #{status}  where id = #{id} "})
    Integer updateAppointmentInfoPass(@Param("id") Integer id, @Param("status") Integer status);


    /**
     * <p>Title: insertCouserApplyInfo</p>
     * <p>Description: </p>
     *
     * @param course
     */
    @Insert({" insert into course_apply_info (title,subtitle,user_id, img_path, lecturer, lecturer_description,"
            + " course_form,course_menu,start_time, price, course_description,course_detail,"
            + " multimedia_type,sale,status,create_time,update_time,client_type) "
            + " values(#{course.gradeName}, #{course.gradeName},#{course.userLecturerId}, #{course.smallImgPath}, #{course.lecturer}, #{course.lecturerDescription},"
            + "	#{course.type},'', #{course.startTime}, #{course.currentPrice}, #{course.courseDetail}, #{course.courseDetail},"
            + "	1, 1,1, now(), now(),#{course.clientType}) "})
    @Options(useGeneratedKeys = true, keyProperty = "course.examineId", keyColumn = "id")
    void insertCouserApplyInfo(@Param("course") Course course);
}