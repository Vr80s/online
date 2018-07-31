package com.xczhihui.course.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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

    @Insert("INSERT IGNORE INTO `oe_course_live_audio_content_like` (discussion_id,user_id) VALUES(#{discussionId},#{userId})")
    void insertCourseLiveAudioDiscussionLike(@Param("discussionId") Integer discussionId, @Param("userId") String userId);

    @Select("SELECT ou.`small_head_photo`,ou.`name`,clad.* FROM `oe_course_live_audio_discussion` clad JOIN `oe_user` ou ON clad.`user_id`=ou.id WHERE clad.id=#{discussionId}")
    CourseLiveAudioDiscussionVO selectCourseLiveAudioDiscussionById(Integer discussionId);
}