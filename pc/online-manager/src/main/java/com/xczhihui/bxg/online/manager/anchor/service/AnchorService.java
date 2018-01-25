package com.xczhihui.bxg.online.manager.anchor.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;

public interface AnchorService {

	/**
	 * 根据条件分页获取课程信息。
	 *
	 * @return
	 */
    public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor, int pageNumber, int pageSize);

    CourseAnchor findCourseAnchorById(Integer id);

    void updateCourseAnchor(CourseAnchor courseAnchor);

	void updatePermissions(Integer id);
}
