package com.xczhihui.medical.headline.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-20
 */
public interface OeBxsArticleMapper extends BaseMapper<OeBxsArticle> {

    List<OeBxsArticleVO> getNewsReports(String doctorId);

    OeBxsArticleVO getNewsReportByArticleId(String articleId);

    List<OeBxsArticleVO> getSpecialColumns(@Param("page") Page<OeBxsArticleVO> page, @Param("doctorId") String doctorId);

    OeBxsArticleVO getSpecialColumnDetailsById(String articleId);

    List<OeBxsArticleVO> getRecentlyNewsReports(@Param("typeId") String typeId);

    List<OeBxsArticleVO> getNewsReportsByPage(@Param("page") Page<OeBxsArticleVO> page, @Param("doctorId") String doctorId, @Param("typeId") String typeId);

    List<OeBxsArticleVO> getHotSpecialColumn(String specialColumn);

    List<OeBxsArticle> selectArticlesByPage(@Param("page") Page page, @Param("typeId") String typeId);

    OeBxsArticle selectArticleById(Integer id);

    List<OeBxsArticleVO> getHotArticles();

    /**
     * 查询医师关联的媒体报道
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return 列表数据
     */
    @Select({"select oba.id, oba.`title`, oba.`content`, oba.`update_time` as updateTime," +
            " oba.`img_path` as imgPath, oba.status as status, oba.user_id as author, oba.url as url" +
            " from `oe_bxs_article` oba" +
            " LEFT JOIN `medical_doctor_report` mdr" +
            " ON oba.`id` = mdr.`article_id` " +
            " where oba.`is_delete`=0 AND mdr.doctor_id = #{doctorId} " +
            " order by oba.create_time desc"})
    List<OeBxsArticleVO> listReport(Page<OeBxsArticleVO> page, @Param("doctorId") String doctorId);

    /**
     * 查询媒体报道
     *
     * @param id id
     * @return 报道数据
     */
    @Select({"select oba.id, oba.`title`, oba.`content`, oba.`update_time` as updateTime," +
            " oba.`img_path` as imgPath, oba.status as status, oba.user_id as author, oba.url as url" +
            " from `oe_bxs_article` oba" +
            " LEFT JOIN `medical_doctor_report` mdr" +
            " ON oba.`id` = mdr.`article_id` " +
            " where oba.id = #{id} and oba.`is_delete`=0"})
    OeBxsArticleVO getReportById(@Param("id") String id);


    /**
     * 查询医师关联的专栏
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return 列表数据
     */
    @Select({"select oba.id, oba.`title`, oba.`content`, oba.`update_time` as updateTime," +
            " oba.`img_path` as imgPath, oba.status as status, oba.user_id as author, oba.url as url" +
            " from `oe_bxs_article` oba" +
            " LEFT JOIN `medical_doctor_special_column` mdsc" +
            " ON oba.`id` = mdsc.`article_id` " +
            " where oba.`is_delete`=0 and mdsc.doctor_id = #{doctorId} " +
            " order by oba.create_time desc"})
    List<OeBxsArticleVO> listSpecialColumn(Page<OeBxsArticleVO> page, @Param("doctorId") String doctorId);

    /**
     * 查询专栏
     *
     * @param id id
     * @return 专栏数据
     */
    @Select({"select oba.id, oba.`title`, oba.`content`, oba.`update_time` as updateTime," +
            " oba.`img_path` as imgPath, oba.status as status, oba.user_id as author, oba.url as url" +
            "   from" +
            "       `oe_bxs_article` oba" +
            "       LEFT JOIN `medical_doctor_special_column` mdsc" +
            "       ON oba.`id` = mdsc.`article_id` " +
            "   where oba.id = #{id} and oba.`is_delete`=0 AND"})
    OeBxsArticleVO getSpecialColumnById(@Param("id") String id);

    /**
     * 获取文章报道的医师
     *
     * @param id id
     * @return
     */
    @Select({"SELECT md.name AS doctorName, md.`id` AS doctorId, mdai.head_portrait headPortrait, md.`province`, md.`city` " +
            "        FROM" +
            "            `oe_bxs_article` oba" +
            "            JOIN `medical_doctor_report` mdr" +
            "                ON oba.`id` = mdr.`article_id`" +
            "            JOIN `medical_doctor` md" +
            "                ON md.id = mdr.`doctor_id`" +
            "            JOIN `medical_doctor_authentication_information` mdai" +
            "                ON mdai.`id` = md.`authentication_information_id`" +
            "        WHERE oba.id = #{id}" +
            "        ORDER BY oba.`create_time` DESC"})
    List<Map<String, Object>> listReportDoctorByArticleId(@Param("id") int id);

    /**
     * 获取专栏作者
     *
     * @param id id
     * @return
     */
    @Select({"SELECT md.name AS doctorName, md.`id` AS doctorId, mdai.head_portrait headPortrait, md.`province`, md.`city`" +
            "        FROM" +
            "            `oe_bxs_article` oba" +
            "            JOIN `medical_doctor_special_column` mdsc" +
            "                ON oba.`id` = mdsc.`article_id`" +
            "            JOIN `medical_doctor` md" +
            "                ON md.id = mdsc.`doctor_id`" +
            "            JOIN `medical_doctor_authentication_information` mdai" +
            "                ON mdai.`id` = md.`authentication_information_id`" +
            "        WHERE oba.id = #{id}" +
            "        ORDER BY oba.`create_time` DESC"})
    List<Map<String, Object>> listSpecialColumnAuthorByArticleId(int id);
}