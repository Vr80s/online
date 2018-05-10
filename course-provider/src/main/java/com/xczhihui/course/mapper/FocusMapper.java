package com.xczhihui.course.mapper;

import java.util.List;

import com.xczhihui.course.model.Focus;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.vo.FocusVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
public interface FocusMapper extends BaseMapper<Focus> {

	List<Integer> selectFocusAndFansCount(@Param("userId")String userId);

	List<FocusVo> selectFocusList(@Param("userId") String userId);
	
	List<FocusVo> selectFansList(@Param("userId") String userId);

	Integer isFoursLecturer(@Param("userId") String userId,@Param("lecturerId")String lecturerId);
	
	Focus findFoursByUserIdAndlecturerId(@Param("userId") String userId,@Param("lecturerId")String lecturerId);
}