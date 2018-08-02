package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.CourseLiveAudioPPT;
import com.xczhihui.course.vo.CourseLiveAudioPPTVO;

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

    @Select({"SELECT ppt.id,ppt.`ppt_img_url` imgUrl, ppt.sort FROM `oe_course_live_audio_ppt` ppt WHERE ppt.`is_delete`=0 AND ppt.course_id=#{courseId} ORDER BY sort ASC "})
    List<CourseLiveAudioPPTVO> selectCourseLiveAudioPPTsByCourseId(Integer courseId);
}