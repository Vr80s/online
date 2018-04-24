package com.xczhihui.medical.doctor.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * @author hejiwei
 */
public interface IMedicalDoctorArticleService {

    /**
     * 专栏列表
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return 列表数据
     */
    Page<OeBxsArticleVO> listSpecialColumn(int page, String doctorId);

    /**
     * 创建专栏
     *
     * @param doctorId     医师id
     * @param oeBxsArticle 专栏数据
     */
    void saveSpecialColumn(String doctorId, OeBxsArticle oeBxsArticle);

    /**
     * 更新专栏
     *
     * @param doctorId     医师id
     * @param oeBxsArticle 医师专栏数据
     * @param id           id
     * @return 是否更新成功
     */
    boolean updateSpecialColumn(String doctorId, OeBxsArticle oeBxsArticle, String id);

    /**
     * 获取专栏数据
     *
     * @param id id
     * @return 专栏数据
     */
    OeBxsArticleVO getSpecialColumn(String id);

    /**
     * 更新专栏状态
     *
     * @param id     id
     * @param status 发布状态
     * @return 是否更新成功
     */
    boolean updateSpecialColumnStatus(String id, int status);

    /**
     * 标记删除
     *
     * @param id id
     * @return 是否删除成功
     */
    boolean deleteSpecialColumnById(String id);

    /**
     * 热门专栏作者
     *
     * @param doctorId 医师id
     * @return 热门专栏作者
     */
    List<Map<String, Object>> listHotSpecialColumnAuthor(String doctorId);

    /**
     * 获取医师的已发布的专栏
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return
     */
    Page<OeBxsArticleVO> listPublicSpecialColumn(int page, String doctorId);


    /**
     * 媒体报道列表
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return 列表数据
     */
    Page<OeBxsArticleVO> listReport(int page, String doctorId);

    /**
     * 创建媒体报道
     *
     * @param doctorId     医师id
     * @param oeBxsArticle 媒体报道数据
     */
    void saveReport(String doctorId, OeBxsArticle oeBxsArticle);

    /**
     * 更新报道
     *
     * @param doctorId     医师id
     * @param oeBxsArticle 医师报道数据
     * @param id           id
     * @return 是否更新成功
     */
    boolean updateReport(String doctorId, OeBxsArticle oeBxsArticle, String id);

    /**
     * 获取报道数据
     *
     * @param id id
     * @return 专栏数据
     */
    OeBxsArticleVO getReport(String id);

    /**
     * 更新报道状态
     *
     * @param id     id
     * @param status 发布状态
     * @return 是否更新成功
     */
    boolean updateReportStatus(String id, int status);

    /**
     * 标记删除
     *
     * @param id id
     * @return 是否删除成功
     */
    boolean deleteReportById(String id);

    /**
     * 获取文章报道的医师
     *
     * @param id id
     * @return
     */
    List<Map<String, Object>> listReportDoctorByArticleId(int id);

    /**
     * 获取专栏作者
     *
     * @param id id
     * @return
     */
    List<Map<String, Object>> listSpecialColumnAuthorByArticleId(int id);

    /**
     * 媒体报道名医
     *
     * @param doctorId 医师id
     * @return 热门专栏作者
     */
    List<Map<String, Object>> listReportDoctor(String doctorId);

    /**
     * 获取医师的已发布的报道
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return
     */
    Page<OeBxsArticleVO> listPublicReport(int page, String doctorId);

    /**
     * 获取医师的已发布的著作
     *
     * @param page     分页参数
     * @param doctorId 医师id
     * @return
     */
    Page<OeBxsArticleVO> listPublicWriting(int page, String doctorId);
}
