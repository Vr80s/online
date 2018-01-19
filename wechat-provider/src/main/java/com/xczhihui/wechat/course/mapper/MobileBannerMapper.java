package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.model.MobileBanner;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
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


	 List<MobileBanner> selectMobileBannerPage(@Param("page") Page<MobileBanner> page, @Param("type") Integer type);
}