package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * @param userId 用户id
     * @param page   分页参数
     * @return
     */
    @Select("select * from oe_message where user_id = #{userId} and is_delete = 0 and type !=2 order by create_time desc")
    List<Message> findByUserId(Page<Message> page, @Param("userId") String userId);

    /**
     * 消息未读总数
     *
     * @param userId 用户id
     * @return
     */
    @Select("select count(*) from oe_message where user_id = #{userId} and is_delete = 0 and type != 2 and readstatus = 0")
    int countUnReadByUserId(@Param("userId") String userId);

    /**
     * 更新消息的读状态
     *
     * @param id     id
     * @param userId 用户id
     * @return
     */
    @Update("update oe_message set readstatus = 1 where user_id = #{userId} and (#{id} is null OR id = #{id}) and readstatus = 0 and is_delete = 0 and type != 2")
    int updateReadStatus(@Param("id") String id, @Param("userId") String userId);

    /**
     * 消息标记为删除
     *
     * @param id     消息id
     * @param userId 用户id
     * @return
     */
    @Update("update oe_message set is_delete = 1 where user_id = #{userId} and id = #{id} and is_deleted = 0")
    int markDeleted(@Param("id") String id, @Param("userId") String userId);

    /**
     * 分页查询消息数据
     *
     * @param offset 偏移量
     * @param size 分页大小
     * @return
     */
    @Select({"select * from oe_message where type != 2 and is_delete = 0 limit #{offset},#{size}"})
    List<Message> list(@Param("offset") int offset, @Param("size") int size);
}
