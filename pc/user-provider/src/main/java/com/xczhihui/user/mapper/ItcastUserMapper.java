package com.xczhihui.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.user.model.ItcastUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface ItcastUserMapper extends BaseMapper<ItcastUser> {

    @Select("SELECT * FROM `itcast_user`")
    List<ItcastUser> selectAll();
}