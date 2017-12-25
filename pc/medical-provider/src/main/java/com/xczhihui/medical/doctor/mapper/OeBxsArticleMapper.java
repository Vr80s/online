package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.OeBxsArticle;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
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

    List<OeBxsArticleVO> getRecentlyNewsReports();

    List<OeBxsArticleVO> getNewsReportsByPage(@Param("page") Page<OeBxsArticleVO> page, @Param("doctorId") String doctorId, @Param("doctorReport") String doctorReport);

    List<OeBxsArticleVO> getHotSpecialColumn(String specialColumn);
}