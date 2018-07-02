package com.xczhihui.user.center.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.user.center.model.ItcastUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface ItcastUserMapper extends BaseMapper<ItcastUser> {

    @Select("SELECT * FROM `itcast_user`")
    List<ItcastUser> selectAll();
}