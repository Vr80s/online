package com.xczhihui.anchor.service;

import com.xczhihui.anchor.vo.AnchorIncomeVO;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;

public interface AnchorService {

	/**
	 * 根据条件分页获取课程信息。
	 *
	 * @return
	 */
	public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor,
			int pageNumber, int pageSize);

	CourseAnchor findCourseAnchorById(Integer id);

	CourseAnchor findCourseAnchorByUserId(String userId);

	void updateCourseAnchor(CourseAnchor courseAnchor);

	void updatePermissions(Integer id);

	Page<AnchorIncomeVO> findCourseAnchorIncomePage(CourseAnchor searchVo,
			int currentPage, int pageSize);

	/**
	 * 上移
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void updateSortUpRec(Integer id);

	/**
	 * 下移
	 * 
	 * @param Integer
	 *            id
	 * @return
	 */
	public void updateSortDownRec(Integer id);

	void updateRec(String[] ids, int isRec);

	Page<CourseAnchor> findCourseAnchorRecPage(CourseAnchor searchVo,
			int currentPage, int pageSize);
}
