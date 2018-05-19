package com.xczhihui.course.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.WechatUser;

/**
 * @author hejiwei
 */
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    /**
     * 通过用户id 查询微信用户信息
     *
     * @param userId
     * @return
     */
    @Select("select * from client_id = #{userId}")
    WechatUser findByUserId(@Param("userId") String userId);
}
