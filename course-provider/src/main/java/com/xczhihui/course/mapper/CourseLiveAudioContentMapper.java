package com.xczhihui.course.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

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
    void insertCourseLiveAudioContentLike(@Param("audioContentId") Integer audioContentId, @Param("userId") String userId);

    @Select("SELECT clac.* FROM `oe_course_live_audio_content` clac WHERE clac.`is_delete`=0 clac.`course_id`=#{courseId} AND clac.`create_time` <= closingDateTime")
    List<CourseLiveAudioContentVO> selectCourseLiveAudioContentByCourseId(@Param("page") Page page, @Param("closingDateTime") Date closingDateTime, @Param("courseId") Integer courseId);
}