package com.xczhihui.user.center.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
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
    void updateLoginName(@Param("oldLoginName")String oldLoginName, @Param("newLoginName")String newLoginName);

    @Select("SELECT * FROM `oe_user` where login_name = #{loginName}")
    OeUserVO getUserVOByLoginName(String loginName);

    @Select({"<script> select id, name, small_head_photo as smallHeadPhoto" +
            " from oe_user " +
            " where id in " +
            " <foreach item=\"id\" collection=\"ids\" separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{id}\n" +
            "    </foreach>" +
            " </script>"})
    List<Map<String, String>> findByIds(@Param("ids") Set<String> ids);
}