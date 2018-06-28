package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.course.vo.QueryConditionVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MobileBannerMapper extends BaseMapper<MobileBanner> {


    List<MobileBanner> selectMobileBannerPage(@Param("type") Integer type);

    void addClickNum(@Param("id") String id);

    List<CourseLecturVo> recommendCourseList(@Param("cateGoryList") List<MenuVo> menuList,
                                             @Param("pageSizeUp") Integer pageSizeUp, @Param("pageSizeDown") Integer pageSizeDown, @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> realCourseList(@Param("cityList") List<OfflineCity> cityList,
                                        @Param("pageSizeUp") Integer pageSizeUp, @Param("pageSizeDown") Integer pageSizeDown, @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> liveCourseList(@Param("pageSizeUp") Integer pageSizeUp,
                                        @Param("pageSizeDown") Integer pageSizeDown, @Param("onlyFree") Boolean onlyFree);

    List<CourseLecturVo> searchQueryKeyCourseList(@Param("queryVo") QueryConditionVo queryVo);

    List<CourseLecturVo> searchCourseList(@Param("queryVo") QueryConditionVo queryVo);

    List<CourseLecturVo> searchCourseList(@Param("page") Page<CourseLecturVo> page, @Param("queryVo") QueryConditionVo queryConditionVo, @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> searchQueryKeyCourseList(@Param("page") Page<CourseLecturVo> page, @Param("queryVo") QueryConditionVo queryConditionVo, @Param("onlyFree") boolean onlyFree);

    List<CourseLecturVo> selectPcIndex(@Param("pageSize") Integer pageSize);

    List<CourseLecturVo> selectUnshelveRecommenCourse(@Param("pageSize") Integer pageSize);

    
    List<CourseLecturVo> realTest1();
    
    List<CourseLecturVo> realTest2(@Param("name") String name);

	List<CourseLecturVo> listenCourseList(@Param("onlyFree") boolean onlyFree);
}