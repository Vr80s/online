package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.MobileProject;
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
public interface MobileProjectMapper extends BaseMapper<MobileProject> {

	 List<MobileProject> selectMobileProjectPage(@Param("page") Page<MobileProject> page, @Param("type") Integer type);
}