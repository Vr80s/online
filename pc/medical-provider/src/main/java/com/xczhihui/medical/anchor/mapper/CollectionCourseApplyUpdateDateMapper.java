package com.xczhihui.medical.anchor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.anchor.model.CollectionCourseApplyUpdateDate;

/**
 * @author hejiwei
 */
public interface CollectionCourseApplyUpdateDateMapper extends BaseMapper<CollectionCourseApplyUpdateDate> {

    /**
     * 删除更新时间
     *
     * @param collectionApplyId 专辑申请的id
     */
    @Delete("delete from collection_course_apply_update_date where collection_apply_id = #{collectionApplyId}")
    void deleteByCollectionApplyId(@Param("collectionApplyId") Integer collectionApplyId);

    /**
     * 查询更新时间
     *
     * @param collectionApplyId 专辑课程申请的id
     * @return
     */
    @Select("select date from collection_course_apply_update_date where collection_apply_id = #{collectionApplyId}")
    List<Integer> listDatesByCollectionApplyId(@Param("collectionApplyId") Integer collectionApplyId);
}
