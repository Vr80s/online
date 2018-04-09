package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.cloudClass.vo.CourseVo;
import com.xczhihui.cloudClass.vo.CriticizeVo;

public interface CriticizeService {

	public Page<CriticizeVo> findCriticizePage(CriticizeVo criticizeVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

	/**
	 * 回复
	 * 
	 * @param id
	 * @param content
	 */
	public void addResponse(String id, String content);

}
