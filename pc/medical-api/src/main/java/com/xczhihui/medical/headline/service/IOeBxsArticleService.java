package com.xczhihui.medical.headline.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
public interface IOeBxsArticleService extends IService<OeBxsArticle> {

    OeBxsArticle selectArticleById(Integer id);

    Page<OeBxsAppraise> selectArticleAppraiseById(@Param("oeBxsAppraisePage") Page oeBxsAppraisePage, @Param("id") Integer id, @Param("userId") String userId);

    Page<OeBxsArticle> selectArticlesByPage(Page page, String type);
}