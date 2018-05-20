package com.xczhihui.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MobileBannerMapper extends BaseMapper<MobileBanner> {


	 List<MobileBanner> selectMobileBannerPage(@Param("type") Integer type);
	 
	 void addClickNum(@Param("id") String id);

	 List<CourseLecturVo> recommendCourseList(@Param("cateGoryList")List<MenuVo> menuList,
			@Param("pageSizeUp")Integer pageSizeUp,@Param("pageSizeDown")Integer pageSizeDown);

	 List<CourseLecturVo> realCourseList(@Param("cityList")List<OfflineCity> cityList,
			 @Param("pageSizeUp")Integer pageSizeUp, @Param("pageSizeDown")Integer pageSizeDown);

	List<CourseLecturVo> liveCourseList(@Param("pageSizeUp")Integer pageSizeUp,
			@Param("pageSizeDown")Integer pageSizeDown);

	List<CourseLecturVo> searchQueryKeyCourseList(@Param("queryVo")QueryConditionVo queryVo);

	List<CourseLecturVo> searchCourseList(@Param("queryVo")QueryConditionVo queryVo);
	 
}