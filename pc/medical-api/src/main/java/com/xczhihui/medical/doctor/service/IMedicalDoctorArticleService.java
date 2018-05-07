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
     * @param page   分页参数
     * @param userId 用户id
     * @return 列表数据
     */
    Page<OeBxsArticleVO> listSpecialColumn(int page, String userId);

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
     * 媒体报道列表
     *
     * @param page   分页参数
     * @param userId 用户id
     * @return 列表数据
     */
    Page<OeBxsArticleVO> listReport(int page, String userId);

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
     * 获取头条列表数据
     *
     * @param page   分页参数
     * @param typeId typeId
     * @return
     */
    Page<OeBxsArticleVO> listPublicArticle(int page, String typeId);

    /**
     * 名医著作
     *
     * @param page 分页参数
     * @param size size
     * @return 名医著作
     */
    Page<OeBxsArticleVO> listPublicWritings(int page, int size);

    /**
     * 名医推荐
     *
     * @param size size
     * @return 名医推荐
     */
    List<Map<String, Object>> listWritingAuthor(int size);

    /**
     * 名医推荐
     *
     * @param size size
     * @return 名医推荐
     */
    List<Map<String, Object>> listReportDoctor(int size);

    /**
     * 专栏作者
     *
     * @param size size
     * @return 专栏作者
     */
    List<Map<String, Object>> listHotSpecialColumnAuthor(int size);

    /**
     * 著作作者
     *
     * @param articleId articleId
     * @return 著作作者
     */
    List<Map<String, Object>> listWritingAuthorByArticleId(int articleId);

    /**
     * 媒体报道名医
     *
     * @param articleId articleId
     * @return 媒体报道名医
     */
    List<Map<String, Object>> listReportDoctorByArticleId(int articleId);

    /**
     * 热门专栏作者
     *
     * @param articleId articleId
     * @return 热门专栏作者
     */
    List<Map<String, Object>> listHotSpecialColumnAuthorByArticleId(int articleId);
}
