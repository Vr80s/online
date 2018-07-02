package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.OfflineCity;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface OfflineCityMapper extends BaseMapper<OfflineCity> {


    List<OfflineCity> selectOfflineCityPage(@Param("page") Page<OfflineCity> page);


    List<OfflineCity> selectOfflineRecommendedCityPage(@Param("page") Page<OfflineCity> page);

}