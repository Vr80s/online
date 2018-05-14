package com.xczhihui.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.user.model.OeUser;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface OeUserMapper extends BaseMapper<OeUser> {

    @Select("SELECT * FROM `oe_user`")
    void selectAll();

    @Select("SELECT * FROM `oe_user` where login_name = #{loginName}")
    OeUser selectByLoginName(String loginName);
}