package com.xczhihui.headline.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.headline.vo.AppraiseVo;

public interface AppraiseService {
	/**
	 * 查询一页数据
	 * @param vid
	 */
	Page<AppraiseVo> findAppraisePage(AppraiseVo searchVo, int currentPage, int pageSize);
	/**
	 * 单挑删除
	 * 
	 */
	public void deleteById(String id,String articleId);







}
