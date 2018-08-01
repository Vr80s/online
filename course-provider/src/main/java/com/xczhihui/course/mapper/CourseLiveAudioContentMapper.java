package com.xczhihui.course.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.common.util.bean.Page;
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

    @Select("SELECT clac.* FROM `oe_course_live_audio_content` clac WHERE clac.`is_delete`=0 AND clac.`course_id`=#{courseId} AND clac.`create_time` <= #{closingDateTime}")
    List<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(@Param("page") Page page, @Param("closingDateTime") String closingDateTime, @Param("courseId") Integer courseId);

    @Update("UPDATE `oe_course_live_audio_content`  clac SET clac.`likes`=clac.`likes`+1 WHERE clac.id=#{audioContentId}")
    int updateLikeById(@Param("audioContentId") Integer audioContentId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc WHERE oc.id=#{courseId}")
    String selectChannelIdByCourseId(@Param("courseId") Integer courseId);

    int insertSelect(@Param("courseLiveAudioContent") CourseLiveAudioContent courseLiveAudioContent);

    @Update("UPDATE `oe_course_live_audio_content`  clac SET clac.`is_delete`=1 WHERE clac.id=#{courseLiveAudioContentId} AND clac.`user_id`=#{userId}")
    int deleteByUserIdAndId(@Param("userId") String userId, @Param("courseLiveAudioContentId") Integer courseLiveAudioContentId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc JOIN `oe_course_live_audio_content` clac ON oc.id=clac.`course_id` WHERE clac.id=#{courseLiveAudioContentId}")
    String selectChannelIdByCourseLiveAudioContentId(@Param("courseLiveAudioContentId") Integer courseLiveAudioContentId);
}