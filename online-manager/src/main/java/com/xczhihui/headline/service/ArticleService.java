package com.xczhihui.headline.service;

import java.util.List;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;

public interface ArticleService {

    Page<ArticleVo> findArticlePage(ArticleVo searchVo, int currentPage,
                                   int pageSize);

    List<ArticleTypeVo> getArticleTypes();

    List<TagVo> getTags();

    void addArticle(ArticleVo articleVo);

    void addArticleTag(ArticleVo articleVo);

    ArticleVo findArticleById(Integer id);

    void updateArticle(ArticleVo articleVo);

    void deletes(String[] ids);

    void updateStatus(Integer id);

    void addPreArticle(ArticleVo articleVo);

    /**
     * Description：
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/4/18 10:03
     **/
    void updateRecommendSort(Integer id, Integer recommendSort,
                             String recommendTime);

}
