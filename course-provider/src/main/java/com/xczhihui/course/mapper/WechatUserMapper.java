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
    @Select("select * from wxcp_client_user_wx_mapping where client_id = #{userId} and deleted = 0")
    WechatUser findByUserId(@Param("userId") String userId);
}
