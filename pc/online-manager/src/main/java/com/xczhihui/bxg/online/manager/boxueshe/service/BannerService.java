package com.xczhihui.bxg.online.manager.boxueshe.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.boxueshe.vo.ArticleVo;

public interface BannerService {

	Page<ArticleVo> findBannerPage(ArticleVo searchVo, int currentPage, int pageSize);
	/**
	 * 向上调整顺序
	 * 
	 *@return void
	 */
	public void updateSortUp(Integer id);
	/**
	 * 向下调整顺序
	 * 
	 *@return void
	 */
	public void updateSortDown(Integer id);
	/**
	 * 修改图片
	 * 
	 *@return void
	 */
	public void updateBannerPath(ArticleVo articleVo);
	/**
	 * 取消推荐
	 * 
	 *@return void
	 */
	public void updateRec(Integer id);
	

}