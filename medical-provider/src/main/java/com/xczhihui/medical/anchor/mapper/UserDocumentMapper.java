package com.xczhihui.medical.anchor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.anchor.model.UserDocument;

/**
 * @author hejiwei
 */
public interface UserDocumentMapper extends BaseMapper<UserDocument> {

    /**
     * 查询用户创建的文档
     *
     * @param userId userId
     * @return
     */
    @Select({"select *" +
            " from oe_user_document" +
            " where user_id = #{userId}"})
    List<UserDocument> listByUserId(@Param("userId") String userId);
}
