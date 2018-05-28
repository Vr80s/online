package com.xczhihui.operate.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.AppVersionInfo;

public interface AppVersionManagerService {

	public Page<AppVersionInfo> findAppVersionInfoPage(
			AppVersionInfo appVersionInfo, Integer pageNumber, Integer pageSize);

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addAppVersionInfo(AppVersionInfo appVersionInfo);

	/**
	 * 修改banner
	 * 
	 * @return void
	 */
	public void updateAppVersionInfo(AppVersionInfo appVersionInfo);

	/**
	 * 修改状态
	 * 
	 * @return void
	 */
	public boolean updateStatus(AppVersionInfo appVersionInfo);

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
