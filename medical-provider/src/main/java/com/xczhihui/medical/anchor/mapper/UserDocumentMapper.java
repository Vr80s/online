package com.xczhihui.medical.anchor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            " where user_id = #{userId} and deleted = false" +
            " order by create_time desc"})
    List<UserDocument> listByUserId(@Param("userId") String userId);

    /**
     * 更新文档的转换状态
     *
     * @param documentId 文档id
     * @param status     状态
     */
    @Update({"update oe_user_document set trans_status = #{status}, page = #{page} where document_id = #{documentId}"})
    void updateStatusByDocumentId(@Param("documentId") String documentId, @Param("status") Integer status, @Param("page") int page);

    /**
     * 删除文档
     *
     * @param documentId documentId
     */
    @Update({"update oe_user_document set deleted = 1 where document_id = #{documentId}"})
    void delete(@Param("documentId") String documentId);
}
