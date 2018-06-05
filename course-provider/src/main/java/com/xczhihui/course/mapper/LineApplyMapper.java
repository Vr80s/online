package com.xczhihui.course.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.LineApply;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
public interface LineApplyMapper extends BaseMapper<LineApply> {

	

	LineApply findLineApplyByUserId(@Param("userId") String userId);

	
}