package com.xczhihui.medical.headline.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.mapper.OeBxsAppraiseMapper;
import com.xczhihui.medical.headline.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.headline.model.OeBxsAppraise;
import com.xczhihui.medical.headline.model.OeBxsArticle;
import com.xczhihui.medical.headline.service.IOeBxsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-02
 */
@Service
public class OeBxsArticleServiceImpl extends ServiceImpl<OeBxsArticleMapper, OeBxsArticle> implements IOeBxsArticleService {

    @Autowired
    private OeBxsAppraiseMapper oeBxsAppraiseMapper;

    @Override
    public OeBxsArticle selectArticleById(Integer id) {
        return baseMapper.selectArticleById(id);
    }

    @Override
    public Page<OeBxsAppraise> selectArticleAppraiseById(Page page, Integer id, String userId) {
        List<OeBxsAppraise> records = oeBxsAppraiseMapper.selectArticleAppraiseById(page,id,userId);
        page.setRecords(records);
        return page;
    }

    @Override
    public Page<OeBxsArticle> selectArticlesByPage(Page page, String type) {
        List<OeBxsArticle> oeBxsArticles = baseMapper.selectArticlesByPage(page, type);
        page.setRecords(oeBxsArticles);
        return page;
    }
}
