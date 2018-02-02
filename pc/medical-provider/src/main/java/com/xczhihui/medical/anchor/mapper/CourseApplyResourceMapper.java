package com.xczhihui.medical.anchor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.vo.CourseApplyResourceVO;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
public interface CourseApplyResourceMapper extends BaseMapper<CourseApplyResource> {

    List<CourseApplyResourceVO> selectAllCourseResources(String userId);

    List<CourseApplyResourceVO> selectCourseResourceByPage(Page<CourseApplyResourceVO> page, String userId);

    List<CourseApplyResource> selectAllCourseResourcesForUpdateDuration();
}