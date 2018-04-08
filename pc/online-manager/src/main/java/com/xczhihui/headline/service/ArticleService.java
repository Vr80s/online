package com.xczhihui.headline.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.headline.vo.ArticleVo;
import com.xczhihui.headline.vo.TagVo;

public interface ArticleService {

	public Page<ArticleVo> findCoursePage(ArticleVo searchVo, int currentPage, int pageSize);

	public List<ArticleTypeVo> getArticleTypes();

	public List<TagVo> getTags();

	public void addArticle(ArticleVo articleVo);

	public void addArticleTag(ArticleVo articleVo);

	public ArticleVo findArticleById(Integer id);

	public void updateArticle(ArticleVo articleVo);

	public void deletes(String[] ids);

	public void updateStatus(Integer id);

	public void addPreArticle(ArticleVo articleVo);

	public void updateRecommend(Integer id);


}
