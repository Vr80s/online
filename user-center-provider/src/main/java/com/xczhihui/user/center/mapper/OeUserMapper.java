package com.xczhihui.user.center.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.user.center.model.OeUser;
import com.xczhihui.user.center.vo.OeUserVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-14
 */
public interface OeUserMapper extends BaseMapper<OeUser> {

    @Select("SELECT * FROM `oe_user` where login_name = #{loginName}")
    OeUser selectByLoginName(String loginName);

    @Update("update oe_user set login_name=#{newLoginName} where login_name=#{oldLoginName}")
    void updateLoginName(String oldLoginName, String newLoginName);

    @Select("SELECT * FROM `oe_user` where login_name = #{loginName}")
    OeUserVO getUserVOByLoginName(String loginName);
}