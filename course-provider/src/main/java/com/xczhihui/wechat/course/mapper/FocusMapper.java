package com.xczhihui.wechat.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.wechat.course.model.Focus;
import com.xczhihui.wechat.course.vo.FocusVo;

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