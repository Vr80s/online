package com.xczhihui.course.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.CourseLiveAudioPPT;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
public interface CourseLiveAudioPptMapper extends BaseMapper<CourseLiveAudioPPT> {

    @Select("UPDATE `oe_course_live_audio_ppt` clap SET clap.is_delete=1 WHERE clap.`course_id`=#{courseId} AND clap.`is_delete`=0")
    void deleteByCourseId(Integer courseId);
}