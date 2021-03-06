package com.xczhihui.medical.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalWriting;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;

/**
 * @author hejiwei
 */
public interface MedicalWritingMapper extends BaseMapper<MedicalWriting> {

    /**
     * 分页查询
     *
     * @param medicalWritingsVOPage 分页参数
     * @param userId                用户id
     * @return 列表数据
     */
    @Select({"SELECT mw.id, mw.`title`, mw.`author`, mw.`img_path` as imgPath, mw.buy_link as buyLink, mw.status as status, mw.update_time as updateTime, mw.remark" +
            " FROM `medical_writings` mw" +
            " WHERE mw.`deleted`=0 AND mw.create_person = #{userId}" +
            " ORDER BY mw.`create_time` desc"})
    List<MedicalWritingVO> listWritingByUserId(Page<MedicalWritingVO> medicalWritingsVOPage, @Param("userId") String userId);

    /**
     * 分页查询
     *
     * @param medicalWritingsVOPage 分页参数
     * @param doctorId              医师id
     * @return 列表数据
     */
    @Select({"SELECT mw.id, mw.`title`, mw.`author`, mw.`img_path` as imgPath, mw.buy_link as buyLink," +
            " mw.status as status, mw.create_time as createTime, mw.remark, mw.article_id as articleId" +
            " FROM `medical_writings` mw" +
            " LEFT JOIN `medical_doctor_writings` mdw ON mdw.`writings_id` = mw.`id`" +
            " LEFT JOIN `oe_bxs_article` a ON mw.article_id = a.id " +
            "WHERE mw.`deleted`=0 AND a.is_delete = 0 AND mw.`status`=1 AND (#{doctorId} is null OR mdw.doctor_id = #{doctorId})" +
            " ORDER BY mw.`create_time` DESC"})
    List<MedicalWritingVO> listWriting(Page<MedicalWritingVO> medicalWritingsVOPage, @Param("doctorId") String doctorId);

    /**
     * 获取著作数据
     *
     * @param id id
     * @return 列表数据
     */
    @Select({"SELECT mw.id, mw.`title`, mw.`author`, mw.`img_path` as imgPath, mw.buy_link as buyLink,mw.article_id as articleId," +
            " mw.status as status, mw.update_time as updateTime, mw.remark" +
            " FROM `medical_writings` mw" +
            "    LEFT JOIN `medical_doctor_writings` mdw ON mdw.`writings_id` = mw.`id`" +
            "WHERE mw.id = #{id} AND mw.`deleted`=0 AND mw.`status`=1" +
            " ORDER BY mw.`create_time` desc"})
    MedicalWritingVO get(@Param("id") String id);

    /**
     * 更新发布状态
     *
     * @param doctorId 医师id
     * @param id       id
     * @param status   状态
     * @return 更新行数
     */
    @Update("update medical_writings mw" +
            " set mw.status = #{status}" +
            " where mw.id = #{id} and mw.deleted = 0")
    int updateStatus(@Param("doctorId") String doctorId, @Param("id") String id, @Param("status") boolean status);

    /**
     * 文章id获取著作数据
     *
     * @param articleId 文章id
     * @return 著作数据
     */
    @Select({"select id, title, author, img_path as imgPath, buy_link as buyLink from medical_writings where article_id = #{articleId}"})
    MedicalWriting findByArticleId(@Param("articleId") int articleId);
}
