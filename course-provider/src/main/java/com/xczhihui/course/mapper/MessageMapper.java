package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Message;

/**
 * @author hejiwei
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 通过用户id分页查询消息
     *
     * @param userId
     * @param page
     * @return
     */
    @Select("select * from oe_message where user_id = #{userId}")
    List<Message> findByUserId(Page<Message> page, @Param("userId") String userId);

}
