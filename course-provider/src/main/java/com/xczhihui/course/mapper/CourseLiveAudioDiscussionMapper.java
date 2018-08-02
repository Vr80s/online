package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.CourseLiveAudioDiscussion;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;

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

    @Select({"SELECT ou.`small_head_photo` imgUrl,ou.`name`,clad.is_anchor anchor,clad.* FROM `oe_course_live_audio_discussion` clad JOIN `oe_user` ou ON clad.`user_id`=ou.id WHERE clad.id=#{discussionId}"})
    CourseLiveAudioDiscussionVO selectCourseLiveAudioDiscussionById(Integer discussionId);

    @Select({"SELECT ou.`small_head_photo` ,ou.`name`,clad.is_anchor anchor,clad.* FROM `oe_course_live_audio_discussion` clad JOIN `oe_user` ou ON clad.`user_id`=ou.id WHERE clad.course_id=#{courseId} AND clad.`create_time` <= #{endTime} order by clad.`create_time` desc"})
    List<CourseLiveAudioDiscussionVO> selectCourseLiveAudioDiscussionByCourseId(Page page, String endTime, Integer courseId);

    @Update("UPDATE `oe_course_live_audio_discussion`  clad SET clad.`likes`=clad.`likes`+1 WHERE clad.id=#{discussionId}")
    int updateLikeById(@Param("discussionId") Integer discussionId);

    @Update("UPDATE `oe_course_live_audio_discussion` clad JOIN `oe_course` oc ON clad.`course_id`=oc.`id` SET clad.`is_delete`=1 WHERE clad.id=#{courseLiveAudioDiscussionId} AND (clad.`user_id`=#{userId} OR oc.`user_lecturer_id`=#{userId})")
    int deleteByUserIdAndId(@Param("userId") String userId, @Param("courseLiveAudioDiscussionId") Integer courseLiveAudioDiscussionId);

    @Select("SELECT oc.`channel_id` channelId FROM `oe_course` oc JOIN `oe_course_live_audio_discussion` clad ON oc.id=clad.`course_id` WHERE clad.id=#{courseLiveAudioDiscussionId}")
    String selectChannelIdByCourseLiveAudioDiscussionId(Integer courseLiveAudioDiscussionId);

    @Select("SELECT COUNT(oc.id) FROM `oe_course` oc WHERE oc.id=#{courseId} AND oc.`user_lecturer_id`=#{accountId}")
    int selectCourseCount(String accountId, Integer courseId);

    @Select("select clad.`id` from `oe_course_live_audio_discussion` clad where clad.`course_id`=#{courseId} and clad.`user_id`=#{userId} and clad.`is_delete`=0")
    List<Integer> selectCourseLiveAudioDiscussionIdsByCourseIdAndUserId(Integer courseId, String userId);
}