package com.xczhihui.bxg.online.manager.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;

public interface CourseApplyService {


	/**
	 * 根据条件分页获取课程信息。
	 *
	 * @return
	 */
    public Page<CourseApplyInfo> findCoursePage(CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize);

    CourseApplyInfo findCourseApplyById(Integer id);

    void savePass(Integer courseApplyId, String id);

	void saveNotPass(CourseApplyInfo courseApply, String id);

    Page<CourseApplyResource> findCourseApplyResourcePage(CourseApplyResource searchVo, int currentPage, int pageSize);

	void deleteOrRecoveryCourseApplyResource(Integer courseApplyId,Boolean delete);

}
