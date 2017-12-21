package com.xczhihui.medical.doctor.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.doctor.model.OeBxsArticle;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVo;

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

    List<OeBxsArticleVo> getNewsReports(String doctorId);

    OeBxsArticleVo getNewsReportByArticleId(String articleId);

    List<OeBxsArticleVo> getSpecialColumnByDoctorId(String doctorId);

    OeBxsArticleVo getSpecialColumnDetailsById(String articleId);
}