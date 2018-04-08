package com.xczhihui.operate.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.operate.vo.Banner2Vo;

public interface Banner2Service {

	public Page<Banner2Vo> findBanner2Page(Banner2Vo banner2Vo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 新增BANNER
	 * 
	 *@return void
	 */
	public void addBanner(Banner2Vo banner2Vo);

	/**
	 * 修改banner
	 * 
	 *@return void
	 */
	public void updateBanner(Banner2Vo banner2Vo);

	/**
	 * 修改状态
	 * 
	 *@return void
	 */
	public boolean updateStatus(Banner2Vo banner2Vo);
	
	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);

	/**
	 * 向上调整顺序
	 * 
	 *@return void
	 */
	public void updateSortUp(Integer id);

	/**
	 * 向上调整顺序
	 * 
	 *@return void
	 */
	public void updateSortDown(Integer id);
}
