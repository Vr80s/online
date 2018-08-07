package com.xczhihui.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.MobileAdvertisement;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface MobileAdvertisementMapper extends BaseMapper<MobileAdvertisement> {


    MobileAdvertisement selectMobileAdvertisement();

    void addClickNum(@Param("id") Integer id);


}