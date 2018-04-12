package com.xczhihui.operate.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.operate.vo.NoticeVo;

public interface NoticeService {

	public Page<NoticeVo> findNoticePage(NoticeVo noticeVo, Integer pageNumber,
			Integer pageSize);

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addNotice(NoticeVo noticeVo);

	/**
	 * 修改banner
	 * 
	 * @return void
	 */
	public void updateNotice(NoticeVo noticeVo);

	/**
	 * 修改状态
	 * 
	 * @return void
	 */
	public boolean updateStatus(NoticeVo noticeVo);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

}
