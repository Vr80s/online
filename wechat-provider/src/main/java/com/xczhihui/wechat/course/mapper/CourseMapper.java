package com.xczhihui.wechat.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface CourseMapper extends BaseMapper<Course> {


	 List<CourseLecturVo> selectCoursePage(@Param("page") Page<CourseLecturVo> page);
}