package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.CourseLiveAudioContent;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
public interface CourseLiveAudioContentMapper extends BaseMapper<CourseLiveAudioContent> {

    @Insert("INSERT IGNORE INTO `oe_course_live_audio_content_like` (audio_content_id,user_id) VALUES(#{audioContentId},#{userId})")
    int insertCourseLiveAudioContentLike(@Param("audioContentId") Integer audioContentId, @Param("userId") String userId);

    @Select("SELECT clac.id,clac.`course_id` AS courseId,clac.`audio_ppt_id` AS audiopptId,clac.`content_type` AS contentType,clac.`content`," +
            "clac.`length`,clac.`user_id` AS userId,clac.`discussion_id` AS discussionId,clac.likes,clac.`create_time` createTime,ppt.`ppt_img_url` pptImgUrl " +
            "FROM `oe_course_live_audio_content` clac LEFT JOIN `oe_course_live_audio_ppt` ppt ON ppt.id=clac.`audio_ppt_id`  " +
            " WHERE clac.`is_delete`=0 AND clac.`course_id`=#{courseId} AND clac.`create_time` <= #{endTime} order by clac.`create_time` desc")
    List<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(@Param("page") Page page, @Param("endTime") String endTime, @Param("courseId") Integer courseId);

    @Update("UPDATE `oe_course_live_audio_content`  clac SET clac.`likes`=clac.`likes`+1 WHERE clac.id=#{audioContentId}")
    int updateLikeById(@Param("audioContentId") Integer audioContentId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc WHERE oc.id=#{courseId} AND oc.`multimedia_type`=2 ")
    String selectChannelIdByCourseId(@Param("courseId") Integer courseId);

    int insertSelect(@Param("courseLiveAudioContent") CourseLiveAudioContent courseLiveAudioContent);

    @Update("UPDATE `oe_course_live_audio_content`  clac SET clac.`is_delete`=1 WHERE clac.id=#{courseLiveAudioContentId} AND clac.`user_id`=#{userId}")
    int deleteByUserIdAndId(@Param("userId") String userId, @Param("courseLiveAudioContentId") Integer courseLiveAudioContentId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc JOIN `oe_course_live_audio_content` clac ON oc.id=clac.`course_id` WHERE clac.id=#{courseLiveAudioContentId}")
    String selectChannelIdByCourseLiveAudioContentId(@Param("courseLiveAudioContentId") Integer courseLiveAudioContentId);

    @Select("SELECT clac.id,clac.`course_id` AS courseId,clac.`audio_ppt_id` AS audiopptId,clac.`content_type` AS contentType,clac.`content`," +
            "clac.`length`,clac.`user_id` AS userId,clac.`discussion_id` AS discussionId,clac.likes,clac.`create_time` createTime,ppt.`ppt_img_url` pptImgUrl " +
            "FROM `oe_course_live_audio_content` clac LEFT JOIN `oe_course_live_audio_ppt` ppt ON ppt.id=clac.`audio_ppt_id`  " +
            " WHERE clac.`is_delete`=0 AND clac.id=#{id}")
    CourseLiveAudioContentVO selectCourseLiveAudioContentById(Integer id);

    @Update("UPDATE `oe_course` oc SET oc.`live_status`=3 WHERE oc.`user_lecturer_id`=#{accountId} AND oc.id=#{courseId}")
    void stop(@Param("accountId") String accountId, @Param("courseId") Integer courseId);

    @Select("SELECT COUNT(*) FROM `oe_course_live_audio_content` clac WHERE clac.`course_id`=#{courseId}")
    int selectContentCountByCourseId(@Param("courseId") Integer courseId);

    @Update("UPDATE `oe_course` oc SET oc.`live_status`=1 WHERE oc.`user_lecturer_id`=#{userId} AND oc.id=#{courseId}")
    void start(String userId, Integer courseId);
}