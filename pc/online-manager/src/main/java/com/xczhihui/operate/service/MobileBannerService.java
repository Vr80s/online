package com.xczhihui.operate.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.MobileBannerVo;

public interface MobileBannerService {

	public Page<MobileBannerVo> findMobileBannerPage(
			MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize);

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addMobileBanner(MobileBannerVo mobileBannerVo);

	/**
	 * 修改banner
	 * 
	 * @return void
	 */
	public void updateMobileBanner(MobileBannerVo mobileBannerVo);

	/**
	 * 修改状态
	 * 
	 * @return void
	 */
	public boolean updateStatus(MobileBannerVo mobileBannerVo);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

	/**
	 * 向上调整顺序
	 * 
	 * @return void
	 */
	public void updateSortUp(String id);

	/**
	 * 向上调整顺序
	 * 
	 * @return void
	 */
	public void updateSortDown(String id);
}
