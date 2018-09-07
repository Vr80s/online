package com.xczhihui.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
public interface CourseLiveAudioDiscussionMapper extends BaseMapper<CourseLiveAudioDiscussion> {

    @Insert("INSERT IGNORE INTO `oe_course_live_audio_discussion_like` (discussion_id,user_id) VALUES(#{discussionId},#{userId})")
    int insertCourseLiveAudioDiscussionLike(@Param("discussionId") Integer discussionId, @Param("userId") String userId);

    @Select({"SELECT ou.`small_head_photo` imgUrl,ou.`name`,clad.id,clad.is_anchor anchor,clad.`is_question` question,clad.`content_type` contentType,clad.`content`,clad.`likes`,clad.`create_time` createTime " +
            "FROM `oe_course_live_audio_discussion` clad JOIN `oe_user` ou ON clad.`user_id`=ou.id WHERE clad.id=#{discussionId}"})
    CourseLiveAudioDiscussionVO selectCourseLiveAudioDiscussionById(@Param("discussionId") Integer discussionId);

    @Select({"<script> " +
            " SELECT ou.`small_head_photo` imgUrl,ou.`name`,clad.id,clad.is_anchor anchor,clad.`is_question` question,clad.`content_type` contentType,clad.`content`,clad.`likes`,clad.`create_time` createTime " +
            " FROM `oe_course_live_audio_discussion` clad JOIN `oe_user` ou ON clad.`user_id`=ou.id WHERE clad.course_id=#{courseId} " +
            "<if test='question != null'>" +
            " AND clad.is_question = true " +
            "</if>" +
            " AND clad.`create_time` &lt;= #{endTime} order by clad.`create_time` desc " +
            " </script>"})
    List<CourseLiveAudioDiscussionVO> selectCourseLiveAudioDiscussionByCourseId(@Param("page") Page page, @Param("endTime") String endTime, @Param("courseId") Integer courseId,@Param("question") Boolean question);

    @Update("UPDATE `oe_course_live_audio_discussion`  clad SET clad.`likes`=clad.`likes`+1 WHERE clad.id=#{discussionId}")
    int updateLikeById(@Param("discussionId") Integer discussionId);

    @Update("UPDATE `oe_course_live_audio_discussion` clad JOIN `oe_course` oc ON clad.`course_id`=oc.`id` SET clad.`is_delete`=1 WHERE clad.id=#{courseLiveAudioDiscussionId} AND (clad.`user_id`=#{userId} OR oc.`user_lecturer_id`=#{userId})")
    int deleteByUserIdAndId(@Param("userId") String userId, @Param("courseLiveAudioDiscussionId") Integer courseLiveAudioDiscussionId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc JOIN `oe_course_live_audio_discussion` clad ON oc.id=clad.`course_id` WHERE clad.id=#{courseLiveAudioDiscussionId}")
    String selectChannelIdByCourseLiveAudioDiscussionId(Integer courseLiveAudioDiscussionId);

    @Select("SELECT COUNT(oc.id) FROM `oe_course` oc WHERE oc.id=#{courseId} AND oc.`user_lecturer_id`=#{accountId}")
    int selectCourseCount(@Param("accountId") String accountId, @Param("courseId") Integer courseId);

    @Select("select clad.`id` from `oe_course_live_audio_discussion` clad where clad.`course_id`=#{courseId} and clad.`user_id`=#{userId} and clad.`is_delete`=0")
    List<Integer> selectCourseLiveAudioDiscussionIdsByCourseIdAndUserId(@Param("courseId") Integer courseId, @Param("userId") String userId);
}