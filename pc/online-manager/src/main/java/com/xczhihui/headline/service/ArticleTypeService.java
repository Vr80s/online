package com.xczhihui.headline.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.headline.vo.ArticleTypeVo;

import java.util.Set;

/**
 * 博学社文章分类管理业务接口
 * 
 * @Author Fudong.Sun【】
 * @Date 2017/1/9 10:59
 */
public interface ArticleTypeService {
	/**
	 * @param typeVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<ArticleTypeVo> findArticleTypePage(ArticleTypeVo typeVo,
			int pageNumber, int pageSize);

	/**
	 * 添加文章分类
	 * 
	 * @param typeVo
	 */
	public void saveType(ArticleTypeVo typeVo);

	/**
	 * 根据id修改文章分类
	 * 
	 * @param typeVo
	 */
	public void updateTypeById(ArticleTypeVo typeVo);

	/**
	 * 修改状态(禁用or启用)
	 * 
	 * @param id
	 */
	public void updateStatus(String id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void deletes(Set<String> ids);

	/**
	 * 上移
	 * 
	 * @param id
	 */
	public void updateSortUp(String id);

	/**
	 * 下移
	 * 
	 * @param id
	 */
	public void updateSortDown(String id);
}
