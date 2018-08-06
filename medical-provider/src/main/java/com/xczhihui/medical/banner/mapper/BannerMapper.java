package com.xczhihui.medical.banner.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.banner.model.OeBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface BannerMapper extends BaseMapper<OeBanner> {

    /**
     * 获取科室列表（分页）
     *
     * @param page 翻页对象
     * @return 每页的科室内容
     */
    List<OeBanner> page(@Param("page")Page page,@Param("type")Integer type,@Param("clientType")Integer clientType);
}